package modelo;

import java.time.LocalDateTime;

public class Nota {
	private Integer id;
	private Integer aluno_id;
	private Integer professor_id;
	private Integer aula_id;
	private Double nota;
	private String comentario;
	private LocalDateTime data_avaliada;
	
	public Nota(Integer id, Integer aluno_id, Integer professor_id) {
		this(id,aluno_id,professor_id,null,null,null,null);
	}
	
	public Nota(Integer id, Integer aluno_id, Integer professor_id, Integer aula_id, Double nota, String comentario, LocalDateTime data_avaliada) {
		this.id = id;
		this.aluno_id = aluno_id;
		this.professor_id = professor_id;
		this.aula_id = aula_id;
		this.nota = nota;
		this.comentario = comentario;
		this.data_avaliada = data_avaliada;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setAlunoId(Integer alunoId) {
		this.aluno_id = alunoId;
	}
	
	public void setProfessorId(Integer professorId) {
		this.professor_id = professorId;
	}
	
	public void setAulaId(Integer aulaId) {
		this.aula_id = aulaId;
	}
	
	public void setNota(Double nota) {
		this.nota = nota;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public void setDataAvaliada(LocalDateTime data) {
		this.data_avaliada = data;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Integer getAlunoId() {
		return aluno_id;
	}
	
	public Integer getProfessorId() {
		return professor_id;
	}
	
	public Integer getAulaId() {
		return aula_id;
	}
	
	public Double getNota() {
		return nota;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public LocalDateTime dataAvaliada() {
		return data_avaliada;
	}
}
