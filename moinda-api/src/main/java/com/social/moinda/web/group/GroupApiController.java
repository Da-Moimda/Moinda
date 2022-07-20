package com.social.moinda.web.group;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.service.GroupCommandService;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
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
@RequestMapping("/api/group")
public class GroupApiController {

    private final GroupCommandService groupCommandService;

    @PostMapping("")
    public ResponseEntity<GroupCreateResponse> create(@RequestBody @Validated GroupCreateDto dto) {
        GroupCreateResponse groupCreateResponse = groupCommandService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupCreateResponse);
    }

}
