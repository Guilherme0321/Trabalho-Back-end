package app;

import static spark.Spark.*;

import serviceDao.ServiceDao;

class Aplicacao {
	public static void main(String[] args) {
		port(8080);
		staticFileLocation("/public");
		get("/", (req,res) -> {
			return null;
		});
		
		get("/user", (req, res) -> ServiceDao.validar(req, res) );
		
		get("/professores", (req,res) -> ServiceDao.listProfessores(res));
		
		get("/reservas", (req,res) -> ServiceDao.listReserva(req,res));

		post("/usuario", (req, res) -> ServiceDao.cadastrar(req) );
		
		post("/updateStatus", (req, res) -> ServiceDao.updateStatus(req));
	}
}
