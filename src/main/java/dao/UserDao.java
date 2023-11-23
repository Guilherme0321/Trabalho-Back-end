package dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Professor;
import modelo.User;

public class UserDao {
	private Connection con;
	
	public UserDao() {		
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
	
	public Professor getProfessor(int id) {
		Professor professor = null;
		try {
			Statement statement = con.createStatement();
			String query = "SELECT perfil FROM users WHERE tipo_usuario = 'professor' and id =" + id;
			ResultSet result = statement.executeQuery(query);
			if(result.next()) {
				String perfil = result.getString(1);
				professor = new Professor(id, null, null,null);
				professor.setPerfil(perfil);
			}
			result.close();
			statement.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return professor;
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
