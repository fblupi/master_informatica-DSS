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
	
	private final int APAGADO = 0;
	private final int ENCENDIDO = 1;
	private final int ACELERANDO = 2;
	private final String TEXTO_APAGADO = "Apagado";
	private final String TEXTO_ENCENDIDO = "Encendido";
	private final String TEXTO_ACELERANDO = "Acelerando";
	private final String TEXTO_ENCENDER = "Encender";
	private final String TEXTO_APAGAR = "Apagar";
	private final String COLOR_ROJO = "red";
	private final String COLOR_VERDE = "green";
	private final String COLOR_AZUL = "blue";
	private final String COLOR_GRIS = "grey";
	private final String COLOR_NEGRO = "black";

	private int estado = APAGADO;
    
	@ManagedProperty(value = "#{textoTitulo}")
	private String textoTitulo;

	@ManagedProperty(value = "#{colorTitulo}")
	private String colorTitulo;
    
	@ManagedProperty(value = "#{textoBtnOnOff}")
	private String textoBtnOnOff;
    
	@ManagedProperty(value = "#{colorBtnOnOff}")
	private String colorBtnOnOff;
    
	@ManagedProperty(value = "#{colorBtnAcelerar}")
	private String colorBtnAcelerar;
	
	private void encender() {
		estado = ENCENDIDO;
		textoTitulo = TEXTO_ENCENDIDO;
		colorTitulo = COLOR_VERDE;
		textoBtnOnOff = TEXTO_APAGAR;
		colorBtnOnOff = COLOR_ROJO;
		colorBtnAcelerar = COLOR_NEGRO;
	}
	
	private void apagar() {
		estado = APAGADO;
		textoTitulo = TEXTO_APAGADO;
		colorTitulo = COLOR_ROJO;
		textoBtnOnOff = TEXTO_ENCENDER;
		colorBtnOnOff = COLOR_VERDE;
		colorBtnAcelerar = COLOR_GRIS;
	}

	private void acelerar() {
		estado = ACELERANDO;
		textoTitulo = TEXTO_ACELERANDO;
		colorTitulo = COLOR_AZUL;
	}
	
	public void actualizarBtnOnOff(ActionEvent e) {
		switch (estado) {
		case APAGADO:
			encender();
			break;
		case ENCENDIDO:
			apagar();
			break;
		case ACELERANDO:
			apagar();
			break;
		default:
			// idle
			break;
		}
	}
	
	public void actualizarBtnAcelerar(ActionEvent e) {
		switch (estado) {
		case APAGADO:
			// idle
			break;
		case ENCENDIDO:
			acelerar();
			break;
		case ACELERANDO:
			acelerar();
			break;
		default:
			// idle
			break;
		}
	}
	
	public String getTextoTitulo() {
		if (textoTitulo != null) {
			return textoTitulo;
		} else {
			return TEXTO_APAGADO;
		}
	}

	public void setTextoTitulo(String textoTitulo) {
		this.textoTitulo = textoTitulo;
	}

	public String getColorTitulo() {
		if (colorTitulo != null) {
			return colorTitulo;
		} else {
			return COLOR_ROJO;
		}
	}

	public void setColorTitulo(String colorTitulo) {
		this.colorTitulo = colorTitulo;
	}

	public String getTextoBtnOnOff() {
		if (textoBtnOnOff != null) {
			return textoBtnOnOff;
		} else {
			return TEXTO_ENCENDER;
		}
	}

	public void setTextoBtnOnOff(String textoBtnOnOff) {
		this.textoBtnOnOff = textoBtnOnOff;
	}

	public String getColorBtnOnOff() {
		if (colorBtnOnOff != null) {
			return colorBtnOnOff;
		} else {
			return COLOR_VERDE;
		}
	}

	public void setColorBtnOnOff(String colorBtnOnOff) {
		this.colorBtnOnOff = colorBtnOnOff;
	}
	
	public String getColorBtnAcelerar() {
		if (colorBtnAcelerar != null) {
			return colorBtnAcelerar;
		} else {
			return COLOR_GRIS;
		}
	}

	public void setColorBtnAcelerar(String colorBtnAcelerar) {
		this.colorBtnAcelerar = colorBtnAcelerar;
	}
}
