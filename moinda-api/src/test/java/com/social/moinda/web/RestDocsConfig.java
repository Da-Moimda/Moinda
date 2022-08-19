package com.social.moinda.web;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;

@TestConfiguration
public class RestDocsConfig {

    /*
        "{class-name}/{method-name}"
        1번을 해결한 코드입니다.
        조각이 생성되는 디렉토리 명을 클래스명/메서드 명으로 정합니다.

        prettyPrint
        json이 한 줄로 출력되던 내용을 pretty 하게 찍어줍니다.
     */
    @Bean
    public RestDocumentationResultHandler write(){
        return MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );
    }
    /*
    Attribute field 메서드
    rest docs에서 기본적으로 문서작성에 필요한 optional(필수값여부), description(설명) 같은 체이닝 메서드는 제공하지만
    제약조건 같이 커스텀으로 작성하는 내용에 대한 기능은 없습니다.
    따라서 Attribute를 이용해 key, value 값으로 넣어주기 위한 함수입니다.
     */
    public static final Attributes.Attribute field(
            final String key,
            final String value){
        return new Attributes.Attribute(key,value);
    }
}
