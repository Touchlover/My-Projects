# Capitec Bank - Customer Branch Appointment Booking System

A full-stack Spring Boot MVC web application developed for the Capitec assessment, allowing customers to schedule in-person branch appointments and receiving a simulated confirmation via automated SMS & Email dispatch.

---

## Tech Stack & Architecture

- **Backend Framework**: Spring Boot 3.3.5 (Spring MVC, Spring Data JPA, Hibernate ORM)
- **Embedded Web Server**: Apache Tomcat (Port `8080`)
- **Frontend / View Layer**: Thymeleaf, HTML5, Bootstrap 5, FontAwesome Icons (Responsive Capitec Theme)
- **Databases**: 
  - **MySQL 8.0**: Configured for local MySQL Workbench & Docker Compose (`customer_appointmentdb`)
  - **H2 In-Memory Database**: MySQL-compatibility mode (`MODE=MySQL`) active by default for immediate out-of-the-box evaluation without external database prerequisites
- **Containerization**: Docker multi-stage build & Docker Compose orchestration
- **Development Tooling**: Eclipse IDE / IntelliJ / VS Code, Maven (`mvnw`)

---

## Assessment Requirements Covered

1. **Customer Appointment Scheduling**:
   - Customer details input (Full name, Email, Phone, Account/ID number, Residential Address).
   - Real Capitec Branch selection (Cape Town Foreshore, Sandton City, Durban Central, Menlyn Park, Gateway).
   - Banking service type selection (Loan Consultation, Account Opening, Financial Planning, Card & PIN Services, Cash & Forex).
   - Date picker with tomorrow validation and selectable preferred time slots.
2. **Simulated Confirmation of Appointment**:
   - Immediately generates a unique Reference ID (e.g. `CPT-58291`).
   - Renders a printable/digital confirmation slip ticket.
   - **Simulated SMS Dispatch**: Displays simulated instant SMS confirmation sent to customer's mobile number.
   - **Simulated Email Dispatch**: Displays simulated rich email confirmation receipt sent to customer's email address.
3. **Appointment Tracking & Search**:
   - Customers can search by their appointment reference ID to check the current booking status (Confirmed, Pending, Cancelled, Completed).
4. **Operations & Branch Dashboard**:
   - Summary metric KPI cards: Total Appointments, Confirmed/Booked, Pending, Cancelled.
   - Comprehensive schedules table with inline status management (Confirm, Pending, Complete, Cancel) and delete capabilities.
5. **Customer & Admin Authentication**:
   - Customer account creation and sign-in.
   - Branch Administrator portal login.

---

## Default Test Credentials & Sample Data

### Branch Admin Login:
- **URL**: `http://localhost:8080/admin/login`
- **Email**: `admin@capitecbank.co.za`
- **Password**: `admin123`

### Sample Customer Login:
- **URL**: `http://localhost:8080/customer/login`
- **Email**: `sipho.nkosi@example.com`
- **Password**: `user123`

### Pre-loaded Sample Reference IDs to Test in Search:
- `CPT-58291` (Sipho Nkosi - Loan Consultation - Foreshore Branch)
- `CPT-61942` (Naledi Molefe - Account Opening - Sandton City Branch)
- `CPT-73014` (David Van Der Merwe - Financial Planning - Durban Central Branch)

---

## How to Run the Project

### Option 1: Direct Execution (Recommended & Currently Running)
Prerequisites: Java 21 is installed.

Run from PowerShell / Terminal in project directory:
```powershell
.\mvnw.cmd spring-boot:run
```
Or run the packaged JAR:
```powershell
java -jar target\customer-appointment-0.0.1-SNAPSHOT.jar
```
Access the application in your browser at:
**[http://localhost:8080](http://localhost:8080)**

### Option 2: Running in Eclipse IDE
1. Open Eclipse IDE.
2. Select **File > Import... > Existing Maven Projects**.
3. Browse to this directory (`customer-appointment`) and click **Finish**.
4. Right-click the project root > **Run As > Spring Boot App** (or run `CustomerAppointmentApplication.java`).
5. Open browser at `http://localhost:8080`.

### Option 3: Running with Docker & Docker Compose
To run both the application and a dedicated MySQL 8.0 database container:
```bash
docker compose up --build
```
This starts:
- `customer-appointment-mysql`: MySQL 8.0 container on port `3306` with database `customer_appointmentdb`
- `customer-appointment-web`: Spring Boot MVC container on port `8080` (waiting for MySQL to be healthy)

### Option 4: Inspecting Database in MySQL Workbench / H2 Console
- **H2 Web Console** (Built-in Web Workbench):
  - URL: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:customer_appointmentdb`
  - Username: `sa`
  - Password: *(leave blank)*
- **MySQL Workbench**:
  - Connect to `localhost:3306`
  - User: `root`
  - Password: `Touchlover@23`
  - Database: `customer_appointmentdb`
  - Run the app with `--spring.profiles.active=mysql` to persist directly to MySQL.

---

## Key Endpoints Map

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/` | GET | Capitec Landing Page & service overview |
| `/appointment` | GET | Appointment scheduling form |
| `/appointment` | POST | Submits appointment and renders simulated confirmation |
| `/confirmation/{id}` | GET | Displays confirmation ticket slip with simulated SMS/Email |
| `/search` | GET/POST | Search & track appointment by reference ID |
| `/customer/login` | GET/POST | Customer login portal |
| `/customer/create` | GET/POST | Customer registration portal |
| `/admin/login` | GET/POST | Branch Administrator login |
| `/dashboard` | GET | Admin operations dashboard with KPIs and table |
| `/dashboard/update-status`| POST | Updates appointment status (Confirmed, Pending, etc.) |
| `/dashboard/delete/{id}` | GET | Deletes an appointment |
| `/h2-console` | GET | In-memory web database browser |

