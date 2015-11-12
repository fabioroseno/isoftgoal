package br.com.isoftgoal.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.isoftgoal.dominio.abstracts.SituacaoProjeto;
import entities.Context;
import entities.annotations.Editor;
import entities.annotations.PropertyDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import entities.descriptor.PropertyType;

@Entity
@Table(name = "projetos")
@Views({
    @View(name = "projetos",
            title = "br.com.isoftgoal.dominio.Projeto.view.projetos.title",
            members = "Projetos[nome; descricao; situacaoProjeto; *dataCadastro; dataInicio; dataPrevisaoTermino; dataTermino]:2, foto", 
            template = "@CRUD+@PAGER",
            roles = "Admin,Vip")
})
public class Projeto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    @Column(name="cod_projeto")
    private Integer codProjeto;

    @NotEmpty
    @Column(length = 60)
    @PropertyDescriptor(displayWidth = 40)
    private String nome;
    
    @Lob
    @NotEmpty
    private String descricao;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cod_situacao_projeto")
    private SituacaoProjeto situacaoProjeto = new SituacaoProjetoAberto(this);
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cod_usuario_criador")
    private Usuario usuarioCriador = (Usuario) Context.getCurrentUser();

    //TODO: Incluir Status do Projeto e a Lista de Requisitos
    
    @NotNull
    @Column(name="dt_cadastro")
    @PropertyDescriptor(readOnly = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro = new Date();

    @Column(name="dt_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;

    @NotNull
    @Column(name="dt_previsao_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrevisaoTermino;
    
    @Column(name="dt_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTermino;
    
    @Lob
    @Editor(propertyType = PropertyType.IMAGE)
    private byte[] foto;

	public Integer getCodProjeto() {
		return codProjeto;
	}

	public void setCodProjeto(Integer codProjeto) {
		this.codProjeto = codProjeto;
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

	public SituacaoProjeto getSituacaoProjeto() {
		return situacaoProjeto;
	}

	public void setSituacaoProjeto(SituacaoProjeto situacaoProjeto) {
		this.situacaoProjeto = situacaoProjeto;
	}

	public Usuario getUsuarioCriador() {
		return usuarioCriador;
	}

	public void setUsuarioCriador(Usuario usuarioCriador) {
		this.usuarioCriador = usuarioCriador;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataPrevisaoTermino() {
		return dataPrevisaoTermino;
	}

	public void setDataPrevisaoTermino(Date dataPrevisaoTermino) {
		this.dataPrevisaoTermino = dataPrevisaoTermino;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
