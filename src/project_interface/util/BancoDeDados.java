package project_interface.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {
    private static final String URL = "jdbc:sqlite:estoque.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criarTabelas() {
        try (Connection conn = conectar()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS Produto (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    preco REAL NOT NULL,
                    quantidade INTEGER NOT NULL
                );
            """;
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}

