package project_interface.model;

public class UsuarioPF {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String genero;

    // Construtor
    public UsuarioPF(String nome, String cpf, String email, String senha, String genero) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.genero = genero;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    // Método para verificar se o e-mail é válido
    public static boolean isEmailValido(String email) {
        return email.contains("@") && email.endsWith(".com");
    }
}
