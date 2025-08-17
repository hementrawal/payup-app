package com.payup.payupapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GroupRequest {
    String groupName;
    String createdBy;
    List<String> members;
}
