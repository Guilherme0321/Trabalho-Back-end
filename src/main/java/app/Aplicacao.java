package app;

import static spark.Spark.*;

import java.time.LocalDate;
import java.util.Arrays;

import seviceDAO.Dao;
import modelo.*;

class Aplicacao {
	public static void main(String[] args) {
		port(8080);
		staticFileLocation("/public");
		get("/", (req,res) -> {
			return null;
		});
		
		get("/user", (req, res) -> {
		    String email = req.queryParams("email");
		    String senha = req.queryParams("senha");
		    
		    
		    Dao dao = new Dao();
		    String[] dados = dao.validarDados(email,senha);

		    res.type("application/json");
		    return "{ \"id\": " + dados[0] + ", \"nome\": \"" + dados[1] + "\", \"topo\": \"" + dados[2] + "\" }";
		});

		
		post("/usuario", (req, res) -> {
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
		    	dados = dao.addUser(user);
		    }		    
		    return "{ \"id\": " + dados[0] + ", \"nome\": \"" + dados[1] + "\", \"topo\": \"" + dados[2] + "\" }";
		});


	}
}
