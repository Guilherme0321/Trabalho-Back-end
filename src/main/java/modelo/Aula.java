package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Aula {
	private Integer id;
	private Integer professor_id;
	private String materia;
	private String descricao;
	private Double preco;
	private LocalDateTime disponivel;
	private LocalDate data_criacao;
	private Nota nota;
	private Reserva reserva; // poderá ser modificado
		
	public Aula(Integer id, Integer professor_id, String materia, String descricao, Double preco, LocalDateTime data_disponivel, LocalDate data_criacao, Nota nota, Reserva reserva) {
		this.id = id;
		this.professor_id = professor_id;
		this.materia = materia;
		this.descricao = descricao;
		this.preco = preco;
		this.disponivel = data_disponivel;
		this.data_criacao = data_criacao;
		this.nota = nota;
		this.reserva = reserva;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setProfessorId(Integer professorId) {
		this.professor_id = professorId;
	}
	
	public void setMateria(String materia) {
		this.materia = materia;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public void setDisponibilidade(LocalDateTime dataDisponibilidade) {
		this.disponivel = dataDisponibilidade;
	}
	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.data_criacao = dataCriacao;
	}
	
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getProfessorId() {
		return professor_id;
	}
	
	public String getMateria() {
		return materia;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public Double getPreco() {
		return preco;
	}
	
	public LocalDateTime getDisponibilidade() {
		return disponivel;
	}
	
	public LocalDate getDataCriacao() {
		return data_criacao;
	}
	
	public Nota getNota() {
		return nota;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
}
