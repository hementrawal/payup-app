package com.payup.payupapp.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserExpense {
    String userName;
    Long paid;
    Long owed;
}
