-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 24, 2024 at 07:05 PM
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
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` varchar(10) NOT NULL,
  `nama_kategori` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `nama_kategori`) VALUES
('', 'NARKOBA'),
('002', 'OBAT GENERIK'),
('003', 'HERBAL'),
('004', 'OBAT JAMU');

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id_obat` varchar(10) NOT NULL,
  `nama_obat` varchar(30) NOT NULL,
  `id_kategori` varchar(10) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `tgl_kadaluarsa` date NOT NULL,
  `harga` int(11) NOT NULL,
  `bentuk_sediaan` varchar(255) NOT NULL,
  `dosis` varchar(255) NOT NULL,
  `supplier` varchar(255) NOT NULL,
  `efek_samping` text NOT NULL,
  `kontraindikasi` text NOT NULL,
  `instruksi_penggunaan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id_obat`, `nama_obat`, `id_kategori`, `kategori`, `tgl_kadaluarsa`, `harga`, `bentuk_sediaan`, `dosis`, `supplier`, `efek_samping`, `kontraindikasi`, `instruksi_penggunaan`) VALUES
('DJB007', 'Daun Jati Belanda', '003', 'HERBAL', '2024-09-30', 25000, 'Kapsul', '300 mg', 'PT. Herbal Nusantara', 'Tidak diketahui secara spesifik', 'Tidak direkomendasikan untuk wanita hamil atau menyusui tanpa pengawasan medis', 'Diminum 2 kapsul sehari setelah makan'),
('MET006', 'Metformin', '002', 'OBAT GENERIK', '2024-10-31', 16000, 'Tablet', '500 mg', 'PT Generik Sejahtera', 'Gangguan pencernaan, Mual', 'Pasien dengan gangguan ginjal', 'Diminum 2 kali sehari setelah makan'),
('TEM009', 'Temulawak', '003', 'HERBAL', '2024-11-29', 18000, 'Ekstrak Kapsul', '500 mg', 'PT. Herbal Lestari', 'Tidak ada efek samping yang signifikan', 'Tidak ada kontraindikasi yang spesifik, tetapi disarankan untuk konsultasi dengan ahli herbal sebelum penggunaan jangka panjang', 'Diminum 3 kali sehari setelah makan'),
('TOL008', 'Tolak Angin', '004', 'OBAT JAMU', '2024-12-31', 12000, 'Cairan Minum', '10 ml', 'PT. Jamu Sehat', 'Tidak ada efek samping yang signifikan', 'Tidak dianjurkan untuk anak di bawah 12 tahun', 'Diminum 2 kali sehari setelah makan');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` varchar(10) NOT NULL,
  `nama_petugas` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama_petugas`, `username`, `password`) VALUES
('001', 'Riyan', 'riyan', '123'),
('002', 'Wili', 'wili', '111'),
('003', 'Bimo', 'nugroho', '321'),
('004', 'Yanto', 'yangeming', '222');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_detail`
--

CREATE TABLE `transaksi_detail` (
  `id_transaksi` varchar(10) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `id_obat` varchar(10) NOT NULL,
  `nama_obat` varchar(20) NOT NULL,
  `harga` int(10) NOT NULL,
  `jml_beli` int(10) NOT NULL,
  `total` int(10) NOT NULL,
  `tgl_transaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`id_obat`),
  ADD KEY `FK_obat` (`id_kategori`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indexes for table `transaksi_detail`
--
ALTER TABLE `transaksi_detail`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `FK_transaksi_detail` (`id_obat`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `obat`
--
ALTER TABLE `obat`
  ADD CONSTRAINT `FK_obat` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_detail`
--
ALTER TABLE `transaksi_detail`
  ADD CONSTRAINT `FK_transaksi_detail` FOREIGN KEY (`id_obat`) REFERENCES `obat` (`id_obat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;