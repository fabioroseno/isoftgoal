package br.com.isoftgoal.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;

@Entity
@Table(name = "softgoals")
@Views({
    @View(name = "softgoals",
            title = "br.com.isoftgoal.dominio.Softgoal.view.softgoals.title",
            members = "Softgoals[nome; descricao]", 
            template = "@CRUD+@PAGER",
            roles = "Admin")
})
public class Softgoal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name="cod_softgoal")
    private Integer codSoftgoal;
	
	@NotEmpty
	@Column(length = 80, unique = true)
	@PropertyDescriptor(displayWidth = 40)
	private String nome;
	
    @Lob
    @NotEmpty
    private String descricao;
    
	public Integer getCodSoftgoal() {
		return codSoftgoal;
	}

	public void setCodSoftgoal(Integer codSoftgoal) {
		this.codSoftgoal = codSoftgoal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
}
