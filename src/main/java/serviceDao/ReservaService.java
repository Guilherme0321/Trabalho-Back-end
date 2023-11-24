package serviceDao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.AulaDao;
import dao.ReservaDao;
import modelo.Aluno;
import modelo.Aula;
import modelo.Professor;
import modelo.Reserva;
import modelo.Status;
import spark.Request;
import spark.Response;

public class ReservaService {
	public static String listReserva(Request req, Response res) {
		ReservaDao dao = new ReservaDao();
		AulaDao auladao = new AulaDao();
		Integer id_user = Integer.parseInt(req.queryParams("id"));
		String tipo = req.queryParams("tipo");
		
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		if(tipo.equals("professor")) {
			Professor prof = new Professor(id_user, null, null, tipo);
			ArrayList<Aula> aulas_do_usuario = auladao.listAulas(prof);
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
		dao.close();
		auladao.close();
	    return jsonBuilder.toString();
	}
	
	public static boolean updateStatus(Request req) {
		ReservaDao dao = new ReservaDao();
		AulaDao auladao = new AulaDao();
		Gson gson = new Gson();
		JsonObject jsonData = gson.fromJson(req.body(), JsonObject.class);
		Integer id = Integer.parseInt(jsonData.get("id").getAsString());
		Integer id_aula = Integer.parseInt(jsonData.get("id_aula").getAsString());
		Status status = new Status(jsonData.get("status").getAsString());
		boolean atualizado = false;
		Reserva temp_reserva = new Reserva(id, status);
		atualizado = dao.updateReserva(temp_reserva);			
		if(status.getStatus().equals("cancelada")) {
			Aula aula = new Aula(id_aula, null, null, null, null, null, null, null, null);
			auladao.deleteAula(aula);
		}
		
		dao.close();
		auladao.close();
		return atualizado;
	}

}
