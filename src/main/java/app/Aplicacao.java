package app;

import static spark.Spark.*;

import serviceDao.AulaService;
import serviceDao.ReservaService;
import serviceDao.UserService;

class Aplicacao {
	public static void main(String[] args) {
		port(8070);
		staticFileLocation("/public");
		get("/", (req,res) -> {
			return null;
		});
		
		get("/user", (req, res) -> UserService.validar(req, res) );
		
		get("/professores", (req,res) -> UserService.listProfessores(res));
		
		get("/reservas", (req,res) -> ReservaService.listReserva(req,res));

		post("/usuario", (req, res) -> UserService.cadastrar(req) );
		
		post("/updateStatus", (req, res) -> ReservaService.updateStatus(req));
		
		post("/aula", (req, res) -> AulaService.cadastrarAula(req));
		
		get("/listarAula", (req, res) -> AulaService.getAulas(req, res));
	}
}
