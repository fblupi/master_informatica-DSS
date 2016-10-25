package prueba;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "controladorNavegacion", eager = true)
@RequestScoped
public class ControladorNavegacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{param.pageId}")
	private String pageId;
	
	// Auto-navegación usando un MB
	public String moverHaciaPagina1() {
		return "pagina1";
	}
	
	public String moverHaciaPagina2() {
		return "pagina2";
	}
	
	public String moverHaciaHome() {
		return "home";
	}
	
	// Navegación condicional
	public String mostrarPagina() {
		if (pageId == null) {
			return "home";
		}
		if (pageId.equals("1")) {
			return "pagina1";
		} else if (pageId.equals("2")) {
			return "pagina2";
		} else {
			return "home";
		}
	}
	
	// Navegación utilizando reglas de navegación
	public String procesarPagina1() {
		return "pagina";
	}
	
	public String procesarPagina2() {
		return "pagina";
	}
	
	// Getter y Setter
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}
