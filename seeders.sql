INSERT INTO `roles` (`id`, `createdAt`, `isEnabled`, `updatedAt`, `userCreated`, `userUpdated`, `label`, `name`) VALUES
                                                                                                                     (1, NULL, b'1', NULL, NULL, NULL, 'Super Administrateur', 'SUPER_ADMIN'),
                                                                                                                     (2, NULL, b'1', NULL, NULL, NULL, 'Administrateur', 'ADMIN'),
                                                                                                                     (3, NULL, b'1', NULL, NULL, NULL, 'Utilisateur', 'USER');

INSERT INTO `users` (`id`, `createdAt`, `isEnabled`, `updatedAt`, `userCreated`, `userUpdated`, `accountIsEnabled`, `accountIsNotExpired`, `accountIsNotLocked`, `credentialNotExpired`, `email`, `firstname`, `isAdmin`, `lastname`, `password`, `phone`, `username`, `role_id`) VALUES
    (1, '2023-07-10 00:59:03.000000', b'1', '2023-07-10 00:59:03.000000', NULL, NULL, b'1', b'1', b'1', b'1', 'layegaye001@gmail.com', 'Abdoulaye', b'1', 'GAYE', '$2a$10$eUCiaM3s4SdWAJQ9CXyFS.bRpQ0m.GZhKwAuv.3KHKA0SRKS9sjuy', '771800510', 'admin', 1);