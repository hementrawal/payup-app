package com.payup.payupapp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExpenseRequest {
    String groupName;
    Long amount;
    String description;
    String createdBy;
    List<UserExpense> users;
}
