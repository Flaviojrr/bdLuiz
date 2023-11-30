CREATE TABLE Produtos (
    id_produto INT PRIMARY KEY,
    nome VARCHAR(255),
    valor DECIMAL(10, 2)
);

CREATE TABLE Nota_Fiscal (
    id_nota SERIAL PRIMARY KEY,
    id_produto INT REFERENCES Produtos(id_produto),
    valor_produto DECIMAL(10, 2),
    nome_cliente VARCHAR(255),
    cpf_cliente VARCHAR(11),
    FOREIGN KEY (id_produto) REFERENCES Produtos(id_produto)
);


CREATE TABLE Tipos_Produto (
    id_categoria INT PRIMARY KEY,
    eletronico BOOLEAN,
    roupa BOOLEAN,
    brinquedo BOOLEAN,
    artigos_esportivos BOOLEAN
);

-- Inserindo tipos de produtos
INSERT INTO Tipos_Produto (id_categoria, eletronico, roupa, brinquedo, artigos_esportivos) VALUES
(1, true, false, false, false),
(2, false, true, false, false),
(3, false, false, true, false),
(4, false, false, false, true);

-- Inserindo dados na tabela Produtoss
INSERT INTO Produtoss (nome, valor, id_categoria) VALUES ('Smartphone', 799.99, 1);
INSERT INTO Produtoss (nome, valor, id_categoria) VALUES ('Camiseta', 29.99, 2);
INSERT INTO Produtoss (nome, valor, id_categoria) VALUES ('Bola de Futebol', 39.99, 4);
INSERT INTO Produtoss (nome, valor, id_categoria) VALUES ('Fone de Ouvido', 59.99, 1);
