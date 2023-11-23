package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Aula;
import modelo.User;

public class AulaDao {
	
	private Connection con;
	
	public AulaDao() {		
		String url = "jdbc:postgresql://ti2.postgres.database.azure.com/postgres";
		String user = "bruno";
		String senha = "Admin123";
		try {
			con = DriverManager.getConnection(url,user,senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addAula(Aula aula) {
		String sql = "INSERT INTO aula (id_professor,titulo, descricao, preco, data_aula, data_criacao, local_aula, link_aula) VALUES (?,?,?,?,?,?,?,?)";
		boolean isSent = false;
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1,aula.getProfessorId());
			statemente.setString(2,aula.getMateria());
			statemente.setString(3,aula.getDescricao());
			statemente.setDouble(4, aula.getPreco());
			statemente.setTimestamp(5, Timestamp.valueOf(aula.getDisponibilidade()));
			statemente.setDate(6, Date.valueOf(aula.getDataCriacao()));
			statemente.setString(7,aula.getTipo_aula());
			statemente.setString(8,aula.getLink_aula());
			
			int num = statemente.executeUpdate();
			if(num > 0) {
				isSent = true;
			}
			statemente.close();
            System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}
	
	public Aula searchFor(int id_aula){
		Aula aula = null;
		try {
			Statement statement = con.createStatement();
			String query = "SELECT * FROM aula WHERE id =" + id_aula;

			ResultSet result = statement.executeQuery(query);
			if(result.next()) {
				Integer id = result.getInt("id");
				Integer id_professor = result.getInt("id_professor");
				String titulo = result.getString("titulo");
				String descricao = result.getString("descricao");
				Double preco = result.getDouble("preco");
				LocalDateTime horario = result.getTimestamp("data_aula").toLocalDateTime();
				LocalDate data = result.getDate("data_criacao").toLocalDate();
				String local_aula = result.getString("local_aula");
				String link_aula = result.getString("link_aula");
				
				aula = new Aula(id,id_professor, titulo, descricao, preco, horario, data, local_aula, link_aula);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return aula;
	}
	
	public ArrayList<Aula> listAulas(String comparation){
		ArrayList<Aula> aulas = new ArrayList<Aula>();
		try {
			Statement statement = con.createStatement();
			String query = "SELECT * FROM aula WHERE titulo LIKE '%" + comparation + "%' OR descricao LIKE '%" + comparation + "%';";
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				Integer id = result.getInt("id");
				Integer id_professor = result.getInt("id_professor");
				String titulo = result.getString("titulo");
				String descricao = result.getString("descricao");
				Double preco = result.getDouble("preco");
				LocalDateTime horario = result.getTimestamp("data_aula").toLocalDateTime();
				LocalDate data = result.getDate("data_criacao").toLocalDate();
				String local_aula = result.getString("local_aula");
				String link_aula = result.getString("link_aula");
				
				Aula aula = new Aula(id,id_professor, titulo, descricao, preco, horario, data, local_aula, link_aula);
				
				aulas.add(aula);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return aulas;
	}
	
	public ArrayList<Aula> listAulas(){
		ArrayList<Aula> aulas = new ArrayList<Aula>();
		try {
			Statement statement = con.createStatement();
			String query = "SELECT * FROM aula";

			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				Integer id = result.getInt("id");
				Integer id_professor = result.getInt("id_professor");
				String titulo = result.getString("titulo");
				String descricao = result.getString("descricao");
				Double preco = result.getDouble("preco");
				LocalDateTime horario = result.getTimestamp("data_aula").toLocalDateTime();
				LocalDate data = result.getDate("data_criacao").toLocalDate();
				String local_aula = result.getString("local_aula");
				String link_aula = result.getString("link_aula");
				
				Aula aula = new Aula(id,id_professor, titulo, descricao, preco, horario, data, local_aula, link_aula);
				
				aulas.add(aula);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return aulas;
	}
	
	public ArrayList<Aula> listAulas(User user){
		ArrayList<Aula> aulas = new ArrayList<Aula>();
		try {
			Statement statement = con.createStatement();
			String query = null;
			if(user.getTipo_user().equals("professor")) {
				query = "SELECT * FROM aula WHERE id_professor = " + user.getId();
				ResultSet result = statement.executeQuery(query);
				while(result.next()) {
					Integer id = result.getInt("id");
					Integer id_professor = result.getInt("id_professor");
					String titulo = result.getString("titulo");
					String descricao = result.getString("descricao");
					Double preco = result.getDouble("preco");
					LocalDateTime horario = result.getTimestamp("data_aula").toLocalDateTime();
					LocalDate data = result.getDate("data_criacao").toLocalDate();
					String local_aula = result.getString("local_aula");
					String link_aula = result.getString("link_aula");
					
					Aula aula = new Aula(id,id_professor, titulo, descricao, preco, horario, data, local_aula, link_aula);
					
					aulas.add(aula);
				}
				result.close();
			}
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return aulas;
	}
	
	public boolean deleteAula(Aula aula) {
		boolean deleted = false;
		try {
			Statement statment = con.createStatement();
			String query = "DELETE FROM aula WHERE id = " + aula.getId();
			statment.executeQuery(query);
			deleted = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return deleted;
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
