package br.com.isoftgoal.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Cancelado")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoCancelado extends SituacaoProjeto {
	
	public SituacaoProjetoCancelado() {
		this.setCodSituacaoProjeto(5);
	}
	
	public SituacaoProjetoCancelado(Projeto projeto) {
		this();
		this.projeto = projeto;
	}

	@Override
	public void abrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " est� cancelado, tente reabr�-lo.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " est� cancelado, tente reabr�-lo.");
	}

	@Override
	public void aguardarValidacao() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " est� cancelado, tente reabr�-lo.");
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
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " j� est� cancelado.");
	}

	@Override
	public void reabrir() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoReaberto(getProjeto()));
	}

}
