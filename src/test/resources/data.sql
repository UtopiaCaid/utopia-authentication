INSERT INTO `tbl_account_roles` (`role_id`, `role_type`) VALUES (1, 'ROLE_USER');
INSERT INTO `tbl_account_roles` (`role_id`, `role_type`) VALUES (2, 'ROLE_ADMIN');
INSERT INTO `tbl_accounts` (`account_number`, `role_id`, `username`, `email`, `password`, `date_created`) VALUES (111, 2, 'admin1', 'admin1@email.com', '$2a$10$yWYQPtraGdc78r3RpG2/YeKi3AOD.NLBxv2PqmgXCuDtFlqSDoaO2', '2012-09-17');
INSERT INTO `tbl_accounts` (`account_number`, `role_id`, `username`, `email`, `password`, `date_created`)  VALUES (001, 1, 'user1', 'user1@email.com', '$2a$10$KJivYEkQbd8ZHt/jkCiBeeuo9cU84PiK/Peq/tXdp5sp5AtaTvpQa', '2013-09-17');
INSERT INTO `tbl_accounts` (`account_number`, `role_id`, `username`, `email`, `password`, `date_created`) VALUES (002, 1, 'user2', 'user2@email.com', '$2a$10$KJivYEkQbd8ZHt/jkCiBeeuo9cU84PiK/Peq/tXdp5sp5AtaTvpQa', '2012-07-17');
INSERT INTO `tbl_accounts` (`account_number`, `role_id`, `username`, `email`, `password`, `date_created`) VALUES (003, 1, 'h2user', 'h2user@email.com', '$2a$10$KJivYEkQbd8ZHt/jkCiBeeuo9cU84PiK/Peq/tXdp5sp5AtaTvpQa', '2014-09-17');
INSERT INTO `tbl_accounts` (`account_number`, `role_id`, `username`, `email`, `password`, `date_created`) VALUES (112, 2, 'admin2', 'admin2@email.com', '$2a$10$yWYQPtraGdc78r3RpG2/YeKi3AOD.NLBxv2PqmgXCuDtFlqSDoaO2', '2012-09-27');