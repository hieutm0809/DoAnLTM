-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 04, 2020 lúc 06:25 PM
-- Phiên bản máy phục vụ: 10.4.14-MariaDB
-- Phiên bản PHP: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `chatproject`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `blocklist`
--

CREATE TABLE `blocklist` (
  `userID` int(11) NOT NULL,
  `username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `blocklist`
--

INSERT INTO `blocklist` (`userID`, `username`) VALUES
(7, '[5,6]');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `friendlist`
--

CREATE TABLE `friendlist` (
  `userID` int(11) NOT NULL,
  `username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `friendlist`
--

INSERT INTO `friendlist` (`userID`, `username`) VALUES
(4, '[5,6]');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `groupchat`
--

CREATE TABLE `groupchat` (
  `groupID` int(11) NOT NULL,
  `groupname` varchar(20) CHARACTER SET utf8 NOT NULL,
  `memberlist` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `groupchat`
--

INSERT INTO `groupchat` (`groupID`, `groupname`, `memberlist`) VALUES
(1, 'tomato', '[4,6]'),
(2, 'apple', '[4,5,7]');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `messageID` int(11) NOT NULL,
  `participant1` int(11) NOT NULL,
  `participant2` int(11) NOT NULL,
  `content` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`messageID`, `participant1`, `participant2`, `content`) VALUES
(1, 4, 5, '[{\"from\": 4,\"time\": \"2020 / 01 / 04 12: 00: 00: 00 AM\",\"content\": \"Đây là nội dung tin nhắn 1\"},{\"from\": 5,\"time\": \"2020 / 01 / 04  12: 03: 00: 00 AM\",\"content\": \"Đây là nội dung tin nhắn 2\"},{\"from\": 4,\"time\": \"2020 / 01 / 04  12: 05: 00: 00 AM\",\"content\": \"Đây là nội dung tin nhắn 3\"},{\"from\": 5,\"time\": \"2020 / 01 / 04  12: 08: 00: 00 AM\",\"content\": \"Đây là nội dung tin nhắn 4\"}]\r\n');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `messagegroup`
--

CREATE TABLE `messagegroup` (
  `messagegruopID` int(11) NOT NULL,
  `content` longtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `messagegroup`
--

INSERT INTO `messagegroup` (`messagegruopID`, `content`) VALUES
(1, '[{\r\n		\"from\": 4,\r\n		\"time\": \"26 / 11 / 2020 12: 00: 00: 00 AM\",\r\n		\"content\": \"Đây là nội dung tin nhắn 1\"\r\n	},\r\n	{\r\n		\"from\": 5,\r\n		\"time\": \"26 / 11 / 2020 12: 03: 00: 00 AM\",\r\n		\"content\": \"Đây là nội dung tin nhắn 2\"\r\n	},\r\n	{\r\n		\"from\": 6,\r\n		\"time\": \"26 / 11 / 2020 12: 05: 00: 00 AM\",\r\n		\"content\": \"Đây là nội dung tin nhắn 3\"\r\n	},\r\n]\r\n');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(10) CHARACTER SET utf8 NOT NULL,
  `fullname` varchar(50) CHARACTER SET utf8 NOT NULL,
  `sex` varchar(5) CHARACTER SET utf8 NOT NULL,
  `birdthday` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`userID`, `username`, `password`, `fullname`, `sex`, `birdthday`) VALUES
(4, 'phong@gmail.com', '123456', 'Huỳnh Chí Phong', 'nam', '1999-07-30'),
(5, 'hieu@gmail.com', '123456', 'Trương Minh Hiếu', 'nam', '1999-08-12'),
(6, 'dang@gmail.com', '123456', 'Nguyễn Khoa Đăng', 'nam', '1999-10-21'),
(7, 'khanh@gmail.com', '123456', 'Thạch Thế Khanh', 'nam', '1999-12-11');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `blocklist`
--
ALTER TABLE `blocklist`
  ADD PRIMARY KEY (`userID`);

--
-- Chỉ mục cho bảng `friendlist`
--
ALTER TABLE `friendlist`
  ADD PRIMARY KEY (`userID`);

--
-- Chỉ mục cho bảng `groupchat`
--
ALTER TABLE `groupchat`
  ADD PRIMARY KEY (`groupID`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`messageID`);

--
-- Chỉ mục cho bảng `messagegroup`
--
ALTER TABLE `messagegroup`
  ADD PRIMARY KEY (`messagegruopID`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `blocklist`
--
ALTER TABLE `blocklist`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `friendlist`
--
ALTER TABLE `friendlist`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `groupchat`
--
ALTER TABLE `groupchat`
  MODIFY `groupID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=905;

--
-- AUTO_INCREMENT cho bảng `message`
--
ALTER TABLE `message`
  MODIFY `messageID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=204;

--
-- AUTO_INCREMENT cho bảng `messagegroup`
--
ALTER TABLE `messagegroup`
  MODIFY `messagegruopID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
