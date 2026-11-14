# TastyHub — Food Ordering and Management System

**Academic project — group work by Group 3, developed by Nyamanyama Essenine.** TastyHub is a standalone desktop application that lets customers order food and lets administrators manage the menu, orders and customer complaints. It was built for the Object Oriented Programming course (COS 2203) using Java Swing, JDBC and a MySQL database.

TastyHub is **not** a commercial service, and all data in the demonstration is sample data.

## What the system can do

**Customers**
- Register and log in
- Browse the menu with food photos
- Add items to an order and place it
- Submit a complaint about an order or service

**Administrators**
- Log in to an admin dashboard
- View and manage customers and orders
- Review and respond to customer complaints

## Technologies

| Layer | Technology |
|-------|------------|
| Front end (GUI) | Java Swing |
| Back end (logic) | Java |
| Database connectivity | JDBC |
| Database | MySQL (`database/schema.sql`) |
| Build / IDE | Maven, Apache NetBeans |

## Project structure

| Folder / file | Purpose |
|---------------|---------|
| `src/` | Java source code and food images |
| `database/` | Database schema and setup notes |
| `docs/` | Project documentation |
| `pom.xml` | Maven build configuration |

## Project team — Group 3

**Developer:** Nyamanyama Essenine (2025-08-40571), on behalf of Group 3.

Kampala International University · School of Mathematics and Computing · Bachelor of Information Technology · Course unit: **Object Oriented Programming (COS 2203)**, Year 2 Semester 1 · Lecturer: **Dr. A. O. Ejidokun**

### Group 3 members

| No. | Reg. number | Member name | Course |
|-----|-------------|-------------|--------|
| 1 | 2025-08-41202 | Nakabuye Maria | BIT |
| 2 | 2025-08-40254 | Nahurira Brair | BIT |
| 3 | 2025-08-40189 | Mubangizi Ian | BIT |
| 4 | 2025-08-40214 | Kisitu Philip | BIT |
| 5 | 2025-08-40434 | Auma Mary Goretty | BIT |
| 6 | 2025-08-40621 | Mukisa Teddy | BIT |
| 7 | 2025-08-40457 | Akuma Dalil | BIT |
| 8 | 2025-08-41277 | Nakayiira Bridget | BIT |
| 9 | 2025-08-40975 | Muwanguzi Charles | BIT |
| 10 | 2025-08-40373 | Nasirumbi Catherine | BIT |
| 11 | 2025-08-41250 | Nabukenya Jamirah | BIT |
| 12 | 2025-08-41258 | Kirabo Stynah | BIT |
| 13 | 2025-08-41431 | Ampeire Sharrot | BIT |
| 14 | 2025-08-40286 | Habiba Nidat Kasambura | BIT |
| 15 | 2025-08-40657 | Ochen Daniel | BIT |
| 16 | 2025-08-40571 | Nyamanyama Essenine | BIT |
| 17 | 2025-08-40630 | Angwena Yerusa Mercy | BIT |
| 18 | 2025-08-41236 | Muhanuzi Lyon | BIT |
| 19 | 2025-08-41257 | Nabirye Hailate Mugweri | BIT |
| 20 | 2025-08-40840 | Ssalabwa Joel | BIT |
| 21 | 2025-08-40748 | Nkamushaba Ruth | BIT |
| 22 | 2025-08-40411 | Namubiru Mariagoreth | BIT |
| 23 | 2025-08-40946 | Ssemuli Keneddy | BIT |
## Requirements

- JDK 21 or later
- Apache Maven (or Maven support in Apache NetBeans)
- MySQL 8.x or a compatible MySQL server
- Apache NetBeans (recommended, but not required)

## Setup in Apache NetBeans

1. Download or clone this repository.
2. If downloaded as a ZIP, extract it first.
3. In NetBeans, select **File → Open Project**.
4. Select the `TastyHub` folder containing `pom.xml`.
5. Allow Maven to resolve the project dependencies.
6. Create or restore the MySQL database named `tastyhub_db`.
7. Configure the database connection using the instructions below.
8. Right-click the project and choose **Clean and Build**.
9. Run the project. The application entry point is `com.myproject.tastyhub.TastyHub`.

## Database configuration

The default local-development connection is:

```text
jdbc:mysql://localhost:3306/tastyhub_db?zeroDateTimeBehavior=CONVERT_TO_NULL
```

The local XAMPP defaults are username `root` and an empty password. These are development defaults only. For another MySQL account, configure these environment variables before starting the application:

- `TASTYHUB_DB_URL`
- `TASTYHUB_DB_USER`
- `TASTYHUB_DB_PASSWORD`

Alternatively, use the JVM properties `-Dtastyhub.db.url=...`, `-Dtastyhub.db.user=...` and `-Dtastyhub.db.password=...`.

**Database schema status:** A verified SQL schema export was not included in the supplied project files. The `database/` folder contains `schema.sql`, prepared from the supplied phpMyAdmin export. It includes the five exported tables and menu seed data, while excluding personal customer, order and complaint records. Test the script against a clean local database and verify the DAO queries before treating it as a production deployment schema.

Never commit real credentials, customer information or an unredacted database dump.

## Demo administrator account

The application includes a demonstration administrator fallback:

- Username: `admin`
- Password: `TastyHub123`

The demo credentials can be overridden using `TASTYHUB_ADMIN_USER` and `TASTYHUB_ADMIN_PASSWORD`. This is a coursework demonstration mechanism, not production-grade authentication. A deployed system should use securely hashed passwords, database-backed accounts and appropriate role-based access control.

## Building from the command line

```bash
mvn clean package
```

The application can be launched from NetBeans using the configured main class. A command-line launch should use the project's Maven execution configuration and a correctly configured database.

## Project team and academic acknowledgement

TastyHub was developed collaboratively by **Group 3** for:

- **Institution:** Kampala International University (KIU)
- **Programme:** Bachelor of Information Technology (BIT)
- **Year/Semester:** Year 2:1
- **Course:** COS 2203 — Object Oriented Programming
- **Lecturer:** Dr. A. O. Ejidokun
- **Group leader:** NYAMANYAMA ESSENINE

See [docs/TEAM.md](docs/TEAM.md) for the full team credits.

## Limitations and future improvements

- Continue testing the supplied SQL schema against a clean local database and the DAO operations.
- Expand automated tests for DAO operations and order/payment workflows.
- Strengthen administrator authentication and authorisation for any non-demo deployment.
- Add further input validation, error handling and operational documentation.

## Academic use

This repository documents a collaborative coursework project. Team members are credited collectively; individual responsibilities are not attributed unless the team confirms an agreed contribution breakdown.
