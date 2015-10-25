package br.com.isoftgoal.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Aberto")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoAberto extends SituacaoProjeto {
	
	public SituacaoProjetoAberto() {
		super(1, "Aberto");
	}
	
	public SituacaoProjetoAberto(Projeto projeto) {
		this();
		this.projeto = projeto;
	}

	@Override
	public void abrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já está aberto.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		this.projeto.setSituacaoProjeto(new SituacaoProjetoEmDesenvolvimento(getProjeto()));
	}

	@Override
	public void aguardarValidacao() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " deve ser desenvolvido.");
	}

	@Override
	public void concluir(Usuario usuarioAtual) {
		if(usuarioAtual.equals(projeto.getUsuarioCriador())) {
			throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " deve ser desenvolvido e validado.");
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
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já está aberto.");
	}

}
