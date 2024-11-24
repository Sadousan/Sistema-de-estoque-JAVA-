package project_interface.model;

import javax.swing.JOptionPane;

public class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Métodos para entrada e saída de estoque
    public void entrada(int quantidade) {
        this.quantidade += quantidade;
        if (this.quantidade <=0){
            throw new IllegalArgumentException("Valor nulo ou negativo não podem ser adicionados ao estoque!");
        }
    }

    public void saida(int quantidade) {
        if (quantidade <= this.quantidade) {
            this.quantidade -= quantidade;
        } else {
            throw new IllegalArgumentException("Quantidade insuficiente no estoque.");
        }
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método para exibir detalhes
    public String detalhes() {
        return String.format("Produto: %s\nPreço: R$ %.2f\nQuantidade: %d\nValor Total: R$ %.2f",
                nome, preco, quantidade, preco * quantidade);
    }
}

