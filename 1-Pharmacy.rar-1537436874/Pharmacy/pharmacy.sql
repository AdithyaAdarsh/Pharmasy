-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 06, 2018 at 11:57 AM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacy`
--

-- --------------------------------------------------------

--
-- Table structure for table `customertable`
--

CREATE TABLE `customertable` (
  `Id` int(11) NOT NULL,
  `CustomerName` varchar(50) NOT NULL,
  `CustomerContact` varchar(50) NOT NULL,
  `CustomerAddress` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customertable`
--

INSERT INTO `customertable` (`Id`, `CustomerName`, `CustomerContact`, `CustomerAddress`) VALUES
(1, 'smith', '12312312332', 'xyz xyz xyz ');

-- --------------------------------------------------------

--
-- Table structure for table `dealertable`
--

CREATE TABLE `dealertable` (
  `Id` int(11) NOT NULL,
  `DealerName` varchar(50) NOT NULL,
  `DealerContact` varchar(50) NOT NULL,
  `DealerAddress` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dealertable`
--

INSERT INTO `dealertable` (`Id`, `DealerName`, `DealerContact`, `DealerAddress`) VALUES
(1, 'john', '12312312321', 'xyz xyzy xyz');

-- --------------------------------------------------------

--
-- Table structure for table `inventorytable`
--

CREATE TABLE `inventorytable` (
  `Id` int(11) NOT NULL,
  `ProductName` varchar(50) NOT NULL,
  `BatchNumber` varchar(50) NOT NULL,
  `Quantity` varchar(50) NOT NULL,
  `PurchaseDate` date DEFAULT NULL,
  `Salt` varchar(100) NOT NULL,
  `PurchaseRate` double NOT NULL DEFAULT '0',
  `SellingRate` double NOT NULL DEFAULT '0',
  `ExpiryDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inventorytable`
--

INSERT INTO `inventorytable` (`Id`, `ProductName`, `BatchNumber`, `Quantity`, `PurchaseDate`, `Salt`, `PurchaseRate`, `SellingRate`, `ExpiryDate`) VALUES
(1, 'dolo 450', '32', '40', '2018-09-06', 'potasium', 23, 24, '2018-09-07');

-- --------------------------------------------------------

--
-- Table structure for table `purchasetable`
--

CREATE TABLE `purchasetable` (
  `Id` int(11) NOT NULL,
  `MedicineName` varchar(50) NOT NULL,
  `DealerName` varchar(100) NOT NULL,
  `BatchNumber` varchar(50) NOT NULL,
  `Quantity` varchar(50) NOT NULL,
  `Salt` varchar(100) NOT NULL,
  `PurchaseDate` datetime DEFAULT NULL,
  `PurchaseRate` double NOT NULL DEFAULT '0',
  `SellingRate` double NOT NULL DEFAULT '0',
  `ExpiryDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchasetable`
--

INSERT INTO `purchasetable` (`Id`, `MedicineName`, `DealerName`, `BatchNumber`, `Quantity`, `Salt`, `PurchaseDate`, `PurchaseRate`, `SellingRate`, `ExpiryDate`) VALUES
(1, 'dolo 450', 'john', '32', '43', 'potasium', '2018-09-06 15:27:13', 23, 24, '2018-09-07');

-- --------------------------------------------------------

--
-- Table structure for table `saleitemtable`
--

CREATE TABLE `saleitemtable` (
  `InvoId` int(11) NOT NULL,
  `ProductName` varchar(50) NOT NULL,
  `Batch` varchar(50) NOT NULL,
  `Quantity` double NOT NULL DEFAULT '0',
  `Amount` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `saleitemtable`
--

INSERT INTO `saleitemtable` (`InvoId`, `ProductName`, `Batch`, `Quantity`, `Amount`) VALUES
(1, 'dolo 450', '32', 3, 72);

-- --------------------------------------------------------

--
-- Table structure for table `saletable`
--

CREATE TABLE `saletable` (
  `Id` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `Customer` varchar(150) NOT NULL,
  `TotalAmount` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `saletable`
--

INSERT INTO `saletable` (`Id`, `Date`, `Customer`, `TotalAmount`) VALUES
(1, '2018-09-06 15:27:40', 'smith', 72);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customertable`
--
ALTER TABLE `customertable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `dealertable`
--
ALTER TABLE `dealertable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `inventorytable`
--
ALTER TABLE `inventorytable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `purchasetable`
--
ALTER TABLE `purchasetable`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `saleitemtable`
--
ALTER TABLE `saleitemtable`
  ADD KEY `InvoId` (`InvoId`),
  ADD KEY `par_ind` (`InvoId`);

--
-- Indexes for table `saletable`
--
ALTER TABLE `saletable`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customertable`
--
ALTER TABLE `customertable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `dealertable`
--
ALTER TABLE `dealertable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `inventorytable`
--
ALTER TABLE `inventorytable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `purchasetable`
--
ALTER TABLE `purchasetable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `saletable`
--
ALTER TABLE `saletable`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `saleitemtable`
--
ALTER TABLE `saleitemtable`
  ADD CONSTRAINT `fk_branchTable` FOREIGN KEY (`InvoId`) REFERENCES `saletable` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
