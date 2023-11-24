package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import modelo.Aluno;
import modelo.Aula;
import modelo.Reserva;
import modelo.Status;

public class ReservaDao {
	private Connection con;
	
	public ReservaDao() {		
		String url = "jdbc:postgresql://ti2.postgres.database.azure.com/postgres";
		String user = "bruno";
		String senha = "Admin123";
		try {
			con = DriverManager.getConnection(url,user,senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			System.out.println(query);
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
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
