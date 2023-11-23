package serviceDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.AulaDao;
import modelo.Aula;
import spark.Request;
import spark.Response;

public class AulaService {
	public static boolean cadastrarAula(Request req) {
		Gson json = new Gson();
		AulaDao auladao = new AulaDao();
		JsonObject objectJson = json.fromJson(req.body(), JsonObject.class);
		
		Integer id = objectJson.get("id").getAsInt();
		String titulo = objectJson.get("titulo").getAsString();
		String descricao = objectJson.get("descricao").getAsString();
		Double preco = objectJson.get("preco").getAsDouble();
		String tempData = objectJson.get("data").getAsString();
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime data = LocalDateTime.parse(tempData, formatter);
        String tipo = objectJson.get("tipo").getAsString();
        String local = objectJson.get("local").getAsString();
		
        Aula aula = new Aula(null, id, titulo, descricao, preco, data, LocalDate.now(), tipo, local);
        
        boolean wasSent = auladao.addAula(aula);
		auladao.close();
		return wasSent;
	}
	
	public static String getAulas(Request req, Response res){
		AulaDao dao = new AulaDao();
		String text = req.queryParams("serach");
		
		ArrayList<Aula> aulas = dao.listAulas(text);
		for(Aula aula : aulas) {
			System.out.println(aula.getMateria() + " " + aula.getDescricao());
		}
		StringBuilder jsonBuilder = new StringBuilder();
	    jsonBuilder.append("[");
	    for (int i = 0; i < aulas.size(); i++) {
	    	Aula aula = aulas.get(i);
	        jsonBuilder.append("{");
	        //jsonBuilder.append("\"professor\":\"" + aula.getProfessorId() + "\",");
	        jsonBuilder.append("\"titulo\":\"" + aula.getMateria() + "\",");
	        jsonBuilder.append("\"descricao\":\"" + aula.getDescricao() + "\",");
	        jsonBuilder.append("\"preco\":\"" + aula.getPreco() + "\",");
	        jsonBuilder.append("\"data_horario\":\"" + aula.getDisponibilidade() + "\",");
	        jsonBuilder.append("\"tipo\":\"" + aula.getTipo_aula() + "\",");
	        jsonBuilder.append("\"link\":\"" + aula.getLink_aula() + "\"");
	        jsonBuilder.append("}");

	        if (i < aulas.size() - 1) {
	            jsonBuilder.append(",");
	        }
	    }
	    jsonBuilder.append("]");
	    dao.close();

	    return jsonBuilder.toString();
	}
}
