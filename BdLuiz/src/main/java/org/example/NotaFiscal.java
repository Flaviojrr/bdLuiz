package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscal {

    private Connection connection;

    public NotaFiscal(Connection connection) {
        this.connection = connection;
    }

    // Método para emitir uma nova nota fiscal
    public void emitirNotaFiscal(int idProduto, String nomeCliente, String cpfCliente) throws SQLException {
        String sql = "INSERT INTO Nota_Fiscal (id_produto, valor_produto, nome_cliente, cpf_cliente) " +
                "SELECT id_produto, valor, ?, ? FROM Produtoss WHERE id_produto = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nomeCliente);
            statement.setString(2, cpfCliente);
            statement.setInt(3, idProduto);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Obtendo o idNota gerado automaticamente
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idNota = generatedKeys.getInt(1);
                        System.out.println("Nota fiscal emitida com sucesso. ID Nota Fiscal gerado: " + idNota);
                    }
                }
            } else {
                System.out.println("Erro ao emitir nota fiscal. Produto não encontrado.");
            }
        }
    }

    // Método para pesquisar uma nota fiscal pelo ID
    public void pesquisarNotaFiscal(int idNota) throws SQLException {
        String sql = "SELECT * FROM Nota_Fiscal WHERE id_nota = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idNota);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Nota fiscal encontrada:");
                    System.out.println("ID Nota Fiscal: " + resultSet.getInt("id_nota"));
                    System.out.println("Nome do Cliente: " + resultSet.getString("nome_cliente"));
                    System.out.println("CPF do Cliente: " + resultSet.getString("cpf_cliente"));
                } else {
                    System.out.println("Nota fiscal não encontrada.");
                }
            }
        }
    }

    // Método para deletar uma nota fiscal pelo ID
    public void deletarNotaFiscal(int idNota) throws SQLException {
        String sql = "DELETE FROM Nota_Fiscal WHERE id_nota = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idNota);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nota fiscal deletada com sucesso.");
            } else {
                System.out.println("Nota fiscal não encontrada.");
            }
        }
    }

    // Método para listar todas as notas fiscais
    public void listarNotasFiscais() throws SQLException {
        String sql = "SELECT * FROM Nota_Fiscal";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<String> notasFiscais = new ArrayList<>();
            while (resultSet.next()) {
                notasFiscais.add("ID Nota Fiscal: " + resultSet.getInt("id_nota") +
                        ", Nome do Cliente: " + resultSet.getString("nome_cliente") +
                        ", CPF do Cliente: " + resultSet.getString("cpf_cliente"));
            }
            if (!notasFiscais.isEmpty()) {
                System.out.println("Lista de Notas Fiscais:");
                for (String notaFiscal : notasFiscais) {
                    System.out.println(notaFiscal);
                }
            } else {
                System.out.println("Nenhuma nota fiscal encontrada.");
            }
        }
    }

    // Método para fechar a conexão com o banco de dados
    public void fecharConexao() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexão fechada.");
        }
    }
}
