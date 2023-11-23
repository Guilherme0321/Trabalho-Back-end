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
	private String tipo_aula;
	private String link_aula;
		
	public Aula(Integer id, Integer professor_id, String materia, String descricao, Double preco, LocalDateTime data_disponivel, LocalDate data_criacao, String tipo_aula, String link) {
		this.id = id;
		this.professor_id = professor_id;
		this.materia = materia;
		this.descricao = descricao;
		this.preco = preco;
		this.disponivel = data_disponivel;
		this.data_criacao = data_criacao;
		this.tipo_aula = tipo_aula;
		this.link_aula = link;
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

	public String getTipo_aula() {
		return tipo_aula;
	}

	public String getLink_aula() {
		return link_aula;
	}

	public void setLink_aula(String link_aula) {
		this.link_aula = link_aula;
	}
}
