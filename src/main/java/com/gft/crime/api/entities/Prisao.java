package com.gft.crime.api.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Prisao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@NotNull
	private Policial policial;
	
	@OneToOne
	@NotNull
	private Criminoso criminoso;
	
	@OneToOne
	@NotNull
	private Vitima vitima;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	
	@OneToOne
	@NotNull
	private Delegacia delegacia;
	
	@OneToOne
	@NotNull
	private Delegado delegado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Policial getPolicial() {
		return policial;
	}

	public void setPolicial(Policial policial) {
		this.policial = policial;
	}

	public Criminoso getCriminoso() {
		return criminoso;
	}

	public void setCriminoso(Criminoso criminoso) {
		this.criminoso = criminoso;
	}

	public Vitima getVitima() {
		return vitima;
	}

	public void setVitima(Vitima vitima) {
		this.vitima = vitima;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Delegacia getDelegacia() {
		return delegacia;
	}

	public void setDelegacia(Delegacia delegacia) {
		this.delegacia = delegacia;
	}

	public Delegado getDelegado() {
		return delegado;
	}

	public void setDelegado(Delegado delegado) {
		this.delegado = delegado;
	}
	
	
}
