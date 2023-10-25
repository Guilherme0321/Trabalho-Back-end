package seviceDAO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Aluno;

public class DAO {
	private Connection con;
	
	public DAO() {		
		String url = "jdbc:postgresql://ti2.postgres.database.azure.com/postgres";
		String user = "bruno";
		String senha = "Admin123";
		try {
			con = DriverManager.getConnection(url,user,senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAluno(Aluno aluno) {
		String sql = "INSERT INTO users (id, nome, email, senha, data_cadastro, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1, aluno.getId());
			statemente.setString(2, aluno.getNome());
			statemente.setString(3,aluno.getEmail());
			statemente.setString(4,aluno.getSenha());
			statemente.setDate(5,Date.valueOf(aluno.getData()));
			statemente.setString(6,"aluno");
			
			statemente.executeUpdate();
            System.out.println("Registro adicionado");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DAO dao = new DAO();
		LocalDate x = LocalDate.now();
		Aluno aluno = new Aluno(10, "alguem","alguem","algo",x);
		dao.addAluno(aluno);
	}
	
}
