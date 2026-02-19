package com.note_management_hub.Note_Management_Hub_API.configurations;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MFAConfiguration {
    @Bean
    public GoogleAuthenticator googleAuthenticator(){
        return new GoogleAuthenticator();
    }
}
