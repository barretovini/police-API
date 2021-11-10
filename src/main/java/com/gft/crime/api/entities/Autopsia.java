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
public class Autopsia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@NotNull
	private Vitima vitima;
	
	@OneToOne
	@NotNull
	private Legista legista;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataAutopsia;
	
	@NotNull
	private String laudo;
	
	public Autopsia() {
		
	}
	
	public Autopsia(@NotNull Vitima vitima, @NotNull Legista legista) {
		this.vitima = vitima;
		this.legista = legista;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vitima getVitima() {
		return vitima;
	}

	public void setVitima(Vitima vitima) {
		this.vitima = vitima;
	}

	public Legista getLegista() {
		return legista;
	}

	public void setLegista(Legista legista) {
		this.legista = legista;
	}

	public Date getDataAutopsia() {
		return dataAutopsia;
	}

	public void setDataAutopsia(Date dataAutopsia) {
		this.dataAutopsia = dataAutopsia;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}
	
	
	
}
