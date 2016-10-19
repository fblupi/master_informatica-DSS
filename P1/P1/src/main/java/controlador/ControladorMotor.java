package controlador;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "controladorMotor", eager = true)
@SessionScoped
public class ControladorMotor implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@ManagedProperty(value = "#{textoTitulo}")
	private String textoTitulo;

	@ManagedProperty(value = "#{colorTitulo}")
	private String colorTitulo;
    
	@ManagedProperty(value = "#{textoBtnOnOff}")
	private String textoBtnOnOff;
    
	@ManagedProperty(value = "#{colorBtnOnOff}")
	private String colorBtnOnOff;
    
	@ManagedProperty(value = "#{textoBtnAcelerar}")
	private String textoBtnAcelerar;
    
	@ManagedProperty(value = "#{colorBtnAcelerar}")
	private String colorBtnAcelerar;
	
	public void actualizarBtnOnOff(ActionEvent e) {
		
	}
	
	public void actualizarBtnAcelerar(ActionEvent e) {
		
	}
	
	public String getTextoTitulo() {
		return textoTitulo;
	}

	public void setTextoTitulo(String textoTitulo) {
		this.textoTitulo = textoTitulo;
	}

	public String getColorTitulo() {
		return colorTitulo;
	}

	public void setColorTitulo(String colorTitulo) {
		this.colorTitulo = colorTitulo;
	}

	public String getTextoBtnOnOff() {
		return textoBtnOnOff;
	}

	public void setTextoBtnOnOff(String textoBtnOnOff) {
		this.textoBtnOnOff = textoBtnOnOff;
	}

	public String getColorBtnOnOff() {
		return colorBtnOnOff;
	}

	public void setColorBtnOnOff(String colorBtnOnOff) {
		this.colorBtnOnOff = colorBtnOnOff;
	}

	public String getTextoBtnAcelerar() {
		return textoBtnAcelerar;
	}

	public void setTextoBtnAcelerar(String textoBtnAcelerar) {
		this.textoBtnAcelerar = textoBtnAcelerar;
	}

	public String getColorBtnAcelerar() {
		return colorBtnAcelerar;
	}

	public void setColorBtnAcelerar(String colorBtnAcelerar) {
		this.colorBtnAcelerar = colorBtnAcelerar;
	}
}
