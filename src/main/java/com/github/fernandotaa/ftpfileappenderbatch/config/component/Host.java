package com.github.fernandotaa.ftpfileappenderbatch.config.component;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Objects;

@Component
public class Host {
    private final String customHost;

    public Host(@Value("${app.host}") String customHost) {
        this.customHost = customHost;
    }

    @SneakyThrows
    public String getHost() {
        if (Objects.nonNull(customHost) && !customHost.isEmpty()) {
            return customHost;
        }
        return InetAddress.getLocalHost().getHostName();
    }
}
