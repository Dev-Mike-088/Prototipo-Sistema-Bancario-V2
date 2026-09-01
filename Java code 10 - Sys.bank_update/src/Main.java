import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Conta contaLogada = null;
        int opcao;
        int verify;

        // Criação de Conta
        do{
        System.out.println("Bem vindo ao MM bank !!");
        System.out.print("Você já é nosso cliente;[1] Sim [2] Não:");
        verify = sc.nextInt();

        if (verify == 1) {
            System.out.println("Me informe o número da sua conta:");
            int contaDigitada = sc.nextInt();

            sc.nextLine();

            System.out.println("Me informe o email da sua conta:");
            String email = sc.nextLine();


            Conta contaAtual = ContaDAO.buscarConta(contaDigitada, email);

                if (contaAtual != null) {

                    System.out.println("Me informe a senha da sua conta:");
                    String senhaDigitada = sc.nextLine();

                    if (contaAtual.verificarSenha(senhaDigitada)) {
                        contaLogada = contaAtual;
                        System.out.println("Bem vindo de volta " + contaLogada.nome);
                    } else {
                        System.out.println("Senha incorreta");
                    }
                    break;
                }

        } else if (verify == 2) {
            // Criação de novos usuários 
            System.out.println("Deseja fazer um cadastro: [1] Sim [2] Não,Sair");
            int ini = sc.nextInt();

            if (ini == 1) {
                System.out.println("Ok, agora irei gerar seu número de conta !!");

                sc.nextLine();

                System.out.println("Me informe seu nome:");
                String nome = sc.nextLine();

                System.out.println("Me informe seu email:");
                String email = sc.nextLine();

                System.out.println("Agora me informe uma senha:");
                String senhaDigitada = sc.nextLine();

                Conta novaConta = ContaDAO.criarConta(nome, email, senhaDigitada);

                if(novaConta != null){
                    System.out.println("Conta criada com sucesso, seu número de conta é de " + novaConta.getUsuario());
                    System.out.println(" E sua senha é  " + senhaDigitada);
                    contaLogada = novaConta;
                    break;
                }else{
                    System.out.println("Conta não cadastrada com sucesso, sinto muito !!");
                    break;
                }
            

            } else if (ini == 2) {
                System.out.println("Tchau, até logo !!!!!");
                break;
            } else {
                System.out.println("Você digitou um número incorreto !!");
            }
        } else if(verify == 3){
            System.out.println("Obrigado por acessar nosso sistema!!!!!");
            break;
        } else {
            System.out.println("Você digitou uma opção incorreta!!!");
        }
        } while(verify != 3);
        
        // Operações dentro da conta
        if (contaLogada != null) {
            do {
                opcao = mostrarMenu(sc, contaLogada.getNome());
                switch (opcao) {
                    case 1:
                        contaLogada.mostrarSaldo();
                        System.out.println("\n");
                        break;
                    case 2:
                        System.out.print("Informe o valor a ser depositado:");
                        double deposito = sc.nextDouble();
                        int sucess = TransacoesDAO.depositar(contaLogada.getUsuario(), deposito);
                        if(sucess > 0 && sucess < 2){
                            contaLogada = ContaDAO.buscarConta(contaLogada.getUsuario(), contaLogada.getEmail());
                            System.out.println("Deposito realizado com sucesso, este é seu novo saldo R$ " + contaLogada.getSaldo());
                        }else{
                            System.out.println("Falha ao realizar Depósito, Sinto Muito!!!!!!!!");
                        }
                        break;
                        
                    case 3:
                        System.out.println("Seu saldo atual é de R$" + contaLogada.getSaldo());
                        System.out.print("Informe o valor que você deseja sacar:");
                        double valor = sc.nextDouble();

                        int sucesso = TransacoesDAO.sacar(contaLogada.getUsuario(), valor);


                        if (sucesso > 0 && sucesso < 2) {
                            contaLogada = ContaDAO.buscarConta(contaLogada.getUsuario(), contaLogada.getEmail());
                            System.out.println("Saque realizado com sucesso, seu novo saldo é de R$" + contaLogada.getSaldo() + " e o saque realizado foi de R$" + valor);
                        } else {
                            System.out.println("Você não tem saldo suficiente para realizar o saque");
                        }
                        break;

                    case 4:


                        System.out.print("Me informe o número da conta que será depositado:");
                        int nmrConta = sc.nextInt();

                        System.out.print("Qual valor deseja tranferir:");
                        double trans = sc.nextDouble();

                        if (nmrConta != 0 && trans > 0) {
                            int lines = TransacoesDAO.transferir(contaLogada.getUsuario(), nmrConta, trans);
                            if (lines == 2) {
                                contaLogada = ContaDAO.buscarConta(contaLogada.getUsuario(), contaLogada.getEmail());
                                System.out.println("Transferência realizada com sucesso, o valor transferido foi de R$ " + trans);
                            }
                        } else {
                            System.out.println("Ocorreu um erro na transferência!!! tente novamente mais tarde");
                        }
                        break;
                    case 5:
                        System.out.println("Obrigado por usar nosso sistema bancário!!!");
                        return;
                    default:
                        System.out.println("Opção inválida!!");
                }
            } while (opcao != 5);
        } else {
            System.out.println("Nenhuma conta Logada!!!");
        }
    }

    public static int mostrarMenu(Scanner sc, String nome) {
        System.out.println("Bem vindo ao Banco MM, " + nome + " segue abaixo o menu");
        System.out.println("----MENU----");
        System.out.println("1 - Ver saldo");
        System.out.println("2 - Depositar");
        System.out.println("3 - sacar");
        System.out.println("4 - Transfêrencia");
        System.out.println("5 - Sair");
        System.out.print("Me diga o que você deseja fazer:");
        int opcao = sc.nextInt();
        return opcao;
    }
    
}


