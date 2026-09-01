public class Conta {
    public String nome;
    private int usuario;
    private String senha;
    private String email;
    private double saldo;

    public Conta(String nome, int numero, String senha, String email, Double saldo) {
        this.nome = nome;
        this.usuario = numero;
        this.senha = senha;
        this.email = email;
        this.saldo = saldo;
    }

    public void mostrarSaldo() {
        System.out.println("Seu saldo atual é de R$ " + String.format("%.2f", saldo));
    }

    public boolean saque(double saque) {
        if (saque <= saldo) {
            saldo = saldo - saque;
            return true;
        } else {
            return false;
        }
    }

    public int getUsuario(){
       return usuario;
    }
    public String getNome(){
       return nome;
    }
    public String getEmail(){
       return email;
    }
    public double getSaldo(){
       return saldo;
    }
    public boolean verificarSenha(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }
}


