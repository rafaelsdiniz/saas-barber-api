package br.com.saasbarber.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "barbeiro")
public class Barbeiro extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Telefone telefone;

    @ManyToOne(optional = false)
    @JoinColumn(name = "barbearia_id")
    private Barbearia barbearia;

    /** URL da foto no Cloudflare R2 */
    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    // GETTERS E SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Barbearia getBarbearia() {
        return barbearia;
    }

    public void setBarbearia(Barbearia barbearia) {
        this.barbearia = barbearia;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
