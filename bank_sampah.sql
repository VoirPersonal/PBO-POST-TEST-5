-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 05, 2025 at 06:42 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank_sampah`
--

-- --------------------------------------------------------

--
-- Table structure for table `sampah`
--

CREATE TABLE `sampah` (
  `id` int(11) NOT NULL,
  `jenis` varchar(100) NOT NULL,
  `berat` double NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `tingkat_dekomposisi` varchar(20) DEFAULT NULL,
  `dapat_didaur_ulang` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sampah`
--

INSERT INTO `sampah` (`id`, `jenis`, `berat`, `kategori`, `tingkat_dekomposisi`, `dapat_didaur_ulang`) VALUES
(1, 'Sisa Sayuran', 2.5, 'Organik', 'Cepat', NULL),
(2, 'Daun Kering', 3, 'Organik', 'Sedang', NULL),
(3, 'Kulit Buah', 1.8, 'Organik', 'Cepat', NULL),
(4, 'Ranting Kayu', 4.2, 'Organik', 'Lambat', NULL),
(5, 'Sisa Nasi', 1.5, 'Organik', 'Cepat', NULL),
(6, 'Ampas Kopi', 0.8, 'Organik', 'Sedang', NULL),
(7, 'Tulang Ayam', 1.2, 'Organik', 'Lambat', NULL),
(8, 'Rumput', 2.3, 'Organik', 'Sedang', NULL),
(9, 'Sekam Padi', 5, 'Organik', 'Sedang', NULL),
(10, 'Daun Pisang', 1, 'Organik', 'Cepat', NULL),
(11, 'Botol Plastik', 1.5, 'Anorganik', NULL, 1),
(12, 'Kaleng Aluminium', 2, 'Anorganik', NULL, 1),
(13, 'Kertas Bekas', 3.5, 'Anorganik', NULL, 1),
(14, 'Kardus', 4, 'Anorganik', NULL, 1),
(15, 'Botol Kaca', 2.8, 'Anorganik', NULL, 1),
(16, 'Styrofoam', 0.5, 'Anorganik', NULL, 0),
(17, 'Plastik Kresek', 0.3, 'Anorganik', NULL, 0),
(18, 'Baterai Bekas', 0.2, 'Anorganik', NULL, 0),
(19, 'Kabel Bekas', 1, 'Anorganik', NULL, 1),
(20, 'Pecahan Keramik', 3, 'Anorganik', NULL, 0),
(21, 'Sendok Plastik', 0.4, 'Anorganik', NULL, 1),
(22, 'Kantong Plastik Tebal', 0.6, 'Anorganik', NULL, 1),
(23, 'Kemasan Sachet', 0.1, 'Anorganik', NULL, 0),
(24, 'Galon Plastik', 1.2, 'Anorganik', NULL, 1),
(25, 'Kawat Tembaga', 0.8, 'Anorganik', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sampah`
--
ALTER TABLE `sampah`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sampah`
--
ALTER TABLE `sampah`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
