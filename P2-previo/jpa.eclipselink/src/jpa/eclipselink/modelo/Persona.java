package jpa.eclipselink.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Persona {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private String id;
  private String nombre;
  private String apellidos;

  private Familia familia;

  private String campoSinSentido = "";

  private List<Empleo> listaEmpleos = new ArrayList<Empleo>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  @ManyToOne
  public Familia getFamilia() {
    return familia;
  }

  public void setFamilia(Familia familia) {
    this.familia = familia;
  }

  @Transient
  public String getCampoSinSentido() {
    return campoSinSentido;
  }

  public void setCampoSinSentido(String campoSinSentido) {
    this.campoSinSentido = campoSinSentido;
  }

  @OneToMany
  public List<Empleo> getListaEmpleos() {
    return this.listaEmpleos;
  }

  public void setListaEmpleos(List<Empleo> listaEmpleos) {
    this.listaEmpleos = listaEmpleos;
  }

}