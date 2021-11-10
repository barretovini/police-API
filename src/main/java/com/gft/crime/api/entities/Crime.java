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
public class Crime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@NotNull
	private Criminoso criminoso;
	
	@OneToOne
	@NotNull
	private Vitima vitima;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataCrime;
	
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataCrime() {
		return dataCrime;
	}

	public void setDataCrime(Date dataCrime) {
		this.dataCrime = dataCrime;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
