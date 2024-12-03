package project_interface.model;


public class ProdutoPJ {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;
    private double montante;

    public ProdutoPJ(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.montante = preco * quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        this.montante = preco * this.quantidade;  // Atualiza o montante sempre que o preço mudar
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.montante = this.preco * quantidade;  // Atualiza o montante sempre que a quantidade mudar
    }

    public double getMontante() {
        return montante;
    }
}
