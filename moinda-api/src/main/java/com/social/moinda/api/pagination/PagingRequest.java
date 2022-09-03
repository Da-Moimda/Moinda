package com.social.moinda.api.pagination;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PagingRequest {

    /*
        페이지 위치, 보여 줄 개수, 검색어?, 정렬 조건
     */

    private int page;
    private int size;

    private String[] orderByCondition;
    private String search;

    public Pageable getPageable() {
        return PageRequest.of(1, 10);
    }
}
