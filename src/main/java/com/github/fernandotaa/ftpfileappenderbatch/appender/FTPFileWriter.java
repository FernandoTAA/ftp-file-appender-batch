package com.github.fernandotaa.ftpfileappenderbatch.appender;


import com.github.fernandotaa.ftpfileappenderbatch.config.component.FTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FTPFileWriter extends AbstractFileWriter {

    private final String path;
    private final FTP ftp;

    public FTPFileWriter(
            @Value("${app.ftp-file}") String path,
            FTP ftp
    ) {
        this.path = path;
        this.ftp = ftp;
    }

    public void append(final String text) {
        ftp.appendFile(path, text + System.lineSeparator());
    }
}
