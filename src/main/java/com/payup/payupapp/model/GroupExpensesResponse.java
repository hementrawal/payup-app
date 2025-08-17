package com.payup.payupapp.model;

import com.payup.payupapp.entity.Expense;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class GroupExpensesResponse {
    Expense expense;
    LinkedList<UserExpense> userExpenses;
}
