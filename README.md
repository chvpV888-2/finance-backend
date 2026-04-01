# Finance Data Processing and Access Control Backend

A robust backend service designed for a finance dashboard system. This API handles the storage and management of financial entries, enforces strictly defined Role-Based Access Control (RBAC), calculates dashboard aggregations, and rigorously validates input data.

## 🛠️ Tech Stack
* **Language:** Java 21
* **Framework:** Spring Boot 3.2
* **Security:** Spring Security (Basic Auth)
* **Database:** H2 (In-Memory for zero-setup evaluation)
* **Build Tool:** Gradle

## 🚀 Key Features Implemented
1. **Role-Based Access Control (RBAC):** Three distinct user roles (`VIEWER`, `ANALYST`, `ADMIN`) with strict endpoint authorization.
2. **Financial Records Management:** Full CRUD capabilities for financial transactions.
3. **Dashboard Aggregations:** Database-level aggregations (SUM) to calculate Total Income, Total Expenses, and Net Balance.
4. **Data Validation:** Strict payload validation (`@NotNull`, `@Positive`, `@NotBlank`) ensuring data integrity before database persistence.

## ⚙️ How to Run Locally

This project uses an in-memory H2 database, meaning **no database installation is required**.

1. Clone the repository and navigate to the project root.
2. Run the application using the Gradle wrapper:
    * **Windows:** `.\gradlew bootRun`
    * **Mac/Linux:** `./gradlew bootRun`
3. The server will start on `http://localhost:8080`.

## 🔐 Authentication & Roles
For evaluation purposes, three in-memory users have been pre-configured using HTTP Basic Authentication:

| Username | Password | Role    | Permissions                                    |
| :---     | :---     | :---    |:-----------------------------------------------|
| `viewer` | `pass`   | VIEWER  | Can only READ records.                         |
| `analyst`| `pass`   | ANALYST | Can READ records and view Dashboard Summaries. |
| `admin`  | `pass`   | ADMIN   | Full access (CREATE, READ, DELETE).            |

## 📡 Core API Endpoints

### 1. Financial Records
* `GET /api/records` - Fetch all records *(Allowed: Viewer, Analyst, Admin)*
* `POST /api/records` - Create a new record *(Allowed: Admin ONLY)*
* `DELETE /api/records/{id}` - Delete a record *(Allowed: Admin ONLY)*

**Sample POST Payload:**
```json
{
  "amount": 1500.50,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "notes": "April paycheck"
}
2. Dashboard Summary
GET /api/dashboard/summary - Returns aggregated financial data (Allowed: Analyst, Admin)

Sample Response:

JSON
{
  "totalIncome": 1500.50,
  "totalExpenses": 0,
  "netBalance": 1500.50
}
🧠 Assumptions & Trade-offs
In-Memory Database: Chosen over PostgreSQL to allow reviewers to run the application immediately without configuring local database servers.

Authentication: Implemented HTTP Basic Auth rather than JWTs to keep the evaluation focused on the core RBAC logic and routing structure rather than token lifecycle management.

Soft Deletes: Omitted for simplicity in V1, utilizing hard deletes for standard CRUD demonstration.

## 📚 API Documentation (Swagger UI)
This project includes auto-generated, interactive API documentation. You do not need Postman to test the endpoints. 

Once the application is running, you can view and test all APIs directly in your browser by visiting:
* **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)