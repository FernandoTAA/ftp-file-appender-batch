package com.github.fernandotaa.ftpfileappenderbatch.config.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
public class FTP {

    private final String server;
    private final Integer port;
    private final String username;
    private final String password;
    private final Boolean passiveMode;
    private final Boolean enabledLog;

    public FTP(
            @Value("${ftp.server}") String server,
            @Value("${ftp.port}") Integer port,
            @Value("${ftp.username}") String username,
            @Value("${ftp.password}") String password,
            @Value("${ftp.passive-mode}") Boolean passiveMode,
            @Value("${ftp.log.enabled}") Boolean enabledLog
    ) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.passiveMode = passiveMode;
        this.enabledLog = enabledLog;
    }

    private FTPClient ftpClient() {
        final FTPClient ftp = new FTPClient();
        if (enabledLog) {
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
        try {
            ftp.connect(server, port);
            ftp.login(username, password);
            if (passiveMode) {
                ftp.enterLocalPassiveMode();
            }
            return ftp;
        } catch (IOException e) {
            log.error("ERROR FTP.ftpClient() ", e);
            throw new RuntimeException(e);
        }
    }

    public void appendFile(final String remotePath, final String text) {
        final FTPClient ftpClient = ftpClient();
        try {
            ftpClient.appendFile(remotePath, new StringInputStream(text));
            ftpClient.disconnect();
        } catch (IOException e) {
            log.error("ERROR FTP.appendFile() ", e);
            throw new RuntimeException(e);
        }
    }

    public void download(String remotePath, File localFile) {
        final FTPClient ftpClient = ftpClient();
        try {
            ftpClient.retrieveFile(remotePath, new BufferedOutputStream(new FileOutputStream(localFile)));
            ftpClient.disconnect();
        } catch (IOException e) {
            log.error("ERROR FTP.download() ", e);
            throw new RuntimeException(e);
        }
    }
}
