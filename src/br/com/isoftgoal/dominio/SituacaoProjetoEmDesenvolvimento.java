package br.com.isoftgoal.dominio;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Em Desenvolvimento")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoEmDesenvolvimento extends SituacaoProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	public SituacaoProjetoEmDesenvolvimento() {
		super(2, "Em Desenvolvimento");
	}
	
	public SituacaoProjetoEmDesenvolvimento(Projeto projeto) {
		this();
		this.projeto = projeto;
	}
	
	@Override
	public void abrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " j� est� aberto e em desenvolvimento.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " j� est� em desenvolvimento.");
	}

	@Override
	public void aguardarValidacao() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoEmValidacao(getProjeto()));
	}

	@Override
	public void concluir(Usuario usuarioAtual) {
		if(usuarioAtual.equals(projeto.getUsuarioCriador())) {
			this.projeto.setSituacaoProjeto(new SituacaoProjetoConcluso(getProjeto()));
		} else {
			throw new IllegalStateException(usuarioAtual.getNomeDeUsuario() + ", voc� n�o tem permiss�o para concluir o Projeto " + getProjeto().getNome());
		}
	}

	@Override
	public void cancelar() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoCancelado(getProjeto()));
	}

	@Override
	public void reabrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " j� est� aberto e em desenvolvimento.");
	}

}
