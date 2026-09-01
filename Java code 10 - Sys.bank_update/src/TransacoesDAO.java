import java.sql.*;

public class TransacoesDAO {

    public static int depositar(int conta, double deposito) throws SQLException{
        
        String sql = "UPDATE contas SET saldo = saldo + ? WHERE numero = ? ";
        Connection conexao = ConexaoDB.getConexao();
    
        if (conexao == null) {
            System.out.println("Erro: Não foi possível conectar ao banco de dados");
            return 0;
        }
    
        if (deposito > 0){
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                
                stmt.setDouble(1, deposito);
                stmt.setInt(2, conta);

                int LinhasAfetadas = stmt.executeUpdate();

                return LinhasAfetadas;
                
            } catch (SQLException e) {
                System.out.println("Erro ao realizar o Depósito: " + e);
            }
        }
        return 0;
    }

    public static int sacar(int conta, double saque) throws SQLException {
        
        String sql = "UPDATE contas SET saldo = saldo - ? WHERE numero = ? AND saldo >= ?";
    
        try(Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            stmt.setDouble(1, saque);
            stmt.setInt(2, conta);
            stmt.setDouble(3, saque);

            int LinhasAfetadas = stmt.executeUpdate();

            return LinhasAfetadas;


        } catch (SQLException e) {
            System.out.println("Falha ao realizar o saque: " + e);
        }
        
        return 0;
    }

    public static int transferir(int contaOrigem, int contaDestino, double valor) throws SQLException{
        String sqlOrigem = "UPDATE contas SET saldo = saldo - ? WHERE numero = ? AND saldo >= ?";
        String sqlFinal = "UPDATE contas SET saldo = saldo + ? WHERE numero = ? AND saldo >= ?";
        Connection conexao = ConexaoDB.getConexao();
    
        if (conexao == null) {
            System.out.println("Erro: Não foi possível conectar ao banco de dados");
            return 0;
        }

        try(PreparedStatement stmtOrigem = conexao.prepareStatement(sqlOrigem);
            PreparedStatement stmtFinal = conexao.prepareStatement(sqlFinal)) {

            conexao.setAutoCommit(false);

            stmtOrigem.setDouble(1, valor);
            stmtOrigem.setInt(2, contaOrigem);
            stmtOrigem.setDouble(3, valor);

            int linhasOrigem = stmtOrigem.executeUpdate();

            stmtFinal.setDouble(1, valor);
            stmtFinal.setInt(2, contaDestino);
            stmtFinal.setDouble(3, valor);

            int linhasDestino = stmtFinal.executeUpdate();

            if(linhasOrigem == 1 && linhasDestino == 1){
                conexao.commit();
                return 2;
            }else{
                conexao.rollback();
                System.out.println("Transferência cancelada: uma ou ambas as contas não foram encontradas");
                return 0;
            }

        } catch (SQLException e) {
            conexao.rollback();
            System.out.println("Não foi possível realizar a transferência!!!" + e);
        } finally{
            try {
                conexao.setAutoCommit(true);
                conexao.close();
                
            } catch (SQLException e) {
                 System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
            
        }
        return 0;
    }
}