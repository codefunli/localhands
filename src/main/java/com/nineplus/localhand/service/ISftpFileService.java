package com.nineplus.localhand.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.nineplus.localhand.enum_class.FolderType;
import com.nineplus.localhand.exceptions.LocalHandException;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISftpFileService {

    Pair<Session, ChannelSftp> getConnection() throws LocalHandException;

    void disconnect(Session session, ChannelSftp channel);

    String uploadImage(Pair<Session, ChannelSftp> sftpConnection, MultipartFile mfile, FolderType folderType,
                       Long objId, int subType) throws LocalHandException;

    String uploadAvatar(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId)throws LocalHandException;

    String uploadDescription(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId)throws LocalHandException;

    String uploadLicense(Pair<Session, ChannelSftp> sftpConnection, MultipartFile file, long userId)throws LocalHandException;

    boolean isValidFile(MultipartFile mFiles);

    boolean removeFile(String pathFileServer, Pair<Session, ChannelSftp> sftpConnection);

    byte[] getFile(String pathFileDownload, Pair<Session, ChannelSftp> sftpConnection) throws LocalHandException;
}
