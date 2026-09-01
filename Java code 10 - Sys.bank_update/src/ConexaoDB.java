import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConexaoDB {

    private static Connection conexao;

    public static Connection getConexao(){
        Properties props = new Properties();

        try(FileInputStream input = new FileInputStream(".env")){
            props.load(input);
            
            String URL = props.getProperty("DB_URL");
            String USUARIO= props.getProperty("DB_USUARIO");
            String SENHA = props.getProperty("DB_SENHA");

            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            return conexao;

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        } catch (IOException a) {
            System.out.println("Erro ao ler o .env :" + a.getMessage());
            return null;
        }
    }
}