# API documentation
    This document describes the available API endpoints, their usage, request/response formats, and examples.

## 1. Create user
    Endpoint : /createUser
    Payload : 
    {
        "userName" : "Tester",
        "email" : "Test@gmail.com",
        "password" : "Test@123"
    }
    
    Response : Tester

## 2. Create group
    Endpoint : /creatGroup
    Payload : 
    {
        "groupName" : "Test",
        "createdBy" : "Kamla",
        "members" : [
            "Hement Rawal",
            "Kamla",
            "Vikram"
        ]
    }
    
    Resonse : Group{id=5, name='Test', createdBy=6}

## 3. Add expense
    Endpoint : /addExpense
    Payload : 
    {
        "groupName": "non_group",
        "amount": 600,
        "description": "Badminton",
        "createdBy": "Harsh",
        "users": [
            {
                "userName": "Hement Rawal",
                "paid": 0,
                "owed": 200
            },
            {
                "userName": "Harsh",
                "paid": 600,
                "owed": 200
            },
            {
                "userName": "Akash",
                "paid": 0,
                "owed": 200
            }
        ]
    }
    
    Response : 200 status code.

## 4. Get group expenses detail
    Endpoint : /GroupExpenses/{groupName}
    Response : 
    [
        {
            "expense": {
                "id": 9,
                "description": "bikeTrip",
                "amount": 900,
                "groupId": 4,
                "createdBy": "Vikram",
                "createdAt": null
            },
            "userExpenses": [
                {
                    "userName": "Akash",
                    "paid": 0,
                    "owed": 300
                },
                {
                    "userName": "Vikram",
                    "paid": 900,
                    "owed": 300
                },
                {
                    "userName": "Kamla",
                    "paid": 0,
                    "owed": 300
                }
            ]
        }
    ]

    Above response is for groupName NITP

## 5. Get details of amount user owed from/to others
    Endpoint : /getExpenses/{userName}
    Response : {Kamla=300, Akash=300}       // For userName Vikram