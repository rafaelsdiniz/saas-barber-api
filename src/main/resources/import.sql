-- ======================================================
-- LIMPEZA (OPCIONAL – USAR APENAS EM DEV)
-- ======================================================
TRUNCATE TABLE agendamento RESTART IDENTITY CASCADE;
TRUNCATE TABLE servico RESTART IDENTITY CASCADE;
TRUNCATE TABLE cliente RESTART IDENTITY CASCADE;
TRUNCATE TABLE barbeiro RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE barbearia RESTART IDENTITY CASCADE;

-- ======================================================
-- 1️⃣ BARBEARIA
-- ======================================================
INSERT INTO barbearia (id, nome, telefone, ativo, data_criacao, data_atualizacao) VALUES
(1,'Barbearia do Diniz','63999990001',true,now(),now()),
(2,'Barbearia Central','63999990002',true,now(),now()),
(3,'Barber Prime','63999990003',true,now(),now()),
(4,'Corte Fino','63999990004',true,now(),now()),
(5,'Navalha de Ouro','63999990005',true,now(),now()),
(6,'Black Beard','63999990006',true,now(),now()),
(7,'Estilo Urbano','63999990007',true,now(),now()),
(8,'Old School Barber','63999990008',true,now(),now()),
(9,'Barber House','63999990009',true,now(),now()),
(10,'Elite Barber','63999990010',true,now(),now());

-- ======================================================
-- 2️⃣ USUÁRIO (senha = 123456)
-- ======================================================
INSERT INTO usuario (id, email, senha, perfil, barbearia_id, ativo, data_criacao, data_atualizacao) VALUES
(1,'admin@diniz.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','ADMIN',1,true,now(),now()),
(2,'barbeiro1@diniz.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','BARBEIRO',1,true,now(),now()),
(3,'barbeiro2@diniz.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','BARBEIRO',1,true,now(),now()),
(4,'cliente1@diniz.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','CLIENTE',1,true,now(),now()),
(5,'cliente2@diniz.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','CLIENTE',1,true,now(),now()),
(6,'admin@elite.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','ADMIN',10,true,now(),now()),
(7,'barbeiro@elite.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','BARBEIRO',10,true,now(),now()),
(8,'cliente@elite.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','CLIENTE',10,true,now(),now()),
(9,'admin@prime.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','ADMIN',3,true,now(),now()),
(10,'admin@central.com','$2a$10$wFFrbAMhjxuXFre9HxjAo.Y67V3dcA8nEAH/KJN6XdfEZRCpbliZm','ADMIN',2,true,now(),now());

-- ======================================================
-- 3️⃣ BARBEIRO
-- ======================================================
INSERT INTO barbeiro (id, nome, telefone, ativo, barbearia_id, data_criacao, data_atualizacao) VALUES
(1,'Carlos Henrique','63998111111',true,1,now(),now()),
(2,'João Pedro','63998222222',true,1,now(),now()),
(3,'Marcos Silva','63998333333',true,1,now(),now()),
(4,'Rafael Costa','63998444444',true,1,now(),now()),
(5,'Lucas Rocha','63998555555',true,1,now(),now()),
(6,'Bruno Alves','63998666666',true,2,now(),now()),
(7,'Diego Martins','63998777777',true,3,now(),now()),
(8,'Felipe Lima','63998888888',true,4,now(),now()),
(9,'Thiago Santos','63998999999',true,5,now(),now()),
(10,'Eduardo Nunes','63998000000',true,10,now(),now());

-- ======================================================
-- 4️⃣ CLIENTE
-- ======================================================
INSERT INTO cliente (id, nome, telefone, email, ativo, barbearia_id, data_criacao, data_atualizacao) VALUES
(1,'Lucas Andrade','63999111111','lucas@gmail.com',true,1,now(),now()),
(2,'Rafael Lima','63999222222','rafa@gmail.com',true,1,now(),now()),
(3,'Pedro Santos','63999333333',null,true,1,now(),now()),
(4,'Bruno Oliveira','63999444444','bruno@gmail.com',true,1,now(),now()),
(5,'Mateus Costa','63999555555','mateus@gmail.com',true,1,now(),now()),
(6,'Gustavo Rocha','63999666666',null,true,2,now(),now()),
(7,'Caio Martins','63999777777','caio@gmail.com',true,3,now(),now()),
(8,'Igor Lima','63999888888','igor@gmail.com',true,4,now(),now()),
(9,'André Silva','63999999999','andre@gmail.com',true,5,now(),now()),
(10,'Daniel Nunes','63999000000','daniel@gmail.com',true,10,now(),now());

-- ======================================================
-- 5️⃣ SERVIÇO
-- ======================================================
INSERT INTO servico (id, nome, preco, duracao_minutos, ativo, barbearia_id, data_criacao, data_atualizacao) VALUES
(1,'Corte de Cabelo',40.00,30,true,1,now(),now()),
(2,'Barba Completa',35.00,25,true,1,now(),now()),
(3,'Corte + Barba',65.00,60,true,1,now(),now()),
(4,'Sobrancelha',15.00,10,true,1,now(),now()),
(5,'Corte Infantil',30.00,30,true,1,now(),now()),
(6,'Corte Degradê',50.00,40,true,2,now(),now()),
(7,'Barba Premium',45.00,30,true,3,now(),now()),
(8,'Corte Navalhado',55.00,45,true,4,now(),now()),
(9,'Relaxamento Capilar',70.00,60,true,5,now(),now()),
(10,'Corte Executivo',60.00,45,true,10,now(),now());

-- ======================================================
-- 6️⃣ AGENDAMENTO
-- ======================================================
INSERT INTO agendamento (id, ativo, barbearia_id, barbeiro_id, cliente_id, servico_id, data_hora, status, data_criacao, data_atualizacao) VALUES
(1,true,1,1,1,1,now()+interval '1 day','PENDENTE',now(),now()),
(2,true,1,2,2,2,now()+interval '2 days','CONFIRMADO',now(),now()),
(3,true,1,3,3,3,now()+interval '3 days','PENDENTE',now(),now()),
(4,true,1,4,4,4,now()+interval '4 days','CONFIRMADO',now(),now()),
(5,true,1,5,5,5,now()+interval '5 days','CANCELADO',now(),now()),
(6,true,2,6,6,6,now()+interval '1 day','PENDENTE',now(),now()),
(7,true,3,7,7,7,now()+interval '2 days','CONFIRMADO',now(),now()),
(8,true,4,8,8,8,now()+interval '3 days','PENDENTE',now(),now()),
(9,true,5,9,9,9,now()+interval '4 days','CONFIRMADO',now(),now()),
(10,true,10,10,10,10,now()+interval '5 days','PENDENTE',now(),now());

-- ======================================================
-- 7️⃣ AJUSTE DAS SEQUENCES
-- ======================================================
SELECT setval('barbearia_id_seq',  (SELECT MAX(id) FROM barbearia));
SELECT setval('usuario_id_seq',    (SELECT MAX(id) FROM usuario));
SELECT setval('barbeiro_id_seq',   (SELECT MAX(id) FROM barbeiro));
SELECT setval('cliente_id_seq',    (SELECT MAX(id) FROM cliente));
SELECT setval('servico_id_seq',    (SELECT MAX(id) FROM servico));
SELECT setval('agendamento_id_seq',(SELECT MAX(id) FROM agendamento));
