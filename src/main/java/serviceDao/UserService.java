package serviceDao;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.UserDao;
import modelo.Aluno;
import modelo.Professor;
import modelo.User;
import spark.Request;
import spark.Response;

public class UserService {
	public static String validar(Request req, Response res) {
		String email = req.queryParams("email");
	    String senha = req.queryParams("senha");
	    
	    
	    UserDao dao = new UserDao();
	    String[] dados = null;
		try {
			dados = dao.validarDados(email,senha);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		dao.close();
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
	    
	    UserDao dao = new UserDao();
	    String[] dados = null;
	    
    	try {
			dados = dao.addUser(user);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		dao.close();

	    return "{ \"id\": " + dados[0] + ", \"nome\": \"" + dados[1] + "\", \"tipo\": \"" + dados[2] + "\" }";
	}
	
	public static String listProfessores(Response res) {
		UserDao dao = new UserDao();
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
		dao.close();

	    return jsonBuilder.toString();
	}
	
}
