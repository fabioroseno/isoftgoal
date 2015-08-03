package br.com.isoftgoal.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

public abstract class Situacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cod_situacao")
	private Integer codSituacao;

	@Column(length = 50, nullable = false)
	private String descricao;

	@Transient
	protected Projeto projeto;

	public Integer getCodSituacao() {
		return codSituacao;
	}

	public void setCodSituacao(Integer codSituacao) {
		this.codSituacao = codSituacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	@Override
	public String toString() {
		return codSituacao != null ? descricao : "Aberto";
	}
	
}
