package com.social.moinda.core.domains.pagination;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class PagingResult<DTO> {

    /*
        페이징에 관련된 데이터를 전달해야하기 때문에,
        전체적인 데이터를 전달해주는 방향으로,
     */
    private List<DTO> dtoList;

    // 페이지번호를 뿌려줄 변수들 생성
    private int totalPage;
    private int page, size, start, end;

    private boolean prev, next;

    // 페이지 번호 나열용 목록
    private List<Integer> pageList;

    public PagingResult(Page<DTO> dtoPage) {
        this.dtoList = dtoPage.toList();

        this.totalPage = dtoPage.getTotalPages();
        makePaging(dtoPage.getPageable());
    }

    public void makePaging(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int mockEndPageNumber = (int)(Math.ceil( this.page * 0.1 )) * 10;

        this.start = mockEndPageNumber - 9;

        this.prev = this.start > 1;

        this.end = Math.min(this.totalPage, mockEndPageNumber);

        this.next = this.totalPage > mockEndPageNumber;

        this.pageList = IntStream.rangeClosed(this.start, this.end)
                .boxed()
                .collect(Collectors.toList());
    }
}
