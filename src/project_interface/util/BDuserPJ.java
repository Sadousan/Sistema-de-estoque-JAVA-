package project_interface.util;

import java.sql.*;
import project_interface.model.UsuarioPJ;

public class BDuserPJ {
    private static final String URL = "jdbc:sqlite:usuariospj.db";

    // Função conectar(), para conexão com o banco de dados
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Método para criar a tabela
    public static void criarTabelas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS PessoaJuridica (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nomeEmpresa TEXT NOT NULL,
                cnpj NUMERIC(14) NOT NULL UNIQUE,
                email TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL,
                cep TEXT NOT NULL,
                bairro TEXT NOT NULL,
                uf TEXT NOT NULL,
                numero INTEGER NOT NULL,
                rua TEXT NOT NULL
            );
        """;
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela PessoaJuridica criada ou já existe.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    // Verifica se o CNPJ existe no banco de dados
    public static boolean usuarioExiste(String cnpj) {
        String sql = "SELECT COUNT(*) FROM PessoaJuridica WHERE cnpj = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnpj);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar usuário: " + e.getMessage());
        }
        return false;
    }

    // Verifica se o e-mail já está cadastrado
    public static boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM PessoaJuridica WHERE email = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
        }
        return false;
    }

    // Inserir um novo usuário PJ
    public static boolean inserirUsuario(UsuarioPJ usuario) {
        if (usuarioExiste(usuario.getCnpj())) {
            System.out.println("Já existe uma empresa com esse CNPJ " + usuario.getCnpj());
            return false;
        }
        
        if (!UsuarioPJ.isEmailValido(usuario.getEmail())) {
            System.out.println("E-mail inválido. O e-mail deve conter '@' e terminar com '.com'.");
            return false;
        }
        
        if (emailExiste(usuario.getEmail())) {
            System.out.println("Já existe um usuário com esse e-mail " + usuario.getEmail());
            return false;
        }

        String sql = "INSERT INTO PessoaJuridica (nomeEmpresa, cnpj, email, senha, cep, bairro, uf, numero, rua) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNomeEmpresa());
            pstmt.setString(2, usuario.getCnpj());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getCep());
            pstmt.setString(6, usuario.getBairro());
            pstmt.setString(7, usuario.getUf());
            pstmt.setInt(8, usuario.getNumero());
            pstmt.setString(9, usuario.getRua());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pessoa jurídica cadastrada com sucesso.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
        return false;
    }

    // Listar todos os usuários PJ
    public static String listarUsuarios() {
        String sql = "SELECT id, nomeEmpresa, cnpj, email, bairro, uf FROM PessoaJuridica";
        StringBuilder sb = new StringBuilder();
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Nome da Empresa: ").append(rs.getString("nomeEmpresa"))
                  .append(", CNPJ: ").append(rs.getString("cnpj"))
                  .append(", Email: ").append(rs.getString("email"))
                  .append(", Bairro: ").append(rs.getString("bairro"))
                  .append(", UF: ").append(rs.getString("uf"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return sb.toString();
    }

    // Excluir um usuário PJ
    public static boolean excluirUsuario(String cnpj) {
        String sql = "DELETE FROM PessoaJuridica WHERE cnpj = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cnpj);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
        return false;
    }

    // Editar dados de um usuário PJ
    public static boolean editarUsuario(String novoNomeEmpresa, String novoEmail, String novoCnpj, String novoCep, String novoBairro, String novoUf, int novoNumero, String novoRua, String cnpjAtual) {
        if (!novoCnpj.equals(cnpjAtual) && usuarioExiste(novoCnpj)) {
            System.err.println("Erro: O CNPJ informado já está em uso por outra empresa.");
            return false;
        }

        if (!UsuarioPJ.isEmailValido(novoEmail)) {
            System.out.println("E-mail inválido. O e-mail deve conter '@' e terminar com '.com'.");
            return false;
        }

        String sql = "UPDATE PessoaJuridica SET nomeEmpresa = ?, email = ?, cnpj = ?, cep = ?, bairro = ?, uf = ?, numero = ?, rua = ? WHERE cnpj = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNomeEmpresa);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novoCnpj);
            pstmt.setString(4, novoCep);
            pstmt.setString(5, novoBairro);
            pstmt.setString(6, novoUf);
           
