package project_interface.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import project_interface.model.ProdutoPJ;

public class BDprodutoPJ {
    private Connection conn;

    public BDprodutoPJ() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:estoque.db");
            criarTabelaSeNaoExistir();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar no banco de dados: " + e.getMessage());
        }
    }

    private void criarTabelaSeNaoExistir() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS produtos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "preco REAL NOT NULL, " +
                    "quantidade INTEGER NOT NULL)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public boolean adicionarProduto(ProdutoPJ produto) {
        try {
            String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean modificarProduto(int id, ProdutoPJ produto) {
        try {
            String sql = "UPDATE produtos SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, produto.getNome());
            pstmt.setDouble(2, produto.getPreco());
            pstmt.setInt(3, produto.getQuantidade());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao modificar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean removerQuantidadeProduto(int id, int quantidade) {
        try {
            String sql = "UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quantidade);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao remover quantidade do produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirProduto(int id) {
        try {
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    public List<ProdutoPJ> listarProdutos() {
        List<ProdutoPJ> produtos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produtos";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                produtos.add(new ProdutoPJ(id, nome, preco, quantidade));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }
}
