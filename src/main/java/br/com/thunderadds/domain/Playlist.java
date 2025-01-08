package br.com.thunderadds.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String genero;
//    @OneToMany
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Musica> musicas = new ArrayList<>();
    private Long idUsuario;
    private LocalDateTime data = LocalDateTime.now();
    
    public Playlist() { }
    
    public Playlist(Long idUsuario) {
	this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
//    public List<Musica> getMusicas() {
//        return musicas;
//    }
//    public void setMusicas(List<Musica> musicas) {
//        this.musicas = musicas;
//    }
    public LocalDateTime getData() {
        return data;
    }
    public void setData(LocalDateTime data) {
        this.data = data;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

}
