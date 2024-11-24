package project_interface.view;

import project_interface.controller.ProdutoController;
import project_interface.model.Produto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaP extends JFrame {
    private ProdutoController produtoController;

    public TelaP() {
        produtoController = new ProdutoController();

        // Configuração da Janela
        setTitle("Gerenciador de Estoque");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Componentes
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 100, 20);
        JTextField txtNome = new JTextField();
        txtNome.setBounds(120, 20, 200, 20);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 60, 100, 20);
        JTextField txtPreco = new JTextField();
        txtPreco.setBounds(120, 60, 200, 20);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(20, 100, 100, 20);
        JTextField txtQuantidade = new JTextField();
        txtQuantidade.setBounds(120, 100, 200, 20);

        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.setBounds(20, 140, 200, 30);

        JTextArea txtListaProdutos = new JTextArea();
        txtListaProdutos.setBounds(20, 200, 540, 150);
        txtListaProdutos.setEditable(false);

        // Actions
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                double preco = Double.parseDouble(txtPreco.getText());
                int quantidade = Integer.parseInt(txtQuantidade.getText());

                produtoController.adicionarProduto(nome, preco, quantidade);

                txtNome.setText("");
                txtPreco.setText("");
                txtQuantidade.setText("");

                atualizarListaProdutos(txtListaProdutos);
            }
        });

        // componentes
        add(lblNome);
        add(txtNome);
        add(lblPreco);
        add(txtPreco);
        add(lblQuantidade);
        add(txtQuantidade);
        add(btnAdicionar);
        add(txtListaProdutos);
    }

    private void atualizarListaProdutos(JTextArea txtListaProdutos) {
        StringBuilder lista = new StringBuilder();
        for (Produto produto : produtoController.listarProdutos()) {
            lista.append(produto.detalhes()).append("\n\n");
        }
        txtListaProdutos.setText(lista.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaP tela = new TelaP();
            tela.setVisible(true);
        });
    }
}
