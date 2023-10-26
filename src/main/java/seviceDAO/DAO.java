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
	
	public void addUser(User user)  {
		String sql = "INSERT INTO users (nome, email, senha, data_cadastro, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement statemente = con.prepareStatement(sql);
			statemente.setString(1, user.getNome());
			statemente.setString(2,user.getEmail());
			statemente.setString(3,user.getSenha());
			statemente.setDate(4,Date.valueOf(user.getData_cadastro()));
			statemente.setString(5,user.getTipo_user());
			
			statemente.executeUpdate();
			statemente.close();
            System.out.println("Registro adicionado");

		}catch(Exception e) {
			e.printStackTrace();
		}
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
	
	public ArrayList<Aula> listAulasTOaluno(User user){
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

	public User validarDados(User user) {
		User userl = null;
		try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE email = ? AND senha = ? AND tipo_usuario = ?");
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());
            stmt.setString(3, user.getTipo_user());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	Integer id = rs.getInt("id");
                	String nome = rs.getString("nome");
                	String email = rs.getString("email");
                	String senha = rs.getString("senha");
                	LocalDate data = rs.getDate("data_cadastro").toLocalDate();
                	if(user.getTipo_user().equals("professor")) {
                		userl = new Professor(id, nome, email, senha, data);
                	}else {
                		userl = new Aluno(id, nome, email, senha, data);
                	}
                }
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return userl;
	}
	
	public static void main(String[] args) {
		//DAO dao = new DAO();
		//Aluno aluno = new Aluno(4, "JoãoEED667", "jalkdçajkdl@email.com", "senha123", LocalDate.now());
		//Professor professor = new Professor(5, "Mar77RRia", "ljaçklsdf@eRRFFDmail.com", "senha456", LocalDate.now());
		//Aula aula = new Aula(4, 5, 4, "Matemática", "Aula de álgebra", 50.0, LocalDateTime.now(), LocalDate.now(), "virtual", "www.algo");
		//Status status = new Status("confirmada");
		//Reserva reserva = new Reserva(1, 4, 4, LocalDateTime.now(), status);
		//Nota nota = new Nota(1, 4, 5, 4, 9, "Muito bom!", "professor");
		
		//dao.addUser(aluno);
		//dao.addUser(professor);
		//dao.addAula(aula);
		//dao.addReserva(reserva);
		//dao.addNota(nota);
		//ArrayList<Professor> profs = dao.listProfessores();
		//for(Professor prof : profs) {
		//	System.out.println(prof.getNome());
		//}
		//ArrayList<Aula> aulas = dao.listAulasTOaluno(5);
		//for(Aula aula : aulas) {
		//	System.out.println("-" + aula.getMateria());
		//}
	}
}
