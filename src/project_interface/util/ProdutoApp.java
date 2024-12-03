package project_interface.util;

import project_interface.model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProdutoApp {
    private final BDprodutoPJ produtoDAO;

    public ProdutoApp() {
        produtoDAO = new BDprodutoPJ();
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("Gerenciador de Produtos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nomeField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField quantidadeField = new JTextField();
        JButton addButton = new JButton("Adicionar Produto");

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Preço:"));
        formPanel.add(precoField);
        formPanel.add(new JLabel("Quantidade:"));
        formPanel.add(quantidadeField);
        formPanel.add(new JLabel(""));
        formPanel.add(addButton);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Tabela de produtos
        String[] colunas = {"ID", "Nome", "Preço", "Quantidade"};
        JTable tabela = new JTable(new Object[0][4], colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botão de exclusão
        JButton deleteButton = new JButton("Excluir Produto");
        mainPanel.add(deleteButton, BorderLayout.SOUTH);

        // Adicionar Produto
        addButton.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                double preco = Double.parseDouble(precoField.getText());
                int quantidade = Integer.parseInt(quantidadeField.getText());

                Produto produto = new Produto(nome, preco, quantidade);
                produtoDAO.inserirProduto(produto);

                JOptionPane.showMessageDialog(frame, "Produto adicionado com sucesso!");
                atualizarTabela(tabela);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Excluir Produto
        deleteButton.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                int id = (int) tabela.getValueAt(row, 0);
                produtoDAO.excluirProduto(id);
                JOptionPane.showMessageDialog(frame, "Produto excluído com sucesso!");
                atualizarTabela(tabela);
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um produto para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Atualizar tabela ao iniciar
        atualizarTabela(tabela);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
 
    private void atualizarTabela(JTable tabela) {
        List<Produto> produtos = produtoDAO.listarProdutos();
        Object[][] data = new Object[produtos.size()][4];
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getNome();
            data[i][2] = p.getPreco();
            data[i][3] = p.getQuantidade();
        }

        String[] colunas = {"ID", "Nome", "Preço", "Quantidade"};
        tabela.setModel(new javax.swing.table.DefaultTableModel(data, colunas));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProdutoApp::new);
    }
}
