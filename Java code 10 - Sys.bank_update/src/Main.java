import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    static ArrayList<Conta> contas = new ArrayList<>();
    static int numberCont = 1000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        Conta contaLogada = null;

        System.out.println("Bem vindo ao MM bank !!");
        System.out.print("Você já é nosso cliente;[1] Sim [2] Não:");
        int verify = sc.nextInt();

        if (verify == 1) {
            System.out.println("Me informe o número da sua conta:");
            int cont = sc.nextInt();

            sc.nextLine();

            System.out.println("Me informe a senha da sua conta:");
            String pin = sc.nextLine();

            Conta contaActual;
            // Verificação de usuário na Array contas
            for (int i = 0; i < contas.size(); i++) {

                contaActual = contas.get(i);


                if (contaActual.getUsuario() == cont) {


                    if (contaActual.verificarSenha(pin)) {
                        contaLogada = contaActual;
                        System.out.println("Bem vindo de volta " + contaLogada.nome);
                    } else {
                        System.out.println("Senha incorreta");
                    }
                    break;
                }

            }

        } else {
            // Criação de novos usuários 
            System.out.println("Deseja fazer um cadastro: [1] Sim [2] Não ");
            int initial = sc.nextInt();

            if (initial == 1) {
                System.out.println("Ok, agora irei gerar seu número de conta !!");

                sc.nextLine();

                System.out.println("Me informe seu nome:");
                String nome = sc.nextLine();


                System.out.println("Agora me informe uma senha:");
                String senhaDigitada = sc.nextLine();

                Conta novaConta = new Conta(nome, numberCont, senhaDigitada);
                contas.add(novaConta);

                System.out.println("Conta criada com sucesso, seu número de conta é de " + numberCont);
                System.out.println(" E sua senha é  " + senhaDigitada);

                numberCont++;

                contaLogada = novaConta;
            } else if (initial == 2) {
                System.out.println("Ok obrigado por usar nosso sistema bancário !!");
            } else {
                System.out.println("Você digitou um número incorreto !!");
            }
        }

        if (contaLogada != null) {
            do {
                opcao = mostrarMenu(sc, contaLogada.nome);
                switch (opcao) {
                    case 1:
                        contaLogada.mostrarSaldo();
                        break;
                    case 2:
                        System.out.print("Informe o valor a ser depositado:");
                        double deposito = sc.nextDouble();
                        contaLogada.depositar(deposito);
                        System.out.println("Deposito realizado com sucesso, este é seu novo saldo R$ " + contaLogada.getSaldo());
                        break;
                    case 3:
                        System.out.println("Seu saldo atual é de R$" + contaLogada.getSaldo());
                        System.out.print("Informe o valor que você deseja sacar:");
                        double valor = sc.nextDouble();


                        if (contaLogada.saque(valor)) {
                            System.out.println("Saque realizado com sucesso, seu novo saldo é de R$" + contaLogada.getSaldo() + " e o saque realizado foi de R$" + valor);
                        } else {
                            System.out.println("Você não tem saldo suficiente para realizar o saque");
                        }
                        break;

                    case 4:

                        Conta contaDestino = null;

                        System.out.print("Me informe o número da conta:");
                        int nmrConta = sc.nextInt();

                        for (int i = 0; i < contas.size(); i++) {
                            if (contas.get(i).getUsuario() == nmrConta) {
                                contaDestino = contas.get(i);
                                break;
                            }
                        }

                        System.out.print("Qual valor deseja tranferir:");
                        double trans = sc.nextDouble();

                        if (contaDestino != null && trans > 0) {
                            if (contaLogada.saque(trans)) {
                                contaDestino.depositar(trans);
                                System.out.println("Transferência realizada com sucesso, o valor transferido foi de R$ " + trans);
                            }
                        } else {
                            System.out.println("Ocorreu um erro na transferência!!! tente novamente mais tarde");
                        }
                        break;
                    case 5:
                        System.out.println("Obrigado por usar nosso sistema bancário!!!");
                        break;
                    default:
                        System.out.println("Opção inválida!!");
                        break;
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
        System.out.println("5 - sair");
        System.out.print("Me diga o que você deseja fazer:");
        int opcao = sc.nextInt();
        return opcao;
    }

}


