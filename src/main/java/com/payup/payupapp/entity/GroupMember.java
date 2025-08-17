package com.payup.payupapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "group_member")
@Getter
@Setter
@ToString
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long groupId;
    Long UserId;
}
