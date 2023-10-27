package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.*;

public class Dao {
	private Connection con;
	
	public Dao() {		
		String url = "jdbc:postgresql://ti2.postgres.database.azure.com/postgres";
		String user = "bruno";
		String senha = "Admin123";
		try {
			con = DriverManager.getConnection(url,user,senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] addUser(User user) throws NoSuchAlgorithmException  {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(user.getSenha().getBytes(), 0, user.getSenha().length());
		String senha = new BigInteger(1,md.digest()).toString(16);
		
		String sql = "INSERT INTO users (nome, email, senha, data_cadastro, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setString(1, user.getNome());
			statemente.setString(2,user.getEmail());
			
			
			statemente.setString(3,senha);
			statemente.setDate(4,Date.valueOf(user.getData_cadastro()));
			statemente.setString(5,user.getTipo_user());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");

		}catch(Exception e) {
			e.printStackTrace();
		}
		

		return validarDados(user.getEmail(),user.getSenha());
		
	}
	
	public void addAula(Aula aula) {
		String sql = "INSERT INTO aula (id_professor, id_aluno,titulo, descricao, preco, data_aula, data_criacao, local_aula, link_aula) VALUES (?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1,aula.getProfessorId());
			statemente.setInt(2,aula.getAluno_id());
			statemente.setString(3,aula.getMateria());
			statemente.setString(4,aula.getDescricao());
			statemente.setDouble(5, aula.getPreco());
			statemente.setTimestamp(6, Timestamp.valueOf(aula.getDisponibilidade()));
			statemente.setDate(7, Date.valueOf(aula.getDataCriacao()));
			statemente.setString(8,aula.getTipo_aula());
			statemente.setString(9,aula.getLink_aula());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addReserva(Reserva reserva) {
		String sql = "INSERT INTO reserva (id_aluno, id_aula, data_reserva, status) values (?,?,?,?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setInt(1,reserva.getAluno_id());
			statemente.setInt(2,reserva.getAula_id());
			statemente.setTimestamp(3,Timestamp.valueOf(reserva.getData()));
			statemente.setString(4,reserva.getStatus().getStatus());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNota(Nota nota) {
		String sql = "INSERT INTO nota (id_professor, id_aluno, id_aula, nota, comentario, origem) VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1,nota.getProfessorId());
			statement.setInt(2, nota.getAlunoId());
			statement.setInt(3, nota.getAulaId());
			statement.setInt(4,nota.getNota());
			statement.setString(5,nota.getComentario());
			statement.setString(6,nota.getOrigem());
			
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
            	
                Professor teacher = new Professor(id,nome,email,null,null);
                teachers.add(teacher);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
	}
	
	public ArrayList<Aula> listAulas(User user){
		ArrayList<Aula> aulas = new ArrayList<Aula>();
		try {
			Statement statement = con.createStatement();
			String query = null;
			if(user.getTipo_user().equals("professor")) {
				query = "SELECT * FROM aula WHERE id_professor = " + user.getId();
			}else {
				query = "SELECT * FROM aula WHERE id_aluno = " + user.getId();
			}
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

	public String[] validarDados(String email, String senha) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(senha.getBytes(), 0, senha.length());
		String password = new BigInteger(1,md.digest()).toString(16);
		String[] dados = new String[3];
		try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	Integer id = rs.getInt("id");
                	String nome = rs.getString("nome");
                	String tipo = rs.getString("tipo_usuario");
                	dados[0] = id + "";
                	dados[1] = nome;
                	dados[2] = tipo;
                }
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return dados;
	}
	
	public boolean updateReserva(Reserva reserva) {
		String sql = "UPDATE reserva SET status = ? WHERE id = ?";
		boolean enviado = false;
		try(PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setString(1, reserva.getStatus().getStatus());
			statement.setInt(2, reserva.getId());
			statement.execute();
			enviado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return enviado;
	}
	
	public void close() throws SQLException {
		con.close();
	}
	
	public ArrayList<Reserva> listReservas(Aluno aluno) {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		try {
			Statement statment = con.createStatement();
			String query = "SELECT * FROM reserva WHERE status = 'pendente' AND id_aluno = " + aluno.getId();
			ResultSet resul = statment.executeQuery(query);
			while(resul.next()) {
				Integer id = resul.getInt("id");
				Integer id_aluno = resul.getInt("id_aluno");
				Integer id_aula = resul.getInt("id_aula");
				LocalDateTime data = resul.getTimestamp("data_reserva").toLocalDateTime();
				Status status = new Status(resul.getString("status"));
				Reserva reserva = new Reserva(id,id_aluno,id_aula,data,status);
				reservas.add(reserva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reservas;
	}
	
	public ArrayList<Reserva> listReservas(ArrayList<Aula> aulas) {
		String test = "(";
		for (int i = 0; i < aulas.size(); i++) {
		    test += (i == aulas.size() - 1) ? aulas.get(i).getId() + ")" : aulas.get(i).getId() + ",";
		}
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		try {
			Statement statment = con.createStatement();
			String query = "SELECT * FROM reserva WHERE status = 'pendente' AND id_aula in " + test;
			ResultSet resul = statment.executeQuery(query);
			while(resul.next()) {
				Integer id = resul.getInt("id");
				Integer id_aluno = resul.getInt("id_aluno");
				Integer id_aula = resul.getInt("id_aula");
				LocalDateTime data = resul.getTimestamp("data_reserva").toLocalDateTime();
				Status status = new Status(resul.getString("status"));
				Reserva reserva = new Reserva(id,id_aluno,id_aula,data,status);
				reservas.add(reserva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reservas;
	}
}
