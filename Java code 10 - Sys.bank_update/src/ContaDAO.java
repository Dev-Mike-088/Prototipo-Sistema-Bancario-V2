import java.sql.*;

public class ContaDAO {

    public static Conta criarConta(String nome, String email, String senha) throws SQLException{

        String sql = "INSERT INTO contas (nome, senha, email, saldo) VALUES (?, ?, ?, 0) RETURNING *";
       
    
        try(Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
               
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setString(3, email);
    
            ResultSet rs = stmt.executeQuery();
    
            if(rs.next()){
                int numeroGerado = rs.getInt("numero");
                String Nome = rs.getString("nome");
                String Senha = rs.getString("senha"); 
                String Email = rs.getString("email");
                double Saldo =rs.getDouble("saldo");
                return new Conta(Nome, numeroGerado, Senha, Email, Saldo);
            }
    
        }catch (SQLException e) {
            System.out.println("Erro ao criar a Conta: " + e);
        }
        return null;
    }
    public static Conta buscarConta(int contaDigitada, String email) throws SQLException{
        
        String sql = "SELECT * FROM contas WHERE numero = ? and email = ?";
        
        try (Connection conexao = ConexaoDB.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1, contaDigitada);
            stmt.setString(2, email);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                int numeroGerado = rs.getInt("numero");
                String Nome = rs.getString("nome");
                String Senha = rs.getString("senha"); 
                String Email = rs.getString("email");
                double Saldo =rs.getDouble("saldo");
                return new Conta(Nome, numeroGerado, Senha, Email, Saldo);
            }

            }catch (SQLException e){
                System.out.println("Erro ao encontrar a conta porque:" + e);
            }
        
    
        return null;
    }
}
