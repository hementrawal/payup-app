package com.payup.payupapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "expense_split")
@Getter
@Setter
@ToString
public class ExpenseSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long expenseId;
    Long userId;
    Long paid;
    Long owed;
}
