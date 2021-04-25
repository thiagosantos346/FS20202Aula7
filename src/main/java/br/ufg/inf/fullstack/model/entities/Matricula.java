package br.ufg.inf.fullstack.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_matricula")
public class Matricula implements Serializable {
	/*
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_matricula")
	private Integer idMatricula;

	@OneToOne
	@JoinColumn(name="id_aluno")
	private Aluno aluno;

	@OneToOne 
	@JoinColumn(name="id_oferta")
	private Oferta oferta;

	public Matricula(){}

	public Matricula(Integer idMatricula, Aluno aluno, Oferta oferta) {
		super();
		this.idMatricula = idMatricula;
		this.aluno = aluno;
		this.oferta = oferta;
	}

	public Integer getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(Integer idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	@Override
	public String toString() {
		return "Matricula [idMatricula=" + idMatricula + ", aluno=" + aluno + ", oferta=" + oferta + "]";
	}

}
