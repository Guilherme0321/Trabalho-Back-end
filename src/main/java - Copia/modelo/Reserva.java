package modelo;

import java.time.LocalDateTime;

public class Reserva {
	private Integer id;
	private Integer aluno_id;
	private Integer aula_id;
	private LocalDateTime data;
	private Status status;
	
	public Reserva(Integer id, Integer aluno_id, Integer aula_id, LocalDateTime data, Status status) {
		this.id = id;
		this.aluno_id = aluno_id;
		this.aula_id = aula_id;
		this.data = data;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAluno_id() {
		return aluno_id;
	}
	public void setAluno_id(Integer aluno_id) {
		this.aluno_id = aluno_id;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Integer getAula_id() {
		return aula_id;
	}
	public void setAula_id(Integer aula_id) {
		this.aula_id = aula_id;
	}
	
}
