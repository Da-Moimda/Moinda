package com.social.moinda.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;


/*
    @SpringBootTest 는 통합테스트로 Application 클래스의 설정을 Bean으로 등록하지만
    @WebMvcTest는 JPA 관련 Bean을 등록하지 않으므로 추가적으로 MockBean을 사용.
 */
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest
public class BaseApiConfig {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected static final String MEMBER_API_URL = "/api/member";

    protected String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
