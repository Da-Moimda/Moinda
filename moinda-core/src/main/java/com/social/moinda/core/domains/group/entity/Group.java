package com.social.moinda.core.domains.group.entity;

import com.social.moinda.core.BaseEntity;
import com.social.moinda.core.domains.group.dto.GroupCreateResponse;
import com.social.moinda.core.domains.member.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "group_id"))
@Table(name = "groups")
@Entity
public class Group extends BaseEntity {

    @Column(name = "group_name", nullable = false, length = 30, unique = true)
    private String name;

    @Column(name = "group_introduce", nullable = false, length = 100)
    private String introduce;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_concern", nullable = false)
    private GroupConcern concern;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "locationDo", column = @Column(name = "do", nullable = false)),
            @AttributeOverride(name = "locationSi", column = @Column(name = "si", nullable = false))
    })
    private GroupLocation location;

    // TODO : fix to @AllArgsConstructor
    public Group(String name,
                 String introduce,
                 GroupLocation location,
                 GroupConcern concern,
                 int capacity) {
        this.name = name;
        this.introduce = introduce;
        this.location = location;
        this.concern = concern;
        this.capacity = capacity;
    }

    public GroupCreateResponse bindToCreateResponse(MemberDto memberDto) {
        return new GroupCreateResponse(
                this.getId(),
                this.name,
                this.introduce,
                this.location.getLocationDo(),
                this.location.getLocationSi(),
                this.concern.name(),
                this.capacity,
                memberDto
        );
    }

}
