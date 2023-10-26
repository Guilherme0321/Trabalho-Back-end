package app;

import static spark.Spark.*;


class Aplicacao {
	public static void main(String[] args) {
		port(8080);
		staticFileLocation("/public");
		get("/", (req,res) -> {
			return null;
		});
		
		get("/user", (req, res) -> { // Criar a forma de validação de dados
		    String username = req.queryParams("nome");
		    String senha = req.queryParams("senha");
		    System.out.println(username + " " + senha);

		    return "Login realizado com sucesso";
		});

		
		post("/usuario", (req, res) -> { // Conectar com o back para enviar informações
		    String nome = req.queryParams("nome");
		    String email = req.queryParams("email");
		    String senha = req.queryParams("senha");
		    System.out.println("Nome: " + nome);
		    System.out.println("Email: " + email);
		    System.out.println("Senha: " + senha);
		    return "enviado";
		});


	}
}
