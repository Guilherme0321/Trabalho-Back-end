package modelo;

import java.time.LocalDateTime;

public class Professor {
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private LocalDateTime data_cadastro;
	private Nota nota;
	private Aula aula; // podera ser alterado
	
	public Professor(Integer id,String nome, String email, String senha) {
		this(id,nome, email, senha, null, null, null);
	}
	
	public Professor(Integer id,String nome, String email, String senha, LocalDateTime data_cadastro, Nota nota, Aula aula) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.data_cadastro = data_cadastro;
		this.nota = nota;
		this.aula = aula;
	}
	
	public Integer getId(){
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	/* public void setEmail(String email) {
		this.email = email;
	} */
	public String getSenha() {
		return senha;
	}
	/* public void setSenha(String senha) {
		this.senha = senha;
	} */
	public LocalDateTime getData() {
		return data_cadastro;
	}
	
	public Nota getNota() {
		return nota;
	}
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	public Aula getAula() {
		return aula;
	}
	public void setAula(Aula aula) {
		this.aula = aula;
	}
}
