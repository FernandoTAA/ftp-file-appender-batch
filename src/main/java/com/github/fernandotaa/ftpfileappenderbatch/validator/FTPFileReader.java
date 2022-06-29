package com.github.fernandotaa.ftpfileappenderbatch.validator;

import com.github.fernandotaa.ftpfileappenderbatch.config.component.FTP;
import com.github.fernandotaa.ftpfileappenderbatch.validator.FileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FTPFileReader extends FileReader {

    private final String path;
    private final File file;
    private final FTP ftp;
    private boolean downloaded = false;

    public FTPFileReader(
            @Value("${app.ftp-file}") String path,
            @Value("${app.file}") File file,
            FTP ftp
    ) {
        super(file);
        this.path = path;
        this.file = file;
        this.ftp = ftp;
    }

    protected String readLine() {
        if (!downloaded) {
            ftp.download(path, file);
            downloaded = true;
        }

        return super.readLine();
    }

}
