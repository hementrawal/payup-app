package com.payup.payupapp.controller;


import com.payup.payupapp.entity.*;
import com.payup.payupapp.model.*;
import com.payup.payupapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class ExpenseApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ExpenseSplitRepository expenseSplitRepository;

    @PostMapping("/createUser")
    public ResponseEntity<?> handleCreateUser(@RequestBody UserRequest request){
        try{
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUserName());
            user.setPasswordHash(request.getPassword());

            userRepository.save(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Sign up successful");
            System.out.println(response);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println(e.toString());

            Map<String, String> error = new HashMap<>();
            error.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/createGroup")
    public ResponseEntity<?> handleCreateGroup(@RequestBody GroupRequest request){
        try{
            String groupName=request.getGroupName();
            String createdBy=request.getCreatedBy();
            Long creatorId=userRepository.findByUsername(createdBy).get().getId();

            Group group=new Group();
            group.setCreatedBy(creatorId);
            group.setName(groupName);
            groupRepository.save(group);

            Long groupId=groupRepository.findByName(groupName).getId();

            for(String user:request.getMembers()){
                Long id=userRepository.findByUsername(user).get().getId();
                GroupMember member=new GroupMember();
                member.setUserId(id);
                member.setGroupId(groupId);
                groupMemberRepository.save(member);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Group creation successful");
            response.put("data", group.toString());
            System.out.println(response);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println(e.toString());

            Map<String, String> error = new HashMap<>();
            error.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Transactional
    @PostMapping("/addExpense")
    public ResponseEntity<?> handleAddExpense(@RequestBody ExpenseRequest request) {

        try{
            String description = request.getDescription();

            if(expenseRepository.findByDescription(description)!=null){
                Long expenseId= expenseRepository.findByDescription(description).getId();
                expenseSplitRepository.deleteByExpenseId(expenseId);
                expenseRepository.deleteById(expenseId);
            }

            String createdBy = request.getCreatedBy();
            Expense expense = new Expense();
            expense.setDescription(description);
            expense.setAmount(request.getAmount());
            expense.setCreatedBy(createdBy);

            String groupName = request.getGroupName();
            Long groupId = -1L;
            if(!groupName.equals("non_group")){
                groupId = groupRepository.findByName(groupName).getId();
            }
            expense.setGroupId(groupId);
            expenseRepository.save(expense);

            Long expenseId = expenseRepository.findByDescription(request.getDescription()).getId();

            for (UserExpense userExpense : request.getUsers()) {
                Long userId = userRepository.findByUsername(userExpense.getUserName()).get().getId();
                ExpenseSplit expenseSplit = new ExpenseSplit();
                expenseSplit.setExpenseId(expenseId);
                expenseSplit.setUserId(userId);
                expenseSplit.setOwed(userExpense.getOwed());
                expenseSplit.setPaid(userExpense.getPaid());
                expenseSplitRepository.save(expenseSplit);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Expense addition successful");
            response.put("data", expense.toString());
            System.out.println(response);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println(e.toString());

            Map<String, String> error = new HashMap<>();
            error.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @GetMapping("/groupExpenses/{groupName}")
    public ResponseEntity<?> handleGetGroupExpenses(@PathVariable String groupName){

        try{
            Long groupId=-1L;

            if(!groupName.equals("non_group")){
                groupId=groupRepository.findByName(groupName).getId();
            }

            List<Expense> expenses = expenseRepository.findByGroupId(groupId);
            LinkedList<GroupExpensesResponse> responses=new LinkedList<>();

            for(Expense expense:expenses){
                Long expenseId= expense.getId();
                responses.add(new GroupExpensesResponse());
                responses.getLast().setExpense(expense);
                responses.getLast().setUserExpenses(new LinkedList<>());
                List<ExpenseSplit> expenseSplits=expenseSplitRepository.findByExpenseId(expenseId);
                for(ExpenseSplit expenseSplit:expenseSplits) {
                    responses.getLast().getUserExpenses().add(new UserExpense());

                    String username = userRepository.findById(expenseSplit.getUserId()).get().getUsername();
                    responses.getLast().getUserExpenses().getLast().setUserName(username);

                    responses.getLast().getUserExpenses().getLast().setPaid(expenseSplit.getPaid());
                    responses.getLast().getUserExpenses().getLast().setOwed(expenseSplit.getOwed());
                }
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Group expenses fetch successful");
            response.put("data", responses.toString());
            System.out.println(response);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println(e.toString());

            Map<String, String> error = new HashMap<>();
            error.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("getExpenses/{userName}")
    public ResponseEntity<?> handleGetExpensesPerUser(@PathVariable String userName){

        try{
            Map<String,Long> relations=new HashMap<>();
            Long userId=userRepository.findByUsername(userName).get().getId();
            List<ExpenseSplit> expenseSplits=expenseSplitRepository.findByUserId(userId);

            for(ExpenseSplit expenseSplit:expenseSplits){
                String paidBy = expenseRepository.findById(expenseSplit.getExpenseId()).get().getCreatedBy();
                if(userName.equals(paidBy)){
                    List<ExpenseSplit> splits = expenseSplitRepository.findByExpenseId(expenseSplit.getExpenseId());
                    for(ExpenseSplit split:splits){
                        if(split.getUserId().equals(userId))
                            continue;

                        Long amount = split.getOwed();
                        String name=userRepository.findById(split.getUserId()).get().getUsername();
                        relations.put(name,relations.getOrDefault(name,0L)+amount);
                    }
                    continue;
                }

                Long amount = expenseSplit.getPaid()-expenseSplit.getOwed();

                relations.put(paidBy,relations.getOrDefault(paidBy,0L)+amount);
            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Expenses fetch successful");
            response.put("data", relations.toString());
            System.out.println(response);

            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            System.out.println(e.toString());

            Map<String, String> error = new HashMap<>();
            error.put("error", e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
