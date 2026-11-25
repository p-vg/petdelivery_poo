DROP DATABASE IF EXISTS petdelivery;
CREATE DATABASE petdelivery CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE petdelivery;

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    telefone   VARCHAR(20),
    email      VARCHAR(100) NOT NULL UNIQUE,
    senha      VARCHAR(255) NOT NULL,
    tipo       ENUM('cliente', 'prestador', 'admin') NOT NULL DEFAULT 'cliente'
);

CREATE TABLE endereco (
    id_endereco INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario  INT NOT NULL,
    rua         VARCHAR(100) NOT NULL,
    numero      VARCHAR(10),
    bairro      VARCHAR(60),
    cidade      VARCHAR(60) NOT NULL,
    estado      CHAR(2) NOT NULL,
    cep         CHAR(8) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE pet (
    id_usuario INT NOT NULL,
    id_pet     INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(60) NOT NULL,
    especie    VARCHAR(30) NOT NULL,
    raca       VARCHAR(50),
    peso       DECIMAL(5,2),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE categoria_servico (
    id_categoria_servico INT AUTO_INCREMENT PRIMARY KEY,
    nome                 VARCHAR(60) NOT NULL
);

CREATE TABLE servico (
    id_servico           INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria_servico INT NOT NULL,
    nome                 VARCHAR(80) NOT NULL,
    descricao            VARCHAR(255),
    preco                DECIMAL(10,2) NOT NULL,
    duracao              INT,
    FOREIGN KEY (id_categoria_servico) REFERENCES categoria_servico(id_categoria_servico)
);

CREATE TABLE prestador_servico (
    id_prestador  INT PRIMARY KEY,
    especialidade VARCHAR(80),
    descricao     VARCHAR(255),
    FOREIGN KEY (id_prestador) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE presta (
    id_prestador INT NOT NULL,
    id_servico   INT NOT NULL,
    PRIMARY KEY (id_prestador, id_servico),
    FOREIGN KEY (id_prestador) REFERENCES prestador_servico(id_prestador) ON DELETE CASCADE,
    FOREIGN KEY (id_servico)   REFERENCES servico(id_servico) ON DELETE CASCADE
);

CREATE TABLE loja (
    id_loja  INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    cnpj     CHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20)
);

CREATE TABLE categoria_produto (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(60) NOT NULL
);

CREATE TABLE produto (
    id_produto   INT AUTO_INCREMENT PRIMARY KEY,
    id_loja      INT NOT NULL,
    id_categoria INT NOT NULL,
    nome         VARCHAR(100) NOT NULL,
    descricao    VARCHAR(255),
    preco        DECIMAL(10,2) NOT NULL,
    estoque      INT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_loja)      REFERENCES loja(id_loja),
    FOREIGN KEY (id_categoria) REFERENCES categoria_produto(id_categoria)
);

CREATE TABLE recomendacao (
    id_recomendacao   INT AUTO_INCREMENT PRIMARY KEY,
    id_pet            INT NOT NULL,
    id_produto        INT NOT NULL,
    motivo            VARCHAR(255),
    data_recomendacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pet)     REFERENCES pet(id_pet) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto) ON DELETE CASCADE
);

CREATE TABLE pedido (
    id_pedido       INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario      INT NOT NULL,
    id_endereco     INT NOT NULL,
    valor_total     DECIMAL(10,2) NOT NULL DEFAULT 0,
    status          ENUM('pendente', 'pago', 'em_entrega', 'entregue', 'cancelado') NOT NULL DEFAULT 'pendente',
    forma_pagamento VARCHAR(30),
    data_pedido     DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario)  REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco)
);

CREATE TABLE item_pedido (
    id_item        INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido      INT NOT NULL,
    id_produto     INT NOT NULL,
    quantidade     INT NOT NULL DEFAULT 1,
    preco_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido)  REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE TABLE pagamento (
    id_pagamento    INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido       INT NOT NULL,
    forma_pagamento VARCHAR(30) NOT NULL,
    valor           DECIMAL(10,2) NOT NULL,
    status          ENUM('pendente', 'aprovado', 'recusado') NOT NULL DEFAULT 'pendente',
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

CREATE TABLE avaliacao (
    id_avaliacao   INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido      INT NOT NULL,
    nota           TINYINT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario     VARCHAR(255),
    data_avaliacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

CREATE TABLE entregador (
    id_entregador INT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    telefone      VARCHAR(20),
    veiculo       VARCHAR(40),
    placa         CHAR(7)
);

CREATE TABLE entrega (
    id_entrega    INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido     INT NOT NULL,
    id_entregador INT NOT NULL,
    status        ENUM('aguardando', 'a_caminho', 'entregue') NOT NULL DEFAULT 'aguardando',
    hora_saida    DATETIME,
    hora_entrega  DATETIME,
    FOREIGN KEY (id_pedido)     REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_entregador) REFERENCES entregador(id_entregador)
);

INSERT INTO usuario (nome, telefone, email, senha, tipo) VALUES
('Maria Silva', '42999990001', 'maria@email.com', 'hash123', 'cliente'),
('João Souza',  '42999990002', 'joao@email.com',  'hash456', 'prestador');

INSERT INTO endereco (id_usuario, rua, numero, bairro, cidade, estado, cep) VALUES
(1, 'Rua das Flores', '120', 'Centro', 'Ponta Grossa', 'PR', '84010000');

INSERT INTO pet (id_usuario, nome, especie, raca, peso) VALUES
(1, 'Thor', 'Cachorro', 'Labrador', 28.50);

INSERT INTO categoria_servico (nome) VALUES ('Banho e Tosa'), ('Veterinário');
INSERT INTO servico (id_categoria_servico, nome, descricao, preco, duracao) VALUES
(1, 'Banho completo', 'Banho com secagem e perfume', 60.00, 60);
INSERT INTO prestador_servico (id_prestador, especialidade) VALUES (2, 'Banho e Tosa');
INSERT INTO presta VALUES (2, 1);

INSERT INTO loja (nome, cnpj, telefone) VALUES ('PetShop Amigo', '12345678000199', '4232220000');
INSERT INTO categoria_produto (nome) VALUES ('Ração'), ('Brinquedos');
INSERT INTO produto (id_loja, id_categoria, nome, descricao, preco, estoque) VALUES
(1, 1, 'Ração Premium 10kg', 'Para cães adultos', 189.90, 25);

INSERT INTO recomendacao (id_pet, id_produto, motivo) VALUES
(1, 1, 'Indicada para cães de porte grande');

INSERT INTO pedido (id_usuario, id_endereco, valor_total, status, forma_pagamento) VALUES
(1, 1, 189.90, 'pago', 'pix');
INSERT INTO item_pedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES
(1, 1, 1, 189.90);
INSERT INTO pagamento (id_pedido, forma_pagamento, valor, status) VALUES
(1, 'pix', 189.90, 'aprovado');

INSERT INTO entregador (nome, telefone, veiculo, placa) VALUES
('Carlos Lima', '42999990003', 'Moto', 'ABC1D23');
INSERT INTO entrega (id_pedido, id_entregador, status, hora_saida) VALUES
(1, 1, 'a_caminho', NOW());
