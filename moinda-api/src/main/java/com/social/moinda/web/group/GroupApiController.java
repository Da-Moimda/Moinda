package com.social.moinda.web.group;

import com.social.moinda.api.group.dto.GroupCreateDto;
import com.social.moinda.api.group.dto.GroupJoinRequest;
import com.social.moinda.api.group.service.GroupCommandService;
import com.social.moinda.api.group.service.GroupQueryService;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.group.dto.GroupDetails;
import com.social.moinda.core.domains.group.dto.GroupDto;
import com.social.moinda.core.domains.group.dto.GroupJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupApiController {

    private final GroupCommandService groupCommandService;
    private final GroupQueryService groupQueryService;

    @PostMapping("")
    public ResponseEntity<GroupCreateResponse> create(@RequestBody @Validated GroupCreateDto dto) {
        GroupCreateResponse groupCreateResponse = groupCommandService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupCreateResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<GroupJoinResponse> joinGroup(@RequestBody @Validated GroupJoinRequest joinRequest) {
        GroupJoinResponse JoinResponse = groupCommandService.joinGroup(joinRequest);
        return ResponseEntity.status(HttpStatus.OK).body(JoinResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<GroupDto>> getGroups() {
        List<GroupDto> groups = groupQueryService.searchGroups();
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }

    // TODO : Postman 관련 에러가 조금 있는듯한?
    @GetMapping("/{keyword}")
    public ResponseEntity<List<GroupDto>> getGroups(@PathVariable String keyword) {
        List<GroupDto> groups = groupQueryService.searchGroups(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(groups);
    }

    @GetMapping("/{groupId}/details")
    public ResponseEntity<GroupDetails> getDetails(@PathVariable Long groupId) {
        GroupDetails groupDetails = groupQueryService.getGroupDetails(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(groupDetails);
    }

}
