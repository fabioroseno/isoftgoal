package br.com.isoftgoal.dominio.abstracts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.isoftgoal.dominio.Projeto;
import br.com.isoftgoal.dominio.SituacaoProjetoCancelado;
import br.com.isoftgoal.dominio.SituacaoProjetoConcluso;
import br.com.isoftgoal.dominio.SituacaoProjetoEmDesenvolvimento;
import br.com.isoftgoal.dominio.SituacaoProjetoEmValidacao;
import br.com.isoftgoal.dominio.SituacaoProjetoReaberto;
import br.com.isoftgoal.dominio.Usuario;
import entities.Repository;
import entities.annotations.ActionDescriptor;
import entities.annotations.EntityDescriptor;

@Entity
@Table(name = "situacoes_projeto")
@EntityDescriptor(hidden = true)
public abstract class SituacaoProjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public SituacaoProjeto(Integer codSituacaoProjeto, String descricao) {
		this.codSituacaoProjeto = codSituacaoProjeto;
		this.descricao = descricao;
		insertsSituacoes();
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

    @ActionDescriptor(preValidate = false)
	private void insertsSituacoes() {
		if (Repository.queryCount(" From SituacaoProjetoAberto sp Where sp.descricao = :descricao ") == 0) {
			Repository.save(this);
			SituacaoProjeto situacaoEmDesenvolvimento = new SituacaoProjetoEmDesenvolvimento();
			Repository.save(situacaoEmDesenvolvimento);
			SituacaoProjeto situacaoProjetoEmValidacao = new SituacaoProjetoEmValidacao();
			Repository.save(situacaoProjetoEmValidacao);
			SituacaoProjeto situacaoProjetoConcluso = new SituacaoProjetoConcluso();
			Repository.save(situacaoProjetoConcluso);
			SituacaoProjeto situacaoProjetoCancelado = new SituacaoProjetoCancelado();
			Repository.save(situacaoProjetoCancelado);
			SituacaoProjeto situacaoProjetoReaberto = new SituacaoProjetoReaberto();
			Repository.save(situacaoProjetoReaberto);
		}
	}
	
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
