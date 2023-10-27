package serviceDao;

import spark.Request;
import spark.Response;
import modelo.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;


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
	    if(user == null) {	
	    	System.out.println("User é null");
	    }else {
	    	try {
				dados = dao.addUser(user);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
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
	    return jsonBuilder.toString();
	}
}
