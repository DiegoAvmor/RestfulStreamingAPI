CREATE DATABASE  IF NOT EXISTS `streaming_api`;
USE `streaming_api`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `roles` (
  `id_role` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `roles` (`id_role`, `name`) VALUES
(1, 'USER'),
(2, 'ADMIN');

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users` (`id_user`, `name`, `email`, `password`, `role`, `created_at`, `updated_at`) VALUES
(2, 'Carlos Alejandro', 'carlos@outlook.es', '12345678', 1, '2021-02-04 01:36:12', '2021-02-04 01:36:12'),
(3, 'Manuel', 'Manuel@outlook.es', 'password123', 1, '2021-02-04 01:39:21', '2021-02-04 01:39:21'),
(4, 'Diego', 'Diego@outlook.es', 'Diegopass', 1, '2021-02-04 01:43:29', '2021-02-04 01:43:29'),
(5, 'Miguel', 'Miguel@outlook.es', 'Miguelon', 1, '2021-02-04 01:43:53', '2021-02-04 01:43:53');

CREATE TABLE `user_preferences_tags` (
  `id_user` int(11) NOT NULL,
  `tag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `user_preferences_tags` (`id_user`, `tag`) VALUES
(2, '[COMEDIA, TERROR, SUSPENSO, DRAMA, PORNO]'),
(3, '[COMEDIA, TERROR, SUSPENSO, DRAMA, PORNO]'),
(4, '[COMEDIA, TERROR, SUSPENSO, DRAMA, PORNO]'),
(5, '[COMEDIA, TERROR, SUSPENSO, DRAMA, PORNO]');

CREATE TABLE `user_ratings` (
  `id_rating` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_video` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `user_ratings` (`id_rating`, `id_user`, `id_video`, `rating`, `created_at`, `updated_at`) VALUES
(1, 5, 1, 5, '2021-02-04 03:34:54', '2021-02-04 03:34:54'),
(2, 5, 2, 6, '2021-02-04 03:37:36', '2021-02-04 03:37:36'),
(3, 5, 3, 2, '2021-02-04 03:37:44', '2021-02-04 03:37:44'),
(4, 5, 4, 2, '2021-02-04 03:38:13', '2021-02-04 03:38:13'),
(5, 2, 1, 10, '2021-02-04 03:39:12', '2021-02-04 03:39:12'),
(6, 2, 2, 8, '2021-02-04 03:39:18', '2021-02-04 03:39:18'),
(7, 2, 3, 6, '2021-02-04 03:39:30', '2021-02-04 03:39:30'),
(8, 2, 4, 2, '2021-02-04 03:39:36', '2021-02-04 03:39:36'),
(9, 3, 1, 5, '2021-02-04 03:40:29', '2021-02-04 03:40:29'),
(10, 3, 2, 6, '2021-02-04 03:40:37', '2021-02-04 03:40:37'),
(11, 3, 3, 7, '2021-02-04 03:40:46', '2021-02-04 03:40:46'),
(12, 3, 4, 2, '2021-02-04 03:40:53', '2021-02-04 03:40:53'),
(13, 4, 1, 5, '2021-02-04 03:42:19', '2021-02-04 03:42:19');

CREATE TABLE `user_recommendations` (
  `id_recommendation` int(11) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_video` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `videos` (
  `id_video` int(11) NOT NULL,
  `id_serialize` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `autor` int(11) DEFAULT NULL,
  `description` text,
  `average_rating` float DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL,
  `video_location` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `videos` (`id_video`, `id_serialize`, `titulo`, `autor`, `description`, `average_rating`, `created_at`, `video_location`) VALUES
(1, 'dP-KHrpjQhVDV3dE2s-m9k4T7mo5Dms6', 'CarlosVideo', 2, NULL, 6.25, '2021-02-04 01:49:04', 'src/main/resources/upload-dir/dP-KHrpjQhVDV3dE2s-m9k4T7mo5Dms6.mp4'),
(2, 'CaBWKnohCBKKuXJ_dPnbrLYPG2pyKi91', 'ManuelVideo', 3, 'Este es un video de manuel', 6.6667, '2021-02-04 01:51:31', 'src/main/resources/upload-dir/CaBWKnohCBKKuXJ_dPnbrLYPG2pyKi91.mp4'),
(3, 'UkzrddmtKd2bWDgvkgwmdPCDvVS-ePhV', 'DiegoVideo', 4, 'Este es un video de Diego', 5, '2021-02-04 02:00:19', 'src/main/resources/upload-dir/UkzrddmtKd2bWDgvkgwmdPCDvVS-ePhV.mp4'),
(4, 'fXkF0NgYVaLT3kWvqAWu9L2uf2HrTKQT', 'MiguelVideo', 5, 'Este es un video de Miguel', 2, '2021-02-04 02:01:49', 'src/main/resources/upload-dir/fXkF0NgYVaLT3kWvqAWu9L2uf2HrTKQT.mp4');

CREATE TABLE `videos_clasifications` (
  `id_videos_clasifications` int(11) NOT NULL,
  `id_video` int(11) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `videos_clasifications` (`id_videos_clasifications`, `id_video`, `tag`) VALUES
(1, 1, 'COMEDIA'),
(2, 1, 'PORNO'),
(3, 1, 'TERROR'),
(4, 2, 'COMEDIA'),
(5, 2, 'PORNO'),
(6, 3, 'COMEDIA'),
(7, 3, 'PORNO'),
(8, 3, 'TERROR'),
(9, 3, 'SUSPENSO'),
(10, 4, 'COMEDIA');

CREATE TABLE `user_recommendations_log` (
  `id_log` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_video` int(11) NOT NULL,
  `log_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_role`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role` (`role`);

ALTER TABLE `user_preferences_tags`
  ADD PRIMARY KEY (`id_user`);

ALTER TABLE `user_ratings`
  ADD PRIMARY KEY (`id_rating`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_video` (`id_video`);

ALTER TABLE `user_recommendations`
  ADD PRIMARY KEY (`id_recommendation`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_video` (`id_video`);

ALTER TABLE `videos`
  ADD PRIMARY KEY (`id_video`),
  ADD KEY `autor` (`autor`);
ALTER TABLE `videos` ADD FULLTEXT KEY `titulo` (`titulo`,`description`);

ALTER TABLE `videos_clasifications`
  ADD PRIMARY KEY (`id_videos_clasifications`),
  ADD KEY `id_video` (`id_video`);

ALTER TABLE `user_recommendations_log`
  ADD PRIMARY KEY (`id_log`);

ALTER TABLE `roles`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `user_ratings`
  MODIFY `id_rating` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

ALTER TABLE `user_recommendations`
  MODIFY `id_recommendation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `videos`
  MODIFY `id_video` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `videos_clasifications`
  MODIFY `id_videos_clasifications` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE `user_recommendations_log`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`id_role`);

ALTER TABLE `user_preferences_tags`
  ADD CONSTRAINT `user_preferences_tags_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`);

ALTER TABLE `user_ratings`
  ADD CONSTRAINT `user_ratings_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `user_ratings_ibfk_2` FOREIGN KEY (`id_video`) REFERENCES `videos` (`id_video`);

ALTER TABLE `user_recommendations`
  ADD CONSTRAINT `user_recommendations_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `user_recommendations_ibfk_2` FOREIGN KEY (`id_video`) REFERENCES `videos` (`id_video`);

ALTER TABLE `videos`
  ADD CONSTRAINT `videos_ibfk_1` FOREIGN KEY (`autor`) REFERENCES `users` (`id_user`);

ALTER TABLE `videos_clasifications`
  ADD CONSTRAINT `videos_clasifications_ibfk_1` FOREIGN KEY (`id_video`) REFERENCES `videos` (`id_video`);

ALTER TABLE `user_recommendations_log`
  ADD CONSTRAINT `users_recommendations_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `users_recommendations_ibfk_2` FOREIGN KEY (`id_video`) REFERENCES `videos` (`id_video`);

COMMIT;
