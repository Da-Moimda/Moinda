package com.social.moinda;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
     TODO : 아래의 설정들을 core 패키지에서 관리시키고 Import 한다면??
     value를 환경설정파일에서 가져올순 없나?
 */
@PropertySources({
        @PropertySource("classpath:application-core.properties")
})
@EntityScan("com.social.moinda.core")
@EnableJpaRepositories("com.social.moinda.core")
@EnableJpaAuditing
@Configuration
public class JpaConfiguration {
}
