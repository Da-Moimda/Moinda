package com.social.moinda.core.domains.pagination;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class PagingRequest {

    private int page;
    private int size;
    private String search;

    public PagingRequest(int page, int size, String search) {
        this.page = page;
        this.size = size;
        this.search = search;
    }

    public Pageable getPageable() {
        return PageRequest.of(
                this.page - 1,
                this.size
        );
    }
}
