package project_interface.model;

public class UsuarioPJ {
    private String nomeEmpresa;
    private String cnpj;
    private String email;
    private String senha;
    private String cep;
    private String bairro;
    private String uf;
    private int numero;
    private String rua;

    // Construtor
    public UsuarioPJ(String nomeEmpresa, String cnpj, String email, String senha, String cep, String bairro, String uf, int numero, String rua) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.cep = cep;
        this.bairro = bairro;
        this.uf = uf;
        this.numero = numero;
        this.rua = rua;
    }

    // Getters e Setters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
    
    // Método para verificar se o e-mail é válido
    public static boolean isEmailValido(String email) {
        return email.contains("@") && email.endsWith(".com");
    }
}
