package com.social.moinda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application-core.properties")
})
@EnableJpaAuditing
public class MoindaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoindaApplication.class, args);
    }
}
