package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produtos {

    private Connection connection;

    public Produtos(Connection connection) {
        this.connection = connection;
    }

    public void adicionarProduto(String nome, double valor, int idCategoria) throws SQLException {
        String sql = "INSERT INTO Produtoss (nome, valor, id_categoria) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nome);
            statement.setDouble(2, valor);
            statement.setInt(3, idCategoria);
            statement.executeUpdate();

            // Obtendo o idProduto gerado automaticamente
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idProduto = generatedKeys.getInt(1);
                    System.out.println("Produto adicionado ao banco de dados. ID Produto gerado: " + idProduto);
                }
            }
        }
    }

    public void deletarProduto(int idProduto) throws SQLException {
        String sql = "DELETE FROM Produtoss WHERE id_produto = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProduto);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produto deletado do banco de dados.");
            } else {
                System.out.println("Produto não encontrado no banco de dados.");
            }
        }
    }

    public void pesquisarProduto(int idProduto) throws SQLException {
        String sql = "SELECT * FROM Produtoss WHERE id_produto = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProduto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Produto encontrado: ");
                    System.out.println("ID: " + resultSet.getInt("id_produto"));
                    System.out.println("Nome: " + resultSet.getString("nome"));
                    System.out.println("Valor: " + resultSet.getDouble("valor"));
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        }
    }

    public void listarProdutos() throws SQLException {
        String sql = "SELECT * FROM Produtoss";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<String> produtos = new ArrayList<>();
            while (resultSet.next()) {
                produtos.add("ID: " + resultSet.getInt("id_produto") +
                        ", Nome: " + resultSet.getString("nome") +
                        ", Valor: " + resultSet.getDouble("valor"));
            }
            if (!produtos.isEmpty()) {
                System.out.println("Lista de Produtos:");
                for (String produto : produtos) {
                    System.out.println(produto);
                }
            } else {
                System.out.println("Nenhum produto encontrado no banco de dados.");
            }
        }
    }

    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexão fechada.");
        }
    }
}
