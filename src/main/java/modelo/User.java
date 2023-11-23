package modelo;

import java.time.LocalDate;

public interface User {
    Integer id = null;
    String nome = null;
    String email = null;
    String senha = null;
    LocalDate data_cadastro = null;
    String tipo_user = null;
    String perfil = null;
    
    public Integer getId();
    public String getNome();
    public void setNome(String nome);
    public String getEmail();
    public String getSenha();
    public LocalDate getData_cadastro();
    public String getTipo_user();
    public String getPerfil();
}
