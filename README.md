
# Spring Boot Server Instructions

## Starting the Server

Before starting the server, set the following environment variables:
- `DB_HOST`
- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_NAME`

You can use `setx` (Windows) or `export` (Unix/Linux) for temporary variables.

To start the server, use the following command:



## Creating a User

After the server starts, create a user using the following endpoint:

**POST** `http://localhost:8080/user/add`

**Body:**
{
"username": "test",
"email": "test@email.com",
"password": "test",
"role": "ROLE_USER"
}

## Generating a JWT Token

After successfully creating a user, generate a JWT token using the following endpoint:

**POST** `http://localhost:8080/user/generateToken`

**Body:**
{
"username": "test",
"password": "test"
}


Note: All subsequent requests need to have a header key `"Authorization"` with the value `"Bearer jwt_token"`.

## Adding a Transaction

To add a transaction, use the following endpoint:

**POST** `http://localhost:8080/transactions/add`

**Body:**
{
"transactionAmount": "12000",
"transactionType": "CREDIT",
"transactionTime": "2024-09-01T00:00:00",
"transactionCurrency": "INR"
}


## Getting Transactions Grouped by Date

To get transactions grouped by date, use the following endpoint:

**GET** `http://localhost:8080/transactions/`

Optionally, you can provide a date value in the query parameter with the key `"lastTransactionTime"` and a value in the format `"2024-09-01T00:00:00"`. This will filter the transactions based on the given date.
