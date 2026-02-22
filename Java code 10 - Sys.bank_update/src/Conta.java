class Conta {
    String nome;
    private int usuario;
    private String senha;
    private double saldo;

   Conta(String nome, int numero, String senha) {
        this.nome = nome;
        this.usuario = numero;
        this.senha = senha;
        this.saldo = 0;
    }

    void mostrarSaldo() {
        System.out.println("Seu saldo atual é de " + saldo);
    }

    double depositar(double valor) {
        saldo += valor;
        return saldo;
    }

    boolean saque(double saque) {
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
    public double getSaldo(){
       return saldo;
    }
    public boolean verificarSenha(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }
}


