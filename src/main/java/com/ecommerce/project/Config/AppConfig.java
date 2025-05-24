package com.ecommerce.project.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration annotation is used to indicate that this class contains one or more bean methods
@Configuration
public class AppConfig {
    // @Bean annotation is used to indicate that a method produces a bean to be managed by the Spring container
    @Bean
    public ModelMapper modelMapper(){
        // this method is used to create a ModelMapper bean
        // ModelMapper is used to map the entity to the DTO and vice versa
        return new ModelMapper();
    }
}
