package modelo;

public class Nota {
	private Integer id;
	private Integer aluno_id;
	private Integer aula_id;
	private Integer nota;
	private String origem;
	
	public Nota(Integer id, Integer aluno_id) {
		this(id,aluno_id,null,null,null);
	}
	
	public Nota(Integer id, Integer aluno_id, Integer aula_id, Integer nota, String origem) {
		this.id = id;
		this.aluno_id = aluno_id;
		this.aula_id = aula_id;
		this.nota = nota;
		this.setOrigem(origem);;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setAlunoId(Integer alunoId) {
		this.aluno_id = alunoId;
	}
	
	public void setAulaId(Integer aulaId) {
		this.aula_id = aulaId;
	}
	
	public void setNota(Integer nota) {
		this.nota = nota;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Integer getAlunoId() {
		return aluno_id;
	}
	
	public Integer getAulaId() {
		return aula_id;
	}
	
	public Integer getNota() {
		return nota;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		if(origem.equals("aluno") || origem.equals("professor")) {			
			this.origem = origem;
		}else {
			System.out.println("O remetente tem que ser um professor ou um aluno");
		}
	}
	
}
