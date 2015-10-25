package br.com.isoftgoal.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Reaberto")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoReaberto extends SituacaoProjeto {
	
	public SituacaoProjetoReaberto() {
		super(6, "Reaberto");
	}
	
	public SituacaoProjetoReaberto(Projeto projeto) {
		this();
		this.projeto = projeto;
	}

	@Override
	public void abrir() {
		throw new IllegalStateException("Projeto " + this.projeto.getNome() + " j� est� aberto.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoEmDesenvolvimento(this.projeto));
	}

	@Override
	public void aguardarValidacao() {
		throw new IllegalStateException("Projeto " + this.projeto.getNome() + " deve ser desenvolvido.");
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
		this.projeto.setSituacaoProjeto(new SituacaoProjetoCancelado(this.projeto));
	}

	@Override
	public void reabrir() {
		throw new IllegalStateException("Projeto " + this.projeto.getNome() + " j� foi reaberto.");
	}

}
