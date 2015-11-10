package br.com.isoftgoal.dominio.abstracts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.isoftgoal.dominio.Projeto;
import br.com.isoftgoal.dominio.Usuario;
import entities.annotations.EntityDescriptor;

@Entity
@Table(name = "situacoes_projeto")
@NamedQueries({
	@NamedQuery(name = "ChecarSituacao", query = " From SituacaoProjeto sp Where sp.codSituacaoProjeto = :codSituacaoProjeto ")})
@EntityDescriptor(hidden = true)
public abstract class SituacaoProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SituacaoProjeto(Integer codSituacaoProjeto, String descricao) {
		this.codSituacaoProjeto = codSituacaoProjeto;
		this.descricao = descricao;
	}

	@Id
	@Column(name = "cod_situacao_projeto")
	private Integer codSituacaoProjeto;

	@Column(length = 50, nullable = false)
	private String descricao;

	@Transient
	protected Projeto projeto;

	public abstract void abrir();
	
	public abstract void aguardarDesenvolvimento();
	
	public abstract void aguardarValidacao();
	
	public abstract void concluir(Usuario usuarioCriador);
	
	public abstract void cancelar();
	
	public abstract void reabrir();
	
	public Integer getCodSituacaoProjeto() {
		return codSituacaoProjeto;
	}

	public void setCodSituacaoProjeto(Integer codSituacaoProjeto) {
		this.codSituacaoProjeto = codSituacaoProjeto;
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
		return codSituacaoProjeto != null ? descricao : "Aberto";
	}
	
}
