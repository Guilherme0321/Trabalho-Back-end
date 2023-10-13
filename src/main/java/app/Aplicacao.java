package app;

import static spark.Spark.*;


class Aplicacao {
	public static void main(String[] args) {
		port(8080);
		staticFileLocation("/public");
		get("/", (req,res) -> {
			return null;
		});
		
		get("/user", (req,res)->{
			String username = req.queryParams("nome");
			String senha = req.queryParams("senha");
			System.out.println(username + "algo" + senha);
			return null;
		});
		
		post("/usuario", (req,res) -> {
			return null;
		});
	}
}
