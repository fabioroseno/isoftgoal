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
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtCadastro;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicio;


}
