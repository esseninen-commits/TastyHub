# TastyHub

**TastyHub** is a Java desktop food-ordering and restaurant-administration application developed as a collaborative academic project by **Group 3** at Kampala International University.

The application brings customer ordering and restaurant administration into one desktop system. It uses Java Swing for the graphical user interface, JDBC for database access and MySQL for persistent data storage.

## Project overview

TastyHub was created as an Object Oriented Programming coursework project to demonstrate how a desktop application can organise food ordering and related restaurant-management tasks. Its codebase separates domain objects from database access classes and user-interface windows.

## Features

- Food menu and customer order placement
- Customer and order management
- Payment recording
- Delivery time estimates (ETA)
- Delivery complaint submission and administration
- Administrator login and dashboard
- Food management
- Sales reporting
- WhatsApp support entry point

## Technology stack

| Technology | Purpose |
|---|---|
| Java | Application programming language |
| Java Swing | Desktop graphical user interface |
| JDBC | Java-to-database connectivity |
| MySQL | Relational database |
| Apache Maven | Dependency and build management |
| MySQL Connector/J | MySQL JDBC driver |
| Apache NetBeans | Recommended development environment |

## Project structure

```text
TastyHub/
├── pom.xml
├── README.md
├── .gitignore
├── database/
│   └── README.md
├── docs/
│   └── TEAM.md
└── src/
    ├── main/
    │   ├── java/com/myproject/tastyhub/
    │   └── resources/images/
    └── test/java/com/myproject/tastyhub/
```

The existing Java package structure and GUI class names have been retained to minimise the risk of disrupting the working coursework application.

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
