package org.example;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost:5432/loja";
        String user = "postgres";
        String password = "merg01";
        Produtos produtosManager = null;
        NotaFiscal notaFiscal = null;
        try {
            // Criando uma instância da classe Produtos com a conexão ao banco de dados
            Connection connection = DriverManager.getConnection(url, user, password);
            produtosManager = new Produtos(connection);
            notaFiscal = new NotaFiscal(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            int op = 0;
            do {
                System.out.println("\nBadaro Stores!!!"
                        + "\nProdutos:"
                        + "\n(1)-Adicionar"

                        + "\n(2)-Deletar"

                        + "\n(3)-Pesquisar"

                        + "\n(4)-Listar"

                        + "\n\nNota fiscal:"

                        + "\n(5)-Emitir"

                        + "\n(6)-Pesquisar"

                        + "\n(7)-Deletar"

                        + "\n(8)-Listar"

                        + "\n(9)-Sair " +

                        "\nSua escolha: ");
                op = sc.nextInt();
                switch (op) {
                    case 1:
                        System.out.println("Nome do Produto: ");
                        sc.nextLine();
                        String nomeProduto = sc.nextLine();
                        System.out.println("Preço do Produto:");
                        double preco = sc.nextDouble();
                        System.out.println("Id da categoria:" +
                                "\n(1)-Eletronico" +
                                "\n(2)-Roupa" +
                                "\n(3)-Brinquedo" +
                                "\n(4)-Artigos esportivos ");
                        int idCategoria = sc.nextInt();
                        produtosManager.adicionarProduto(nomeProduto,preco,idCategoria);
                        break;
                    case 2:
                        System.out.println("id do produto que deseja deletar: ");
                        sc.nextLine();
                        int id = sc.nextInt();
                        produtosManager.deletarProduto(id);
                        break;
                    case 3:
                        System.out.println("id do produto que deseja pesquisar: ");
                        sc.nextLine();
                        int id2 = sc.nextInt();
                        produtosManager.pesquisarProduto(id2);
                        break;
                    case 4:
                        produtosManager.listarProdutos();
                        break;
                    case 5:
                        System.out.println("Id do produto:");
                        int id4 = sc.nextInt();
                        System.out.println("Nome do cliente: ");
                        sc.nextLine();
                        String nomeCliente= sc.nextLine();
                        System.out.println("CPF do cliente: ");
                        String cpfCliente= sc.nextLine();
                        notaFiscal.emitirNotaFiscal(id4,nomeCliente,cpfCliente);
                        break;
                    case 6:
                        System.out.println("ID da nota fiscal para pesquisar: ");
                        int idNota = sc.nextInt();
                        notaFiscal.pesquisarNotaFiscal(idNota);
                        break;
                    case 7:
                        System.out.println("ID da nota fiscal para delatar: ");
                        int idNota2 = sc.nextInt();
                        notaFiscal.deletarNotaFiscal(idNota2);
                        break;
                    case 8:
                        notaFiscal.listarNotasFiscais();
                        break;
                }
            } while (op != 9);
        }
    }