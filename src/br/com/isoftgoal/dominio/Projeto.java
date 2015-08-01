package br.com.isoftgoal.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import entities.annotations.PropertyDescriptor;

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

    //TODO: Incluir Status do Projeto e a Lista de Requisitos
    
    @NotNull
    @Column(name="dt_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCadastro;

    @Column(name="dt_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicio;

    @Column(name="dt_previsao_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtPrevisaoTermino;
    
    @Column(name="dt_termino")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtTermino;

}
