package com.social.talk.web.member;

import com.social.talk.api.member.dto.MemberCreateDto;
import com.social.talk.api.member.service.MemberCommandService;
import com.social.talk.core.domains.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberCommandService memberCommandService;

    @PostMapping("")
    public ResponseEntity<MemberDto> signup(@RequestBody @Validated MemberCreateDto dto) {

        MemberDto memberDto = memberCommandService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberDto);
    }

}
