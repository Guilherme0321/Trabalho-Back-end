package modelo;

import java.time.LocalDate;

public class Aluno implements User{
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private LocalDate data_cadastro;
	
	public Aluno(String email, String senha) { // construtor para o login
		this(null,null,email,senha);
	}
	
	public Aluno(Integer id,String nome, String email, String senha) {
		this(id,nome, email, senha, null);
	}
	
	public Aluno(Integer id,String nome, String email, String senha, LocalDate data_cadastro) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.data_cadastro = data_cadastro;
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
	public LocalDate getData_cadastro() {
		return data_cadastro;
	}
	public String getTipo_user() {
		 return "aluno";
	}
}
