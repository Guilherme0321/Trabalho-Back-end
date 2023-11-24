package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.*;

public class NotaDao {
	private Connection con;
	
	public NotaDao() {		
		String url = "jdbc:postgresql://ti2.postgres.database.azure.com/postgres";
		String user = "bruno";
		String senha = "Admin123";
		try {
			con = DriverManager.getConnection(url,user,senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNota(Nota nota) {
		String sql = "INSERT INTO nota (id_aluno, id_aula, nota, origem) VALUES (?,?,?,?)";
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1,nota.getAlunoId());
			statement.setInt(2, nota.getAulaId());
			statement.setInt(3,nota.getNota());
			statement.setString(4,nota.getOrigem());
			
			statement.executeUpdate();
			statement.close();
			System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateNota(Nota nota) {
	}
	
	public void deleteNota(int id) {
		
	}
	
	private ArrayList<Nota> list(String sql){
		ArrayList<Nota> notas = new ArrayList<Nota>();
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt(1);
				int id_aluno = result.getInt(2);
				int id_aula = result.getInt(3);
				int valor_nota = result.getInt(4);
				String origem = result.getString(5);
				Nota nota = new Nota(id, id_aluno, id_aula, valor_nota, origem);
				notas.add(nota);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return notas;
	}
	
	public ArrayList<Nota> list(){
		return list("SELECT * FROM nota");
	}
	
	public ArrayList<Nota> listBy(String x){
		String sql = "SELECT * FROM nota WHERE id_aula in (SELECT id FROM aula WHERE titulo LIKE '%" + x + "%')";
		return list(sql);
	}
	
	public int getBiggerNota(int id_aluno){
		AulaDao aulaDao = new AulaDao();
		Integer aulaNota_id = null;
		String sql = "SELECT id_aula FROM nota WHERE id_aluno = " + id_aluno + " ORDER BY nota ASC LIMIT 1;";
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(result.next()) {
				aulaNota_id = result.getInt(1);				
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(aulaNota_id == null) {
			return -1;
		}else {			
			Aula temp = aulaDao.searchFor(aulaNota_id);
			aulaDao.close();
			return temp.getProfessorId() ;
		}
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
