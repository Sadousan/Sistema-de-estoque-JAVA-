package project_interface.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Testel extends JFrame {

    private JTable tabelaUsuarios;
    private JScrollPane scrollPane;

    public Testel() {
        setTitle("Lista de Usuários - Pessoa Física");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define o layout
        setLayout(new java.awt.BorderLayout());

        // Configura a tabela
        String[] colunas = {"Nome", "CPF", "E-mail", "Gênero"};
        Object[][] dados = BDuserPF.obterDadosUsuarios(); // Obtém os dados do banco
        DefaultTableModel modeloTabela = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desabilita a edição das células
            }
        };

        tabelaUsuarios = new JTable(modeloTabela);
        scrollPane = new JScrollPane(tabelaUsuarios);

        // Adiciona a tabela à janela
        add(scrollPane, java.awt.BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Certifique-se de criar a tabela no banco antes de executar a interface
        BDuserPF.criarTabelas(); 
        SwingUtilities.invokeLater(() -> new Testel());
    }
}
