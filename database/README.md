# TastyHub database setup

This folder contains `schema.sql`, prepared from the Group 3 project's supplied phpMyAdmin export. It creates the `tastyhub_db` database and the five tables present in that export:

- `customers`
- `food_items`
- `order`
- `order_items`
- `delivery_complaints`

The script retains the food menu seed records but deliberately excludes customer names, phone numbers, delivery addresses, orders, order items and complaint records from the supplied live/test export. Do not publish an unredacted database dump.

## Import with XAMPP / phpMyAdmin

1. Start **Apache** and **MySQL** in the XAMPP Control Panel.
2. Open `http://localhost/phpmyadmin`.
3. Select the **Import** tab.
4. Choose `schema.sql` from this folder.
5. Click **Import** / **Go** and wait for the success message.
6. Confirm that `tastyhub_db` appears and contains the five tables listed above.

The script uses `CREATE DATABASE IF NOT EXISTS` and then selects `tastyhub_db`, but its `CREATE TABLE` statements expect those tables not to exist yet. Import it into a new, empty `tastyhub_db` database. If you already have a working database with these tables, do not import this script over it; back up your database first or use a separate test database.

## Connection

The application's default local JDBC URL is documented in the root README. Configure the database username and password for your own MySQL installation; never commit credentials.

## Important verification note

This SQL file reflects the tables, columns, indexes, foreign keys and menu rows in the export supplied for repository preparation. Before using it as a fresh-install guarantee, test it against a clean local MariaDB/MySQL database and verify every DAO query in the Java application. The export contains five tables; no additional payment table was present in the supplied SQL file.
