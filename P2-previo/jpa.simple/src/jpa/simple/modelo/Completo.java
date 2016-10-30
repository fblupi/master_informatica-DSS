package jpa.simple.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Completo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String resumen;
  private String descripcion;
  public String getResumen() {
    return resumen;
  }
  public void setResumen(String resumen) {
    this.resumen = resumen;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  @Override
  public String toString() {
    return "Completo [resumen=" + resumen + ", descripcion=" + descripcion + "]";
  }
}