-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 08, 2020 lúc 09:15 AM
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
(4, '[5, 6, 7]'),
(5, '[4,6]'),
(6, '[4,5]'),
(7, '[4]');

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
(1, 'tomato', '[4,5,6]'),
(2, 'apple', '[4,5]'),
(3, 'tomato', '[4, 5]'),
(9, 'banana', '[7, 4]');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `participant1` int(11) NOT NULL,
  `participant2` int(11) NOT NULL,
  `content` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `message`
--

INSERT INTO `message` (`participant1`, `participant2`, `content`) VALUES
(4, 5, '[{\"from\":4,\"time\":\"2020 / 01 / 04 12: 00: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 1\"},{\"from\":5,\"time\":\"2020 / 01 / 04  12: 03: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 2\"},{\"from\":4,\"time\":\"2020 / 01 / 04  12: 05: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 3\"},{\"from\":5,\"time\":\"2020 / 01 / 04  12: 08: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 4\"},{\"from\":4,\"time\":\"2020/12/06 14:00:23\",\"content\":\"asda\"},{\"from\":4,\"time\":\"2020/12/06 14:12:30\",\"content\":\"hieu\"},{\"from\":4,\"time\":\"2020/12/06 17:34:18\",\"content\":\"hello\"},{\"from\":5,\"time\":\"2020/12/06 17:35:04\",\"content\":\"hello bạn\"},{\"from\":4,\"time\":\"2020/12/07 22:53:20\",\"content\":\"hello\"},{\"from\":4,\"time\":\"2020/12/07 22:57:50\",\"content\":\"ok\"},{\"from\":4,\"time\":\"2020/12/07 22:58:33\",\"content\":\"aaaaa\"},{\"from\":4,\"time\":\"2020/12/07 23:02:40\",\"content\":\"hello\"},{\"from\":4,\"time\":\"2020/12/07 23:05:11\",\"content\":\"asd\"},{\"from\":4,\"time\":\"2020/12/07 23:11:16\",\"content\":\"aaaaa\"},{\"from\":4,\"time\":\"2020/12/07 23:12:07\",\"content\":\"bbbbb\"}]'),
(4, 6, '[{\"from\":4,\"time\":\"2020/12/06 15:17:52\",\"content\":\"abc\"},{\"from\":4,\"time\":\"2020/12/07 22:55:06\",\"content\":\"ccc\"}]'),
(5, 6, '[{\"from\":5,\"time\":\"2020/12/07 22:54:46\",\"content\":\"aaaa\"},{\"from\":6,\"time\":\"2020/12/07 22:54:53\",\"content\":\"bbb\"}]');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `messagegroup`
--

CREATE TABLE `messagegroup` (
  `groupID` int(11) NOT NULL,
  `content` longtext CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `messagegroup`
--

INSERT INTO `messagegroup` (`groupID`, `content`) VALUES
(1, '[{\"from\":4,\"time\":\"26 / 11 / 2020 12: 00: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 1\"},{\"from\":5,\"time\":\"26 / 11 / 2020 12: 03: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 2\"},{\"from\":6,\"time\":\"26 / 11 / 2020 12: 05: 00: 00 AM\",\"content\":\"Đây là nội dung tin nhắn 3\"},{\"from\":4,\"time\":\"2020/12/06 17:50:11\",\"content\":\"hello\"},{\"from\":5,\"time\":\"2020/12/06 18:02:56\",\"content\":\"chao nhaa\"}]'),
(2, '[{\"from\":4,\"time\":\"2020/12/06 18:05:12\",\"content\":\"hi\"},{\"from\":5,\"time\":\"2020/12/06 18:05:30\",\"content\":\"hi cau\"}]');

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
  `birthday` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`userID`, `username`, `password`, `fullname`, `sex`, `birthday`) VALUES
(4, 'phong@gmail.com', '123456', 'Huỳnh Chí Phong', 'nam', '1999-07-30'),
(5, 'hieu@gmail.com', '123456', 'Trương Minh Hiếu', 'nam', '1999-08-12'),
(6, 'dang@gmail.com', '123456', 'Nguyễn Khoa Đăng', 'nam', '1999-10-21'),
(7, 'khanh@gmail.com', '123456', 'Thạch Thế Khanh', 'nam', '1999-12-11'),
(9, 'thia@gmail.com', '123456', 'Nguyễn Thị A', 'nu', '1999-01-04'),
(10, 'nguyenthia@gmail.com', '123456', 'Nguyễn Thị A', 'nu', '1999-01-04'),
(11, 'vanb@gmail.com', '123456', 'Trần Văn B', 'nam', '1991-01-01');

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
  ADD PRIMARY KEY (`participant1`,`participant2`);

--
-- Chỉ mục cho bảng `messagegroup`
--
ALTER TABLE `messagegroup`
  ADD PRIMARY KEY (`groupID`);

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
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT cho bảng `groupchat`
--
ALTER TABLE `groupchat`
  MODIFY `groupID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `messagegroup`
--
ALTER TABLE `messagegroup`
  MODIFY `groupID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
