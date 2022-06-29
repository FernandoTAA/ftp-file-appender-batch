package com.github.fernandotaa.ftpfileappenderbatch.config.component;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {
    private final String string;
    private Integer charPosition = 0;

    public StringInputStream(String string) {
        this.string = string;
    }

    @Override
    public int read() throws IOException {
        if (charPosition >= string.length()) {
            return -1;
        }
        return string.charAt(charPosition++);
    }

    @Override
    public String toString() {
        return string;
    }
}
