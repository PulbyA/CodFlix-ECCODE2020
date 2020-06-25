-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jun 06, 2020 at 08:35 AM
-- Server version: 5.7.30
-- PHP Version: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `codflix`
--

create database IF NOT EXISTS CodFlix;
use CodFlix;

-- --------------------------------------------------------

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
CREATE TABLE `media` (
  `id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `type` varchar(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `release_date` date NOT NULL,
  `summary` longtext NOT NULL,
  `trailer_url` varchar(100) NOT NULL,
  `director` varchar(50) NOT NULL,
  `duration` int(11) NOT NULL DEFAULT '0' COMMENT 'in seconds'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `media` (`id`, `genre_id`, `title`, `type`, `status`, `release_date`, `summary`, `trailer_url`, `director`, `duration`) VALUES
(1, 2, "Get Out", "movie", "released", "2017/03/16", "Couple mixte, Chris et sa petite amie Rose filent le parfait amour. Le moment est donc venu de rencontrer la belle famille, Missy et Dean lors d'un week-end sur leur domaine dans le nord de l'État. Chris commence par penser que l'atmosphère tendue est liée à leur différence de couleur de peau, mais très vite une série d'incidents de plus en plus inquiétants lui permet de découvrir l'inimaginable.", "https://www.youtube.com/embed/mGOypegrMr4", "Jordan Peele", 6240),

(2, 5, "The Shining", "movie", "released", "1980/10/16", "Écrivain, Jack Torrance est engagé comme gardien, pendant tout l'hiver, d'un grand hôtel isolé du Colorado – l'Overlook – où il espère surmonter enfin sa panne d'inspiration. Il s'y installe avec sa femme Wendy et son fils Danny, doté d'un don de médium. Tandis que Jack n'avance pas dans son livre et que son fils est de plus en plus hanté par des visions terrifiantes, il découvre les terribles secrets de l'hôtel et bascule peu à peu dans une forme de folie meurtrière où il s'en prend à sa propre famille…", "https://www.youtube.com/embed/DL_GjZglYz8", "Stanley Kubrick", 8580),

(3, 8, "Spider-Man : New Generation", "movie", "released", "2018/12/12", "Spider-Man : New Generation suit les aventures de Miles Morales, un adolescent afro-américain et portoricain qui vit à Brooklyn et s'efforce de s'intégrer dans son nouveau collège à Manhattan. Mais la vie de Miles se complique quand il se fait mordre par une araignée radioactive et se découvre des super-pouvoirs : il est désormais capable d'empoisonner ses adversaires, de se camoufler, de coller littéralement aux murs et aux plafonds ; son ouïe est démultipliée... Dans le même temps, le plus redoutable cerveau criminel de la ville, le Caïd, a mis au point un accélérateur de particules nucléaires capable d'ouvrir un portail sur d'autres univers. Son invention va provoquer l'arrivée de plusieurs autres versions de Spider-Man dans le monde de Miles, dont un Peter Parker plus âgé, Spider-Gwen, Spider-Man Noir, Spider-Cochon et Peni Parker, venue d'un dessin animé japonais.", "https://www.youtube.com/embed/3CrxSs6NPsc", "Peter Ramsey", 7020),

(4, 3, "Le Visiteur du Futur", "serie", "released", "2009/04/27", "Raph est un jeune homme paisible et sans histoire, jusqu'à un jour de 2009 où, alors qu'il flâne avec ses deux amis Tim et Leo, un étrange personnage ensanglanté apparaît comme par magie devant lui grâce à une machine qu'il porte en bracelet. Prétendant venir du futur, celui-ci le met en garde à de multiples reprises sur les conséquences désastreuses que peuvent avoir ses gestes, surtout les plus anodins, dans un futur lointain. Après de multiples intrusions du Visiteur dans sa vie, Raph subit des interventions de deux étranges personnages qui se présentent comme étant la Brigade temporelle. Ainsi, il fait la connaissance de Mattéo, un agent qui capture le Visiteur, et de Judith, sa supérieure. Leurs buts : découvrir les objectifs de ce mystérieux visiteur qui pourtant a première vue semble agir sans le moindre discernement…", "https://www.youtube.com/embed/GK1JEm0YstE", "François Descraques", 0)
;

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`id`, `name`) VALUES
(1, 'Action'),
(2, 'Horreur'),
(3, 'Science-Fiction'),
(4, 'Policier'),
(5, 'Thriller'),
(6, 'Super-héros'),
(7, 'Fantasy'),
(8, 'Animation'),
(9, 'Action-Aventure')
;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `media_id` int(11) NOT NULL,
  `episode_id` int(11),
  `start_date` datetime NOT NULL,
  `finish_date` datetime DEFAULT NULL,
  `watch_duration` int(11) NOT NULL DEFAULT '0' COMMENT 'in seconds'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(254) NOT NULL,
  `password` varchar(80) NOT NULL,
  `isVerified` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `isVerified`) VALUES
(1, 'coding@gmail.com', '123456', false);

--
-- Table structure for table `episode`
--

DROP TABLE IF EXISTS `episode`;
CREATE TABLE `episode` (
  `id` int(11) NOT NULL,
  `media_id` int(11) NOT NULL,
  `season` int(11) NOT NULL,
  `episode` int(11) NOT NULL,
  `episode_url` VARCHAR (254) NOT NULL,
  `title` VARCHAR(50) NOT NULL,
  `summary` VARCHAR(50) NOT NULL,
  `release_date` VARCHAR(50) NOT NULL,
  `duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table `episode`
--

INSERT INTO `episode` (`id`, `media_id`, `season`, `episode`, `episode_url`, `title`, `summary`, `release_date`, `duration`) VALUES
(1, 4, 1, 1, "https://www.youtube.com/embed/1wtGLc_Fvf0", "La Canette", "1x01", "2010-10-03", 132),
(2, 4, 1, 2, "https://www.youtube.com/embed/IHA0P_QRHJo", "La Pizza", "1x02", "2010-10-04", 115),
(3, 4, 1, 3, "https://www.youtube.com/embed/XmTVJaik91o", "La Copine", "1x03", "2010-10-04", 151),
(4, 4, 1, 4, "https://www.youtube.com/embed/MOa4EURDSd8", "Le Casse-Dalle", "1x04", "2010-10-04", 131),
(5, 4, 1, 5, "https://www.youtube.com/embed/4xN6UFn1FZ0", "Le Policier", "1x05", "2010-10-05", 157),
(6, 4, 1, 6, "https://www.youtube.com/embed/CdJn6tgvDGc", "Le Policier Bis", "1x06", "2010-10-05", 156),
(7, 4, 1, 7, "https://www.youtube.com/embed/jFqtrD5FwBs", "La Réalité", "1x07", "2010-10-08", 226),
(8, 4, 1, 8, "https://www.youtube.com/embed/HTAuJC3uV7o", "Le Plan", "1x08", "2010-10-08", 193),
(9, 4, 2, 1, "https://www.youtube.com/embed/akRGciMZaDs", "Reboot (partie 1)", "2x01", "2010-11-16", 533),
(10, 4, 2, 2, "https://www.youtube.com/embed/YuDdR79QP78", "Reboot (partie 2)", "2x02", "2011-01-20", 499)
;
--
-- Indexes for dumped tables
--

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`id`),
  ADD KEY `media_genre_id_fk_genre_id` (`genre_id`) USING BTREE;


--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `history_user_id_fk_user_id` (`user_id`),
  ADD KEY `history_media_id_fk_media_id` (`media_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `episode`
--
ALTER TABLE `episode`
  ADD PRIMARY KEY (`id`),
  ADD KEY `episode_media_id_fk_media_id` (`media_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `media`
--
ALTER TABLE `media`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;


--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `episode`
--
ALTER TABLE `episode`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `media_genre_id_b1257088_fk_genre_id` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`);

--
-- Constraints for table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `history_media_id_fk_media_id` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `history_user_id_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `episode`
--
ALTER TABLE `episode`
  ADD CONSTRAINT `episode_mediaid_fk_media_id` FOREIGN KEY (`media_id`) REFERENCES `media` (`id`);


COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
