package modelo;

import java.time.LocalDate;

public class Aluno {
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private LocalDate data_cadastro;
	private Nota nota;
	private Reserva reserva; // podera ser alterado
	
	public Aluno(String nome, String email, String senha) {
		this(nome, email, senha, null, null, null);
	}
	
	public Aluno(String nome, String email, String senha, LocalDate data_cadastro, Nota nota, Reserva reserva) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.data_cadastro = data_cadastro;
		this.nota = nota;
		this.reserva = reserva;
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
	public LocalDate getData() {
		return data_cadastro;
	}
	public Nota getNota() {
		return nota;
	}
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
}
