package com.social.moinda.web.group;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.dto.GroupJoinRequest;
import com.social.moinda.api.group.service.GroupCommandService;
import com.social.moinda.api.group.service.GroupQueryService;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.dto.GroupDetails;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.dto.GroupJoinResponse;
import com.social.moinda.core.domains.member.dto.MemberDto;
import com.social.moinda.core.domains.pagination.PagingRequest;
import com.social.moinda.core.domains.pagination.PagingResult;
import com.social.moinda.web.ApiURLProvider;
import com.social.moinda.web.BaseApiConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.social.moinda.web.RestDocsConfig.field;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = GroupApiController.class)
public class GroupApiControllerTests extends BaseApiConfig {

    @MockBean
    private GroupCommandService groupCommandService;

    @MockBean
    private GroupQueryService groupQueryService;

    @DisplayName("그룹 만들기를 성공한다")
    @Test
    void post_create_group() throws Exception {

        GroupCreateDto groupCreateDto = new GroupCreateDto(1L, "그룹1", "어서오세요?"
                , "경기도", "부천시", "FREE", 30);

        GroupCreateResponse groupCreateResponse = new GroupCreateResponse(1L, "그룹1", "어서오세요?"
                , "경기도", "부천시", "FREE", 30,
                new MemberDto(1L, "user1@email.com", "user1", "hihi"));

        given(groupCommandService.create(any(GroupCreateDto.class))).willReturn(groupCreateResponse);

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.GROUP_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(groupCreateDto)));

        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("memberId").description("사용자 번호").attributes(field("constraint", "필수")),
                                        fieldWithPath("name").description("그룹명").attributes(field("constraint", "필수")),
                                        fieldWithPath("introduce").description("그룹 소개").attributes(field("constraint", "필수")),
                                        fieldWithPath("locationDo").description("그룹 위치1").attributes(field("constraint", "필수")),
                                        fieldWithPath("locationSi").description("그룹 위치2").attributes(field("constraint", "필수")),
                                        fieldWithPath("concern").description("그룹 주제").attributes(field("constraint", "필수")),
                                        fieldWithPath("capacity").description("최대 인원 수").attributes(field("constraint", "필수"))
                                ),
                                responseFields(
                                        fieldWithPath("groupId").description("그룹 번호"),
                                        fieldWithPath("name").description("그룹명"),
                                        fieldWithPath("introduce").description("그룹 소개"),
                                        fieldWithPath("locationDo").description("그룹 위치1"),
                                        fieldWithPath("locationSi").description("그룹 위치2"),
                                        fieldWithPath("concern").description("그룹 주제"),
                                        fieldWithPath("capacity").description("최대 인원 수"),
                                        fieldWithPath("memberDto.memberId").description("사용자 번호"),
                                        fieldWithPath("memberDto.email").description("이메일"),
                                        fieldWithPath("memberDto.name").description("이름"),
                                        fieldWithPath("memberDto.introduce").description("자기소개")
                                )
                        )
                );
    }

    @DisplayName("전체 그룹 목록 조회에 성공한다.")
    @Test
    void get_find_groups() throws Exception {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "그룹1", "부천시", "FREE", 20),
                new GroupDto(2L, "그룹2", "시흥시", "STUDY", 30),
                new GroupDto(3L, "그룹3", "마포구", "FREE", 11)
        );

        given(groupQueryService.searchGroups()).willReturn(dtoList);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.GROUP_API_URL));

        perform.andExpect(status().is2xxSuccessful())
                .andExpect(content().json(toJson(dtoList)))
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("[].groupId").description("그룹 번호"),
                                fieldWithPath("[].groupName").description("그룹명"),
