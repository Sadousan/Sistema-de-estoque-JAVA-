package project_interface.controller;

import project_interface.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {
    private List<Produto> produtos;

    public ProdutoController() {
        this.produtos = new ArrayList<>();
    }

    // Adicionar produto
    public void adicionarProduto(String nome, double preco, int quantidade) {
        produtos.add(new Produto(nome, preco, quantidade));
    }

    // Consultar todos os produtos
    public List<Produto> listarProdutos() {
        return produtos;
    }

    // Realizar entrada no estoque
    public void entradaEstoque(int indice, int quantidade) {
        Produto produto = produtos.get(indice);
        produto.entrada(quantidade);
    }

    // Realizar saída do estoque
    public void saidaEstoque(int indice, int quantidade) {
        Produto produto = produtos.get(indice);
        produto.saida(quantidade);
    }
}

