package br.com.isoftgoal.dominio;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.annotations.EntityDescriptor;
import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;

@Entity
@DiscriminatorValue("Concluso")
@EntityDescriptor(hidden = true)
public class SituacaoProjetoConcluso extends SituacaoProjeto implements Serializable {

	private static final long serialVersionUID = 1L;

	public SituacaoProjetoConcluso() {
		super(4, "Concluso");
	}
	
	public SituacaoProjetoConcluso(Projeto projeto) {
		this();
		this.projeto = projeto;
	}
	
	@Override
	public void abrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
	}

	@Override
	public void aguardarDesenvolvimento() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
	}

	@Override
	public void aguardarValidacao() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
	}

	@Override
	public void concluir(Usuario usuarioAtual) {
		if(usuarioAtual.equals(projeto.getUsuarioCriador())) {
			throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
		} else {
			throw new IllegalStateException(usuarioAtual.getNomeDeUsuario() + ", você não tem permissão para concluir o Projeto " + getProjeto().getNome());
		}
	}

	@Override
	public void cancelar() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
	}

	@Override
	public void reabrir() {
		throw new IllegalStateException("O Projeto " + getProjeto().getNome() + " já foi concluso.");
	}

}
