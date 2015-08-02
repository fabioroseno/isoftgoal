package br.com.isoftgoal.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.validator.constraints.NotEmpty;

import entities.annotations.PropertyDescriptor;

public class Softgoal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(name="cod_softgoal")
    private Integer codSoftgoal;
	
	@NotEmpty
	@Column(length = 80)
	@PropertyDescriptor(displayWidth = 40)
	private String nome;

    @Lob
    @NotEmpty
    private String descricao;
	 
}
