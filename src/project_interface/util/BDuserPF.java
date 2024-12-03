package project_interface.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import project_interface.model.UsuarioPF;

public class BDuserPF {
    private static final String URL = "jdbc:sqlite:usuariospf.db";

    // Função conectar(), para conexão com o banco de dados
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Método para criar a tabela
    public static void criarTabelas() {
        String sql = """
            CREATE TABLE IF NOT EXISTS PessoaFisica (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                cpf NUMERIC(11) NOT NULL UNIQUE,
                email TEXT NOT NULL UNIQUE,
                senha TEXT NOT NULL,
                genero TEXT NOT NULL CHECK(genero IN ('masculino', 'feminino', 'Outros'))
            );
        """;
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela PessoaFisica criada ou já existe.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    // Verifica se o usuário existe no banco de dados
    public static boolean usuarioExiste(String cpf) {
        String sql = "SELECT COUNT(*) FROM PessoaFisica WHERE cpf = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar usuário: " + e.getMessage());
        }
        return false;
    }

    // Verifica se o e-mail já está cadastrado
    public static boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM PessoaFisica WHERE email = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
        }
        return false;
    }

    // Inserir um novo usuário
    public static boolean inserirUsuario(UsuarioPF usuario) {
        if (usuarioExiste(usuario.getCpf())) {
            System.out.println("Já existe um usuário com esse CPF " + usuario.getCpf());
            return false;
        }
        
        if (!UsuarioPF.isEmailValido(usuario.getEmail())) {
            System.out.println("E-mail inválido. O e-mail deve conter '@' e terminar com '.com'.");
            return false;
        }
        
        if (emailExiste(usuario.getEmail())) {
            System.out.println("Já existe um usuário com esse e-mail " + usuario.getEmail());
            return false;
        }

        String sql = "INSERT INTO PessoaFisica (nome, cpf, email, senha, genero) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getCpf());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setString(5, usuario.getGenero());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pessoa física cadastrada com sucesso.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
        return false;
    }

    // Listar todos os usuários
    public static String listarUsuarios() {
        String sql = "SELECT id, nome, cpf, email, genero FROM PessoaFisica";
        StringBuilder sb = new StringBuilder();
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Nome: ").append(rs.getString("nome"))
                  .append(", CPF: ").append(rs.getString("cpf"))
                  .append(", Email: ").append(rs.getString("email"))
                  .append(", Gênero: ").append(rs.getString("genero"))
                  .append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return sb.toString();
    }

    // Excluir um usuário
    public static boolean excluirUsuario(String cpf) {
        String sql = "DELETE FROM PessoaFisica WHERE cpf = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
        return false;
    }

    // Editar dados de um usuário
    public static boolean editarUsuario(String novoNome, String novoEmail, String novoCpf, String novoGenero, String cpfAtual) {
        if (!novoCpf.equals(cpfAtual) && usuarioExiste(novoCpf)) {
            System.err.println("Erro: O CPF informado já está em uso por outro usuário.");
            return false;
        }

        if (!UsuarioPF.isEmailValido(novoEmail)) {
            System.out.println("E-mail inválido. O e-mail deve conter '@' e terminar com '.com'.");
            return false;
        }

        String sql = "UPDATE PessoaFisica SET nome = ?, email = ?, cpf = ?, genero = ? WHERE cpf = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novoCpf);
            pstmt.setString(4, novoGenero);
            pstmt.setString(5, cpfAtual);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao editar usuário: " + e.getMessage());
        }
        return false;
    }

    // Verificar login
    public static boolean verificarLogin(String email, String senha, String cpf) {
        String sql = cpf.isEmpty() 
                ? "SELECT COUNT(*) FROM PessoaFisica WHERE email = ? AND senha = ?"
                : "SELECT COUNT(*) FROM PessoaFisica WHERE email = ? AND senha = ? AND cpf = ?";
        
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            if (!cpf.isEmpty()) {
                pstmt.setString(3, cpf);
            }
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar login: " + e.getMessage());
        }
        return false;
    }

    // Método para obter os dados dos usuários e retorná-los em um formato adequado para a JTable
        // Método para obter os dados dos usuários e retorná-los em um formato adequado para a JTable
    public static Object[][] obterDadosUsuarios() {
        String sql = "SELECT id, nome, cpf, email, genero FROM PessoaFisica";
        
        // Lista para armazenar as linhas de dados que serão exibidas na tabela
        List<Object[]> listaDados = new ArrayList<>();

        try (Connection conn = conectar(); 
             PreparedStatement pstmt = conn.prepareStatement(sql); 
             ResultSet rs = pstmt.executeQuery()) {

            // Enquanto houver registros no ResultSet (rs), vai iterar para registro dos dados do banco
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),        
                    rs.getString("nome"),   
                    rs.getString("cpf"),    
                    rs.getString("email"),  
                    rs.getString("genero")  
                };
                // Adiciona a linha de dados à lista
                listaDados.add(linha);  
            }

        } catch (SQLException e) {
            System.err.println("Erro ao obter dados dos usuários: " + e.getMessage());
        }

        // Converte a lista de dados para um array bidimensional (formato da JTable)
        // seria uma matriz bidimensional (array dentro do array)
        Object[][] dadosUsuarios = new Object[listaDados.size()][5];
        for (int i = 0; i < listaDados.size(); i++) {
            dadosUsuarios[i] = listaDados.get(i);
        }

        return dadosUsuarios;  // Retorna os dados no formato adequado para a JTable
    }
}