package com.github.sergejsamsonow.dataextractionunit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "extraction_units")
public class ExtractionUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "extraction_class")
	private String extractionClass;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExtractionClass() {
		return extractionClass;
	}

	public void setExtractionClass(String extractionClass) {
		this.extractionClass = extractionClass;
	}
	
}
