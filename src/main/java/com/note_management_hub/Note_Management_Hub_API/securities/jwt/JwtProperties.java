package com.note_management_hub.Note_Management_Hub_API.securities.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private long expiration;
}
