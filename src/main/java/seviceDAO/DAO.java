package seviceDAO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.*;

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
			statemente.close();
            System.out.println("Registro adicionado");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAluno(Professor teacher) {
		String sql = "INSERT INTO users (id, nome, email, senha, data_cadastro, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1, teacher.getId());
			statemente.setString(2, teacher.getNome());
			statemente.setString(3,teacher.getEmail());
			statemente.setString(4,teacher.getSenha());
			statemente.setDate(5,Date.valueOf(teacher.getData()));
			statemente.setString(6,"professor");
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addAula(Aula aula) {
		String sql = "INSERT INTO aula (id,id_professor,id_aluno,titulo,descricao,preco,data_aula,data_criacao,local_aula,link_aula) VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1, aula.getId());
			statemente.setInt(2,aula.getProfessorId());
			statemente.setInt(3,aula.getAluno_id());
			statemente.setString(4,aula.getMateria());
			statemente.setString(5,aula.getDescricao());
			statemente.setDouble(6, aula.getPreco());
			statemente.setTimestamp(7, Timestamp.valueOf(aula.getDisponibilidade()));
			statemente.setDate(8, Date.valueOf(aula.getDataCriacao()));
			statemente.setString(9,aula.getTipo_aula());
			statemente.setString(10,aula.getLink_aula());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addReserva(Reserva reserva) {
		String sql = "INSERT INTO aula (id,id_aluno,id_aula,data_reserva,status) values (?,?,?,?,?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1,reserva.getId());
			statemente.setInt(2,reserva.getAluno_id());
			statemente.setInt(3,reserva.getAula_id());
			statemente.setTimestamp(4,Timestamp.valueOf(reserva.getData()));
			statemente.setString(5,reserva.getStatus().getStatus());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNota(Nota nota) {
		String sql = "INSERT INTO nota (id,id_professor,id_aluno,id_aula,nota,comentario,origem) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1,nota.getId());
			statement.setInt(2,nota.getProfessorId());
			statement.setInt(3, nota.getAlunoId());
			statement.setInt(4, nota.getAulaId());
			statement.setInt(5,nota.getNota());
			statement.setString(6,nota.getComentario());
			statement.setString(7,nota.getOrigem());
			
			statement.executeUpdate();
			statement.close();
			System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Professor> listProfessores() {
		ArrayList<Professor> teachers = new ArrayList<Professor>();
        try {
            Statement statement = con.createStatement();
            String query = "SELECT * FROM users WHERE tipo_usuario = 'professor'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	Integer id = resultSet.getInt("id");
            	String nome = resultSet.getString("nome");
            	String email = resultSet.getString("email");
            	String senha = resultSet.getString("senha");
            	LocalDate data = resultSet.getDate("data_cadastro").toLocalDate();
            	
                Professor teacher = new Professor(id,nome,email,senha,data);
                teachers.add(teacher);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
	}
	
	public ArrayList<Aula> listAulas(Integer user_id){
		ArrayList<Aula> aulas = new ArrayList<Aula>();
		try {
			Statement statement = con.createStatement();
			String query = "SELECT * FROM aula WHERE id_aluno = " + user_id;
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				Integer id = result.getInt("id");
				Integer id_professor = result.getInt("id_professor");
				Integer id_aluno = result.getInt("id_aluno");
				String titulo = result.getString("titulo");
				String descricao = result.getString("descricao");
				Double preco = result.getDouble("preco");
				LocalDateTime horario = result.getTimestamp("data_aula").toLocalDateTime();
				LocalDate data = result.getDate("data_criacao").toLocalDate();
				String local_aula = result.getString("local_aula");
				String link_aula = result.getString("link_aula");
				
				Aula aula = new Aula(id, id_aluno, id_professor, titulo, descricao, preco, horario, data, local_aula, link_aula);
				
				aulas.add(aula);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return aulas;
	}
}
