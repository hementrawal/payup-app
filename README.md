# PayUp API

PayUp is an application that allows friends to create groups and split expenses among themselves.
It provides clear success and error messages for all API operations.

## Base URL
```
http://localhost:8080
```

---

## API Endpoints & Descriptions

### 1. Create User
**POST** `/createUser`  
Creates a new user account in the PayUp system.

**Request Body:**
```json
{
  "userName": "anuraggupta",
  "email": "anurag@gmail.com",
  "password": "anurag"
}
```

**Success Response:**
```json
{
  "message": "Sign up successful"
}
```

---

### 2. Create Group
**POST** `/createGroup`  
Creates a new expense group with given members.

**Request Body:**
```json
{
  "groupName": "room224",
  "createdBy": "agrox29",
  "members": ["Harsh", "Akash", "gupta"]
}
```

**Success Response:**
```json
{
  "data": "Group{id=6, name='room224', createdBy=9}",
  "message": "Group creation successful"
}
```

---

### 3. Add Expense
**POST** `/addExpense`  
Adds a new expense to a specific group and assigns owed amounts to members.

**Request Body:**
```json
{
  "groupName": "room224",
  "amount": 1000,
  "description": "electricity bill",
  "createdBy": "agrox29",
  "users": [
    { "userName": "Harsh", "paid": 0, "owed": 200 },
    { "userName": "Akash", "paid": 0, "owed": 500 },
    { "userName": "agrox29", "paid": 0, "owed": 300 }
  ]
}
```

**Success Response:**
```json
{
  "data": "Expense(id=13, description=electricity bill, amount=1000, groupId=6, createdBy=agrox29, createdAt=null)",
  "message": "Expense addition successful"
}
```

---

### 4. Get Group Expenses
**GET** `/groupExpenses/{groupName}`  
Retrieves all expenses for a given group along with each member’s contributions.

**Example:**
```
GET /groupExpenses/NITP
```

**Success Response:**
```json
{
  "data": "[GroupExpensesResponse(expense=Expense(id=9, description=bikeTrip, amount=900, groupId=4, createdBy=Vikram, createdAt=null), userExpenses=[UserExpense(userName=Akash, paid=0, owed=300), UserExpense(userName=Vikram, paid=900, owed=300), UserExpense(userName=Kamla, paid=0, owed=300)])]",
  "message": "Group expenses fetch successful"
}
```

---

### 5. Get User Expenses
**GET** `/getExpenses/{userName}`  
Retrieves a summary of expenses for a given user across all groups.

**Example:**
```
GET /getExpenses/Hement Rawal
```

**Success Response:**
```json
{
  "data": "{Harsh=800, Akash=100}",
  "message": "Expenses fetch successful"
}
```

---

## Schemas

### UserRequest
```json
{
  "userName": "string",
  "email": "string",
  "password": "string"
}
```

### GroupRequest
```json
{
  "groupName": "string",
  "createdBy": "string",
  "members": ["string"]
}
```

### ExpenseRequest
```json
{
  "groupName": "string",
  "amount": 0,
  "description": "string",
  "createdBy": "string",
  "users": [
    {
      "userName": "string",
      "paid": 0,
      "owed": 0
    }
  ]
}
```

---

## Running the PayUp Spring Boot Application

### Prerequisites
- Java 17 or later installed.
- Maven installed.
- PostgreSQL/MySQL database running (update application.properties with your DB config).

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/payup.git
   cd payup
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the API at:
   ```
   http://localhost:8080
   ```

5. To view the API documentation (Swagger UI), open:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## Authors

- **Hement Rawal**  
  [LinkedIn](https://www.linkedin.com/in/hement-rawal/) | [GitHub](https://github.com/hementrawal)

- **Anurag Gupta**  
  [LinkedIn](https://www.linkedin.com/in/anuraggupta29/) | [GitHub](https://github.com/anuraggupta29)