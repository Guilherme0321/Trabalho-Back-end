package serviceDao;

import spark.Request;
import spark.Response;
import modelo.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.Dao;

public class ServiceDao {
	public static String validar(Request req, Response res) {
		String email = req.queryParams("email");
	    String senha = req.queryParams("senha");
	    
	    
	    Dao dao = new Dao();
	    String[] dados = null;
		try {
			dados = dao.validarDados(email,senha);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    res.type("application/json");
	    return "{ \"id\": " + dados[0] + ", \"nome\": \"" + dados[1] + "\", \"tipo\": \"" + dados[2] + "\" }";
	}
	
	public static String cadastrar(Request req) {
	    String nome = req.queryParams("nome");
	    String email = req.queryParams("email");
	    String senha = req.queryParams("senha");
	    String tipo = req.queryParams("tipo");
	    
	    LocalDate data = LocalDate.now();
	    
	    User user = null;
	    
	    if(tipo.equals("professor")) {
	    	user = new Aluno(null, nome, email, senha, data);
	    }else {
	    	user = new Professor(null, nome, email, senha, data);
	    }
	    
	    Dao dao = new Dao();
	    String[] dados = null;
	    
    	try {
			dados = dao.addUser(user);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	try {
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "{ \"id\": " + dados[0] + ", \"nome\": \"" + dados[1] + "\", \"tipo\": \"" + dados[2] + "\" }";
	}
	
	public static String listProfessores(Response res) {
		Dao dao = new Dao();
		ArrayList<Professor> profs = dao.listProfessores();
	    StringBuilder jsonBuilder = new StringBuilder();
	    
	    jsonBuilder.append("[");
	    for (int i = 0; i < profs.size(); i++) {
	        Professor prof = profs.get(i);
	        jsonBuilder.append("{");
	        jsonBuilder.append("\"id\":" + prof.getId() + ",");
	        jsonBuilder.append("\"nome\":\"" + prof.getNome() + "\",");
	        jsonBuilder.append("\"email\":\"" + prof.getEmail() + "\"");
	        jsonBuilder.append("}");

	        if (i < profs.size() - 1) {
	            jsonBuilder.append(",");
	        }
	    }
	    jsonBuilder.append("]");

	    res.type("application/json");
	    try {
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return jsonBuilder.toString();
	}
	
	public static String listReserva(Request req, Response res) {
		Dao dao = new Dao();
		Integer id_user = Integer.parseInt(req.queryParams("id"));
		String tipo = req.queryParams("tipo");
		
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		
		if(tipo.equals("professor")) {
			Professor prof = new Professor(id_user, null, null, tipo);
			ArrayList<Aula> aulas_do_usuario = dao.listAulas(prof);
			reservas = dao.listReservas(aulas_do_usuario);
		}else if(tipo.equals("aluno")){
			Aluno usuario = new Aluno(id_user, null, null, tipo);
			reservas = dao.listReservas(usuario);
		}
		
		
		StringBuilder jsonBuilder = new StringBuilder();
	    
	    jsonBuilder.append("[");
	    for (int i = 0; i < reservas.size(); i++) {
	        Reserva reserva = reservas.get(i);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String data = reserva.getData().format(formatter);
	        jsonBuilder.append("{");
	        jsonBuilder.append("\"id\":" + reserva.getId() + ",");
	        jsonBuilder.append("\"id_aluno\":\"" + reserva.getAluno_id() + "\",");
	        jsonBuilder.append("\"id_aula\":\"" + reserva.getAula_id() + "\",");
	        jsonBuilder.append("\"data\":\"" + data + "\",");
	        jsonBuilder.append("\"status\":\"" + reserva.getStatus().getStatus() + "\"");
	        jsonBuilder.append("}");

	        if (i < reservas.size() - 1) {
	            jsonBuilder.append(",");
	        }
	    }
	    jsonBuilder.append("]");

	    res.type("application/json");
	    try {
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return jsonBuilder.toString();
	}
	
	public static boolean updateStatus(Request req) {
		Dao dao = new Dao();
		Gson gson = new Gson();
		JsonObject jsonData = gson.fromJson(req.body(), JsonObject.class);
		Integer id = Integer.parseInt(jsonData.get("id").getAsString());
		Integer id_aula = Integer.parseInt(jsonData.get("id_aula").getAsString());
		Status status = new Status(jsonData.get("status").getAsString());
		boolean atualizado = false;
		Reserva temp_reserva = new Reserva(id, status);
		atualizado = dao.updateReserva(temp_reserva);			
		if(status.getStatus().equals("cancelada")) {
			Aula aula = new Aula(id_aula, null, null, null, null, null, null, null, null, null);
			dao.deleteAula(aula);
		}
		
		try {
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return atualizado;
	}
}
