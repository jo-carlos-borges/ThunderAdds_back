package br.com.thunderadds.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    @Transient
    private String tokenSessao;
    @Transient
    private LocalDateTime ultimaSolicitacao;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getTokenSessao() {
        return tokenSessao;
    }
    public void setTokenSessao(String tokenSessao) {
        this.tokenSessao = tokenSessao;
    }
    public LocalDateTime getUltimaSolicitacao() {
        return ultimaSolicitacao;
    }
    public void setUltimaSolicitacao(LocalDateTime ultimaSolicitacao) {
        this.ultimaSolicitacao = ultimaSolicitacao;
    }

}
