package serviceDao;

import java.util.ArrayList;

import dao.AulaDao;
import dao.NotaDao;
import dao.UserDao;
import modelo.Aula;
import modelo.Nota;

public class NotaService {
	
	private static float media(ArrayList<Nota> notas, int aula_id) {
		float mediaNotas = 0;
		int count = 0;
		for(int i = 0; i < notas.size(); i++) {
			if(notas.get(i).getAulaId() == aula_id) {
				mediaNotas += notas.get(i).getNota();
				count++;
			}
		}
		return mediaNotas / count;
	}
 	public static ArrayList<String> filterNotas(){
 		AulaDao aulaDao = new AulaDao();
 		NotaDao notaDao = new NotaDao();
 		UserDao userDao = new UserDao();
 		
 		ArrayList<Nota> notas = notaDao.list();
 		ArrayList<Aula> aulas = new ArrayList<>();
 		
 		for(Nota nota : notas) {
 			Aula aula = aulaDao.searchFor(nota.getAulaId());
 			aulas.add(aula);
 		}
 	
 		ArrayList<String> integentSystemData = new ArrayList<String>();
 		for(int i = 0; i < notas.size(); i++) {
 			int aula_id = notas.get(i).getAulaId();
 			int professor_id = aulas.get(i).getProfessorId();
 			String perfil = userDao.getProfessor(professor_id).getPerfil();
 			float media = media(notas, aula_id);
 			String data = aula_id + "," + professor_id + "," + perfil + "," + media;
 			integentSystemData.add(data);
 		}
 		
 		userDao.close();
 		notaDao.close();
 		aulaDao.close();
 		return integentSystemData;
 	};
 	
 	public static void main(String[] args) {
 		ArrayList<String> i = filterNotas();
 		for(String x : i) {
 			System.out.println(x);
 		}
 	}
}
