package com.social.moinda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@PropertySources({
        @PropertySource("classpath:application-core.properties")
})
@EntityScan("com.social.moinda.core")
@EnableJpaRepositories("com.social.moinda.core")
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.social.moinda")
public class MoindaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoindaApplication.class, args);
    }
}
