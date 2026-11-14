-- TastyHub database schema and public menu seed data
-- Source: Group 3 TastyHub phpMyAdmin export (reviewed and privacy-cleaned)
-- Personal customer, order and complaint records have been intentionally excluded.
-- Import into a NEW local database only; do not overwrite a live database.

CREATE DATABASE IF NOT EXISTS `tastyhub_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tastyhub_db`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

START TRANSACTION;

SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tastyhub_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `Customer_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `phoneNo` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `delivery_area` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `delivery_complaints`
--

CREATE TABLE `delivery_complaints` (
  `complaint_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `complaint_type` varchar(50) NOT NULL DEFAULT 'Delivery Delay',
  `complaint_message` text NOT NULL,
  `complaint_date` datetime NOT NULL DEFAULT current_timestamp(),
  `complaint_status` varchar(30) NOT NULL DEFAULT 'Open'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `food_items`
--

CREATE TABLE `food_items` (
  `Food_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `category` varchar(50) NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_items`
--

INSERT INTO `food_items` (`Food_id`, `name`, `price`, `category`, `available`) VALUES
(1, 'Chicken and Chips', 15000, 'Main Meal', 1),
(2, 'Beef Burger', 12000, 'Main Meal', 1),
(3, 'Pizza', 20000, 'Main Meal', 1),
(4, 'Chips', 5000, 'Side', 1),
(5, 'Soda', 2000, 'Drink', 1),
(6, 'Grilled Chicken', 18000, 'Main Meal', 1),
(7, 'Beef Wrap', 10000, 'Main Meal', 1),
(8, 'Chicken Burger', 14000, 'Main Meal', 1),
(9, 'Beef pilau', 12000, 'Main Meal', 1),
(10, 'Chicken Wings', 15000, 'Main Meal', 1),
(11, 'Sausage and Chips', 8000, 'Side', 1),
(12, 'Somosas', 4000, 'Side', 1),
(13, 'Fresh Juice', 5000, 'Drink', 1),
(14, 'Bottled Water', 1500, 'Drink', 1),
(15, 'Milkshake', 7000, 'Drink', 1),
(16, 'MTK', 60000, 'Main Meal', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `Order_id` int(11) NOT NULL,
  `Customer_id` int(11) NOT NULL,
  `Order_date` datetime NOT NULL DEFAULT current_timestamp(),
  `delivery_area` varchar(100) NOT NULL,
  `delivery_address` varchar(255) NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'Pending',
  `total_amount` decimal(10,2) NOT NULL DEFAULT 0.00,
  `payment_method` varchar(30) NOT NULL DEFAULT 'Not Selected',
  `payment_status` varchar(20) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `Order_item_id` int(11) NOT NULL,
  `Order_id` int(11) NOT NULL,
  `food_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`Customer_id`);

--
-- Indexes for table `delivery_complaints`
--
ALTER TABLE `delivery_complaints`
  ADD PRIMARY KEY (`complaint_id`),
  ADD KEY `fk_complaint_order` (`order_id`);

--
-- Indexes for table `food_items`
--
ALTER TABLE `food_items`
  ADD PRIMARY KEY (`Food_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`Order_id`),
  ADD KEY `Customer_id` (`Customer_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`Order_item_id`),
  ADD KEY `Order_id` (`Order_id`),
  ADD KEY `food_id` (`food_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `Customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `delivery_complaints`
--
ALTER TABLE `delivery_complaints`
  MODIFY `complaint_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `food_items`
--
ALTER TABLE `food_items`
  MODIFY `Food_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `Order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `Order_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `delivery_complaints`
--
ALTER TABLE `delivery_complaints`
  ADD CONSTRAINT `fk_complaint_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`Order_id`) ON UPDATE CASCADE;

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `order_ibfk_1` FOREIGN KEY (`Customer_id`) REFERENCES `customers` (`Customer_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`Order_id`) REFERENCES `order` (`Order_id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`food_id`) REFERENCES `food_items` (`Food_id`);

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
