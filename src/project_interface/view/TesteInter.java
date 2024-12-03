package project_interface.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//importar banco de dados
import project_interface.util.BDuserPF;

public class TesteInter {
    public static void main(String[] args) {
        // Cria as tabelas do banco de dados (se ainda não existirem)
        BDuserPF.criarTabelas();
        
        // Configura a janela principal do Swing
        JFrame frame = new JFrame("Cadastro de Pessoa Física");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centraliza na tela
        
        // Painel para os componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Adiciona um título
        JLabel titleLabel = new JLabel("Cadastro e Gerenciamento de Pessoas Físicas");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        // Botões para as ações
        JButton cadastroButton = new JButton("Cadastrar Nova Pessoa Física");
        cadastroButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton listarButton = new JButton("Listar Pessoas Físicas");
        listarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton verificarButton = new JButton("Verificar Pessoa Física por CPF");
        verificarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adicionando botões no painel
        panel.add(Box.createVerticalStrut(20)); // Espaçamento
        panel.add(cadastroButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(listarButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(verificarButton);
        
        // Configura a janela
        frame.add(panel);
        frame.setVisible(true);
        
        // Ação para o botão de cadastro
        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPessoa();
            }
        });
        
        // Ação para o botão de listar
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarPessoas();
            }
        });
        
        // Ação para o botão de verificar
        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarCPF();
            }
        });
    }
    
    private static void cadastrarPessoa() {
        // Janela de cadastro
        JTextField nomeField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField senhaField = new JPasswordField();
        JTextField generoField = new JTextField();

        Object[] message = {
            "Nome:", nomeField,
            "CPF:", cpfField,
            "Email:", emailField,
            "Senha:", senhaField,
            "Gênero (masculino, feminino, Outros):", generoField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Cadastrar Pessoa Física", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            String email = emailField.getText();
            String senha = senhaField.getText();
            String genero = generoField.getText();
            
            BDuserPF.inserirUsuario(nome, cpf, email, senha, genero);
        }
    }

    private static void listarPessoas() {
        // Exibe todas as pessoas físicas cadastradas
        String pessoas = BDuserPF.listarUsuarios();
        JOptionPane.showMessageDialog(null, pessoas, "Pessoas Físicas Cadastradas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void verificarCPF() {
        // Janela de entrada do CPF para verificação
        JTextField cpfField = new JTextField();
        
        Object[] message = {
            "Digite o CPF para verificar:", cpfField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Verificar CPF", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String cpf = cpfField.getText();
            boolean existe = BDuserPF.usuarioExiste(cpf);
            if (existe) {
                JOptionPane.showMessageDialog(null, "A pessoa física com CPF " + cpf + " já existe.", "Pessoa Encontrada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "A pessoa física com CPF " + cpf + " não foi encontrada.", "Pessoa Não Encontrada", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
