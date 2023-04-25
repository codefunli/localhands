package com.nineplus.localhand.service.impl;

import com.jcraft.jsch.*;
import com.nineplus.localhand.enum_class.FolderType;
import com.nineplus.localhand.exceptions.FileHandleException;
import com.nineplus.localhand.exceptions.LocalHandException;
import com.nineplus.localhand.service.ISftpFileService;
import com.nineplus.localhand.utils.CommonConstants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SftpFileService implements ISftpFileService {

    private static final String AVATAR = "avatar";
    private static final String DESCRIPTION = "description";
    private static final String LICENSE = "license";

    public static final String YYYYMMDD = "yyyyMMdd";

    @Value("${fileServer.root}")
    public String ROOT_PATH;

    @Value("${fileServer.host}")
    private String SFTP_HOST;

    @Value("${fileServer.port}")
    private int SFTP_PORT;

    @Value("${fileServer.user}")
    private String SFTP_USER;

    @Value("${fileServer.password}")
    private String SFTP_PASSWORD;

    @Value("${fileServer.maxSize}")
    private float MAX_SIZE_FILE;

    public static final String SEPARATOR = "/";
    @Override
    public Pair<Session, ChannelSftp> getConnection() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(SFTP_USER, SFTP_HOST, SFTP_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(SFTP_PASSWORD);
            session.connect();
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            System.out.println("Sftp server connected");
            return Pair.of(session, channel);
        } catch (JSchException e) {
            throw new FileHandleException(e.getMessage(), e);
        }
    }

    @Override
    public void disconnect(Session session, ChannelSftp channel) {
        if (channel != null) {
            channel.exit();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    @Override
    public String uploadImage(Pair<Session, ChannelSftp> sftpConnection, MultipartFile mfile, FolderType folderType,
                               Long objId, int subType) {
        Session session = null;
        ChannelSftp channel = null;
        String pathTemp = null;
        String finalPath = null;

        // Create folder in sftp server.
        try {
            session = sftpConnection.getFirst();
            channel = sftpConnection.getSecond();

            String absolutePathInSftpServer = getPathSeverUpload(folderType);
            if (!isExistFolder(channel, absolutePathInSftpServer)) {
                pathTemp = this.createFolder(channel, absolutePathInSftpServer);
            } else {
                if (subType == 2){
                    absolutePathInSftpServer = absolutePathInSftpServer + SEPARATOR + buildSubFolderName(folderType);
                    if (!isExistFolder(channel, absolutePathInSftpServer)) {
                        pathTemp = this.createFolder(channel, absolutePathInSftpServer);
                    } else {
                        pathTemp = absolutePathInSftpServer;
                    }
                } else {
                    pathTemp = absolutePathInSftpServer;
                }
            }

            pathTemp = pathTemp + SEPARATOR + objId;
            if (!isExistFolder(channel, pathTemp)) {
                pathTemp = this.createFolder(channel, pathTemp);
            }

            String fileName = FilenameUtils.getName(mfile.getOriginalFilename());

            // save file.
            channel.cd(pathTemp);
            channel.put(mfile.getInputStream(), fileName);
            finalPath = pathTemp + SEPARATOR + fileName;
        } catch (IOException | SftpException e) {
            disconnect(session, channel);
            throw new FileHandleException(e.getMessage(), e);
        }
        return finalPath;
    }

    @Override
    public String uploadAvatar(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId) throws LocalHandException {
        return uploadImage(sftpConnection, file, FolderType.AVATAR, userId, 0);
    }

    @Override
    public String uploadDescription(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId) throws LocalHandException {
        return uploadImage(sftpConnection, file, FolderType.DESCRIPTION, userId, 2);
    }

    @Override
    public String uploadLicense(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId) throws LocalHandException {
        return uploadImage(sftpConnection, file, FolderType.LICENSE, userId, 1);
    }

    private String getPathSeverUpload(FolderType folderType) {
        return ROOT_PATH + SEPARATOR + getParentPath(folderType);
    }

    public boolean isExistFolder(ChannelSftp channel, String path) {
        try {
            channel.lstat(path);
            return true;
        } catch (SftpException ex) {
            return false;
        }
    }

    public String createFolder(ChannelSftp channel, String path) {
        try {
            channel.mkdir(path);
            return path;
        } catch (SftpException ex) {
            throw new FileHandleException(ex.getMessage(), ex);
        }
    }

    private String getParentPath(FolderType folderType) {
        String res = "";
        switch (folderType) {
            case AVATAR -> res = AVATAR;
            case DESCRIPTION -> res = DESCRIPTION;
            case LICENSE -> res = LICENSE;
            default -> {
            }
        }
        return res;
    }

    public String buildSubFolderName(FolderType folderType) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYYMMDD);
        return date.format(formatter);
    }

    @Override
    public boolean isValidFile(MultipartFile file) {
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        // file must be < 5MB
        if (fileSizeInMegabytes >= MAX_SIZE_FILE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeFile(String pathFileServer, Pair<Session, ChannelSftp> sftpConnection) {
        ChannelSftp channel = null;
        try {
            channel = sftpConnection.getSecond();
            // Check exist path on sever
            if (!isExistFolder(channel, pathFileServer)) {
                return false;
            }
            channel.rm(pathFileServer);
        } catch (SftpException ex) {
            throw new FileHandleException(ex.getMessage(), ex);
        }
        return true;
    }

    @Override
    public byte[] getFile(String pathFileDownload, Pair<Session, ChannelSftp> sftpConnection)
            throws LocalHandException {
        byte[] resBytes = null;
        ChannelSftp channel = null;
        Session session = null;
        try {
            session = sftpConnection.getFirst();
            channel = sftpConnection.getSecond();
            if (isExistFolder(channel, pathFileDownload)) {
                resBytes = IOUtils.toByteArray(channel.get(pathFileDownload));
            }
        } catch (SftpException | IOException e) {
            // disconnect to sftp server
            disconnect(session, channel);
            throw new LocalHandException(CommonConstants.MessageError.ER022, null);
        }

        return resBytes;
    }
}
