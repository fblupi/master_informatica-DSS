package jpa.eclipselink.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empleo {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private int id;
  private double salario;
  private String descripcionTrabajo;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getSalerio() {
    return salario;
  }

  public void setSalario(double salario) {
    this.salario = salario;
  }

  public String getDescripcionTrabajo() {
    return descripcionTrabajo;
  }

  public void setDescripcionTrabajo(String descripcionTrabajo) {
    this.descripcionTrabajo = descripcionTrabajo;
  }
}