//                                fieldWithPath("[].introduce").description("그룹 소개"),
                                fieldWithPath("[].locationSi").description("그룹 위치1"),
                                fieldWithPath("[].concern").description("그룹 주제"),
                                fieldWithPath("[].userNum").description("최대 인원 수")
                        )
                ));
    }

    @DisplayName("특정한 그룹명 검색 및 조회를 성공한다.")
    @Test
    void get_find_groups_by_search() throws Exception {

        List<GroupDto> dtoList = List.of(
                new GroupDto(1L, "부천그룹1", "부천시", "FREE", 20),
                new GroupDto(3L, "부천그룹3", "부천시", "FREE", 11)
        );

        given(groupQueryService.searchGroups(anyString())).willReturn(dtoList);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.GROUP_API_URL + "/" + "부천"));

        perform.andExpect(status().is2xxSuccessful())
                .andExpect(content().json(toJson(dtoList)))
                .andDo(restDocs.document(
                                // TODO : to required PathParameter Description
                                responseFields(
                                        fieldWithPath("[].groupId").description("그룹 번호"),
                                        fieldWithPath("[].groupName").description("그룹명"),
                                        fieldWithPath("[].locationSi").description("그룹 위치1"),
                                        fieldWithPath("[].concern").description("그룹 주제"),
                                        fieldWithPath("[].userNum").description("최대 인원 수")
                                )
                        )
                );
    }

    @DisplayName("그룹 가입에 성공한다.")
    @Test
    void post_join_group() throws Exception {
        GroupJoinRequest joinRequest = new GroupJoinRequest(1L, 1L);
        GroupJoinResponse joinResponse = new GroupJoinResponse(1L, 1L, "그룹명");

        given(groupCommandService.joinGroup(any(GroupJoinRequest.class)))
                .willReturn(joinResponse);

        ResultActions perform = mockMvc.perform(post(ApiURLProvider.GROUP_API_URL + "/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(joinRequest)));

        perform.andExpect(status().isOk())
                .andDo(restDocs.document(
                                requestFields(
                                        fieldWithPath("groupId").description("그룹 번호").attributes(field("constraint", "필수")),
                                        fieldWithPath("memberId").description("사용자 번호").attributes(field("constraint", "필수"))
                                ),
                                responseFields(
                                        fieldWithPath("memberId").description("사용자 번호"),
                                        fieldWithPath("groupId").description("그룹 번호"),
                                        fieldWithPath("groupName").description("그룹명")
                                )
                        )
                );
    }

    @DisplayName("특정 그룹에 대한 상세정보 조회에 성공한다.")
    @Test
    void get_group_details() throws Exception {

        GroupDetails groupDetails = new GroupDetails(1L, "그룹1", "그룹입니다.",
                Collections.emptyList(), Collections.emptyList());

        given(groupQueryService.getGroupDetails(anyLong())).willReturn(groupDetails);

        ResultActions perform = mockMvc.perform(get(ApiURLProvider.GROUP_API_URL + "/" + 1L + "/details")
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andDo(restDocs.document(
                                // TODO : to required PathParameter Description
                                responseFields(
                                        fieldWithPath("groupId").description("그룹 번호"),
                                        fieldWithPath("groupName").description("그룹명"),
                                        fieldWithPath("groupIntroduce").description("그룹 소개"),
                                        fieldWithPath("members[]").description("그룹 멤버"),
                                        fieldWithPath("meetings[]").description("모임 목록")
                                )
                        )
                );
    }

    @DisplayName("페이징된 전체 그룹목록들을 가져온다.")
    @Test
    void get_groups_with_paging() throws Exception {

        List<GroupDto> dtoList = LongStream.rangeClosed(1, 40)
                .mapToObj(idx ->
                        new GroupDto(idx, "그룹" + idx, "부천시", "FREE", 30))
                .sorted(Comparator.comparing(GroupDto::getGroupId).reversed())
                .collect(Collectors.toList());

        PagingRequest pagingRequest = new PagingRequest(1, 10, null);

        Page<GroupDto> groupDtos = new PageImpl<>(dtoList, pagingRequest.getPageable(), dtoList.size());

        PagingResult<GroupDto> pagingResult = new PagingResult<>(groupDtos);

        given(groupQueryService.displayGroupsWithPaging(any(PagingRequest.class)))
                .willReturn(pagingResult);

        ResultActions perform = mockMvc.perform(
                get(ApiURLProvider.GROUP_API_URL + "/page")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isOk())
                .andDo(restDocs.document(
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("표시될 목록 개수"),
                                        parameterWithName("search").description("검색어").optional()
                                ),
                                responseFields(
                                        fieldWithPath("dtoList[].groupId").description("그룹 번호"),
                                        fieldWithPath("dtoList[].groupName").description("그룹명"),
                                        fieldWithPath("dtoList[].locationSi").description("그룹 위치1"),
                                        fieldWithPath("dtoList[].concern").description("그룹 주제"),
                                        fieldWithPath("dtoList[].userNum").description("최대 인원 수"),
                                        fieldWithPath("totalPage").description("전체 페이지 수"),
                                        fieldWithPath("page").description("현재 페이지"),
                                        fieldWithPath("size").description("페이지 내 표시되는 목록 개수"),
                                        fieldWithPath("start").description("페이지 목록에 표시될 첫 번째 값"),
                                        fieldWithPath("end").description("페이지 목록에 표시될 마지막 값"),
                                        fieldWithPath("prev").description("이전 버튼 활성화 여부"),
                                        fieldWithPath("next").description("다음 버튼 활성화 여부"),
                                        fieldWithPath("pageList[]").description("화면에 표시될 페이지 목록")
                                )
                        )
                );
    }
}
