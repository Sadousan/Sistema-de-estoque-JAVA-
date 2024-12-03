package project_interface.util;

import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Cria as tabelas do banco de dados (se ainda não existirem)
        BDuserPF.criarTabelas();

        while (true) {
            // Menu de opções
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Cadastrar uma nova Pessoa Física");
            System.out.println("2 - Listar todas as Pessoas Físicas");
            System.out.println("3 - Verificar se uma Pessoa Física existe pelo CPF");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a linha em branco após o número

            switch (opcao) {
                case 1:
                    // Cadastrar nova Pessoa Física
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    
                    System.out.print("Digite o CPF: ");
                    String cpf = scanner.nextLine();
                    
                    System.out.print("Digite o email: ");
                    String email = scanner.nextLine();
                    
                    System.out.print("Digite a senha: ");
                    String senha = scanner.nextLine();
                    
                    System.out.print("Digite o gênero (masculino, feminino, Outros): ");
                    String genero = scanner.nextLine();
                    
                    BDuserPF.inserirUsuario(nome, cpf, email, senha, genero);
                    break;
                    
                case 2:
                    // Listar todas as Pessoas Físicas cadastradas
                    BDuserPF.listarUsuarios();
                    break;
                    
                case 3:
                    // Verificar se a Pessoa Física existe pelo CPF
                    System.out.print("Digite o CPF para verificar: ");
                    String cpfVerificar = scanner.nextLine();
                    
                    boolean existe = BDuserPF.usuarioExiste(cpfVerificar);
                    if (existe) {
                        System.out.println("A pessoa física com CPF " + cpfVerificar + " já existe.");
                    } else {
                        System.out.println("A pessoa física com CPF " + cpfVerificar + " não foi encontrada.");
                    }
                    break;
                    
                case 4:
                    // Sair do programa
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
}
