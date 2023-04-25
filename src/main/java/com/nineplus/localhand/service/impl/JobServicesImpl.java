package com.nineplus.localhand.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.nineplus.localhand.dto.JobDto;
import com.nineplus.localhand.dto.JobProgressDto;
import com.nineplus.localhand.enum_class.JobStatus;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.model.Job;
import com.nineplus.localhand.model.JobHistory;
import com.nineplus.localhand.model.User;
import com.nineplus.localhand.repository.JobHistoryRepository;
import com.nineplus.localhand.repository.JobRatingRepository;
import com.nineplus.localhand.repository.JobRepository;
import com.nineplus.localhand.repository.UserRepository;
import com.nineplus.localhand.service.ISftpFileService;
import com.nineplus.localhand.service.JobServices;
import com.nineplus.localhand.utils.CommonConstants;
import com.nineplus.localhand.utils.UserAuthUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobServicesImpl implements JobServices {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private JobRatingRepository jobRatingRepository;

    @Autowired
    private UserAuthUtils userAuthUtils;

    @Autowired
    private ISftpFileService sftpFileService;

    @Autowired
    private UserRepository userRepository;

    private static final String SEPARATOR = "<>";

    /**
     * @param jobDto
     * @param files
     * @return
     * @throws LocalHandException
     */
    @Override
    @Transactional
    public JobDto createJob(JobDto jobDto, List<MultipartFile> files) throws LocalHandException {
        try {
            if (jobRepository.checkIfLocalHasJobsProgresing(jobDto.getLocalUserId(), JobStatus.OPEN.getValue(), JobStatus.WORKING.getValue()) > 0) {
                throw new LocalHandException(CommonConstants.MessageError.ER023, null);
            }
            Job job = new Job();
            BeanUtils.copyProperties(jobDto, job);
            String path = this.getPathImage(jobDto, files);
            job.setJobMediaPicture(path);
            job.setSysCreatedAt(Instant.now());
            job.setSysUpdatedAt(Instant.now());
            job.setJobStatus(JobStatus.OPEN);
            job.setJobDate(Instant.now());
            job.setHelperAcceptedAt(Instant.now());
            job = jobRepository.save(job);
            BeanUtils.copyProperties(job, jobDto);
            createNewJobHistory(job);
            //        payment capture fund
            return jobDto;
        } catch (LocalHandException e) {
            throw new LocalHandException(e.getMsgCode(), null);
        }
    }

    /**
     * @param jobDto
     * @return
     * @throws LocalHandException
     */
    @Override
    @Transactional
    public JobDto updateJob(JobDto jobDto) throws LocalHandException {
        Optional<Job> optionalJob = jobRepository.findById(jobDto.getId());
        if (optionalJob.isEmpty()) {
            throw new RuntimeException(CommonConstants.MessageError.ER025);
        }
        Job job = optionalJob.get();
        jobDto.setJobStatus(jobDto.getJobStatus());
        BeanUtils.copyProperties(jobDto, job);
        job.setJobStatus(JobStatus.fromValue(jobDto.getJobStatus()));
        job.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        job.setSysUpdatedAt(Instant.now());
        job = jobRepository.save(job);
        BeanUtils.copyProperties(job, jobDto);
        return jobDto;
    }

    /**
     * @param job
     * @throws LocalHandException
     */
    @Transactional
    public void createNewJobHistory(Job job) throws LocalHandException {
        JobHistory jobHistory = new JobHistory();
        jobHistory.setJobId(job.getId());
        jobHistory.setJobStatus(JobStatus.OPEN);
        jobHistory.setLocalId(job.getLocalUserId());
        jobHistory.setHelperId(job.getHelperUserId());
        jobHistory.setSysCreatedAt(Instant.now());
        jobHistory.setSysCreatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        jobHistory.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        jobHistory.setSysUpdatedAt(Instant.now());
        jobHistoryRepository.save(jobHistory);
    }

    /**
     * @param job
     * @param jobDto
     * @throws LocalHandException
     */
    @Transactional
    public void updateJobHistory(Job job, JobDto jobDto) throws LocalHandException {
        JobHistory jobHistory = jobHistoryRepository.getHistoryToUpdate(job.getId(), job.getLocalUserId(), job.getHelperUserId(), job.getJobStatus().getValue());
        jobHistory.setHelperId(jobDto.getHelperUserId());
        jobHistory.setJobStatus(JobStatus.fromValue(jobDto.getJobStatus()));
        jobHistory.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        jobHistory.setSysUpdatedAt(Instant.now());
        jobHistory.setCancelReason(jobDto.getReason());
        jobHistory.setCancelBy(jobDto.getActionBy());
        jobHistoryRepository.save(jobHistory);
    }

    /**
     * @param id
     * @return
     * @throws LocalHandException
     */
    @Override
    public JobDto getDetail(Long id) throws LocalHandException {
        Pair<Session, ChannelSftp> sftpConnection = null;
        Session session = null;
        ChannelSftp channel = null;
        List<byte[]> desImg = new ArrayList<>();
        try {
            Optional<Job> jobOpt = jobRepository.findById(id);
            if (jobOpt.isEmpty()) {
                throw new LocalHandException(CommonConstants.MessageError.ER021, null);
            }
            Job job = jobOpt.get();
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(job, jobDto);
            if (ObjectUtils.isNotEmpty(job.getJobMediaPicture())) {
                sftpConnection = sftpFileService.getConnection();
                session = sftpConnection.getFirst();
                channel = sftpConnection.getSecond();
                String[] pathList = job.getJobMediaPicture().split(SEPARATOR);
                if (ObjectUtils.isNotEmpty(pathList)) {
                    for (int i = 0; i < pathList.length; i++) {
                        desImg.add(sftpFileService.getFile(pathList[i], sftpConnection));
                    }
                }
            }
            jobDto.setJobMediaPicture(desImg);
            return jobDto;
        } catch (LocalHandException e) {
            throw new LocalHandException(CommonConstants.MessageError.ER021, null);
        } finally {
            sftpFileService.disconnect(session, channel);
        }
    }

    @Override
    public JobDto cancelJob(JobProgressDto jobDto) throws LocalHandException {
        Optional<Job> optionalJob = jobRepository.findById(jobDto.getJobId());
        if (optionalJob.isEmpty()) {
            throw new RuntimeException(CommonConstants.MessageError.ER025);
        }
        Job job = optionalJob.get();
        JobDto dto = new JobDto();
        updateStatusHistory(job, jobDto, JobStatus.CANCEL);
        if (Objects.equals(job.getLocalUserId(), jobDto.getActionBy())) {
            job.setJobStatus(JobStatus.CANCEL);
        } else {
            createNewJobHistory(job);
            job.setJobStatus(JobStatus.OPEN);
            job.setHelperAcceptedAt(null);
            job.setHelperGeoLng(null);
            job.setHelperGeoLng(null);
            job.setHelperGeoLat(null);
        }
        job.setSysUpdatedAt(Instant.now());
        job.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        BeanUtils.copyProperties(jobRepository.save(job), dto);
        return dto;
    }

    @Override
    public JobDto acceptJob(JobProgressDto jobDto) throws LocalHandException {
        Optional<Job> optionalJob = jobRepository.findById(jobDto.getJobId());
        if (optionalJob.isEmpty()) {
            throw new RuntimeException(CommonConstants.MessageError.ER025);
        }
        Optional<User> optionalUser = userRepository.findById(jobDto.getActionBy());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException(CommonConstants.MessageError.ER027);
        }
        Job job = optionalJob.get();
        User helper = optionalUser.get();
        JobDto dto = new JobDto();
        updateStatusHistory(job, jobDto, JobStatus.WORKING);
        job.setJobStatus(JobStatus.WORKING);
        job.setHelperUserId(helper.getId());
        job.setHelperGeoLat(helper.getLastGeoLat());
        job.setHelperGeoLng(helper.getLastGeoLng());
        job.setHelperAcceptedAt(Instant.now());
        BeanUtils.copyProperties(jobRepository.save(job), dto);
        return dto;
    }

    @Override
    public JobDto doneJob(JobProgressDto jobDto) throws LocalHandException {
        Optional<Job> optionalJob = jobRepository.findById(jobDto.getJobId());
        if (optionalJob.isEmpty()) {
            throw new RuntimeException(CommonConstants.MessageError.ER025);
        }
        Job job = optionalJob.get();
        JobDto dto = new JobDto();
        updateStatusHistory(job, jobDto, JobStatus.DONE);
        job.setJobStatus(JobStatus.DONE);
        job.setHelperAcceptedAt(Instant.now());
        BeanUtils.copyProperties(jobRepository.save(job), dto);
        return dto;
    }

    /**
     * @param jobDto
     * @param files
     * @return
     * @throws LocalHandException
     */
    public String getPathImage(JobDto jobDto, List<MultipartFile> files) throws LocalHandException {
        Pair<Session, ChannelSftp> sftpConnection = null;
        Session session = null;
        ChannelSftp channel = null;
        StringBuilder finalPath = new StringBuilder("");
        try {
            if (ObjectUtils.isNotEmpty(files) && !files.isEmpty()) {
                sftpConnection = sftpFileService.getConnection();
                session = sftpConnection.getFirst();
                channel = sftpConnection.getSecond();
                for (MultipartFile file : files) {
                    if (!sftpFileService.isValidFile(file)) {
                        throw new LocalHandException(CommonConstants.MessageError.ER018, null);
                    }
                    String path = sftpFileService.uploadDescription(sftpConnection, file, jobDto.getLocalUserId());
                    finalPath.append(path).append(SEPARATOR);
                }
            }
            return finalPath.toString();
        } catch (LocalHandException e) {
            throw new LocalHandException(e.getMsgCode(), null);
        } finally {
            sftpFileService.disconnect(session, channel);
        }
    }

    private void updateStatusHistory(Job job, JobProgressDto dto, JobStatus jobStatus) throws LocalHandException {
        JobHistory jobHistory = jobHistoryRepository.getHistoryToUpdate(job.getId(), job.getLocalUserId(), job.getHelperUserId(), job.getJobStatus().getValue());
        jobHistory.setJobStatus(jobStatus);
        jobHistory.setSysUpdatedBy(userAuthUtils.getUserInfoFromReq().getUsername());
        jobHistory.setSysUpdatedAt(Instant.now());
        jobHistory.setCancelReason(dto.getReason());
        if (jobStatus.equals(JobStatus.CANCEL)) {
            jobHistory.setCancelBy(dto.getActionBy());
        }
        if (jobStatus.equals(JobStatus.WORKING)) {
            jobHistory.setHelperId(dto.getActionBy());
        }
        jobHistoryRepository.save(jobHistory);
    }
}
