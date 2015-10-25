package br.com.isoftgoal.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Em Validação")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoEmValidacao extends SituacaoProjeto {

	public SituacaoProjetoEmValidacao() {
		super(3, "Em Validação");
	}
	
	public SituacaoProjetoEmValidacao(Projeto projeto) {
		this();
		this.projeto = projeto;
	}
	
	@Override
	public void abrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi aberto, desenvolvido e está em validação.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoEmDesenvolvimento(getProjeto()));
	}

	@Override
	public void aguardarValidacao() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já está em validação.");
	}

	@Override
	public void concluir(Usuario usuarioAtual) {
		if(usuarioAtual.equals(projeto.getUsuarioCriador())) {
			this.projeto.setSituacaoProjeto(new SituacaoProjetoConcluso(getProjeto()));
		} else {
			throw new IllegalStateException(usuarioAtual.getNomeDeUsuario() + ", você não tem permissão para concluir o Projeto " + getProjeto().getNome());
		}
	}

	@Override
	public void cancelar() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoCancelado(getProjeto()));
	}

	@Override
	public void reabrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi aberto, desenvolvido e está em validação.");
	}

}
