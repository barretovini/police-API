package com.gft.crime.api.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Delegacia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Endereco enderecoDelegacia;

	@Pattern(regexp = "^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$")
	private String telefone;

	@NotNull
	private String batalhao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Endereco getEnderecoDelegacia() {
		return enderecoDelegacia;
	}

	public void setEnderecoDelegacia(Endereco enderecoDelegacia) {
		this.enderecoDelegacia = enderecoDelegacia;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getBatalhao() {
		return batalhao;
	}

	public void setBatalhao(String batalhao) {
		this.batalhao = batalhao;
	}
}