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
	
	private final String tTituloApagado = "Apagado";
	private final String tTituloEncendido = "Encendido";
	private final String tTituloAcelerando = "Acelerando";
	
	private final String cTituloApagado = "red";
	private final String cTituloEncendido = "green";
	private final String cTituloAcelerando = "blue";
	
	private final String tBtnOnOffApagado = "Encender";
	private final String tBtnOnOffEncendido = "Apagar";
	
	private final String cBtnOnOffApagado = "green";
	private final String cBtnOnOffEncendido = "red";
	
	private final String cBtnAcelerarApagado = "grey";
	private final String cBtnAcelerarEncendido = "black";

	private int estado = APAGADO;
    
	@ManagedProperty(value = "#{textoTitulo}")
	private String textoTitulo = tTituloApagado;

	@ManagedProperty(value = "#{colorTitulo}")
	private String colorTitulo = cTituloApagado;
    
	@ManagedProperty(value = "#{textoBtnOnOff}")
	private String textoBtnOnOff = tBtnOnOffApagado;
    
	@ManagedProperty(value = "#{colorBtnOnOff}")
	private String colorBtnOnOff = cBtnOnOffApagado;
    
	@ManagedProperty(value = "#{colorBtnAcelerar}")
	private String colorBtnAcelerar = cBtnAcelerarApagado;
	
	private void encender() {
		textoTitulo = tTituloEncendido;
		colorTitulo = cTituloEncendido;
		textoBtnOnOff = tBtnOnOffEncendido;
		colorBtnOnOff = cBtnOnOffEncendido;
		colorBtnAcelerar = cBtnAcelerarEncendido;
	}
	
	private void apagar() {
		textoTitulo = tTituloApagado;
		colorTitulo = cTituloApagado;
		textoBtnOnOff = tBtnOnOffApagado;
		colorBtnOnOff = cBtnOnOffApagado;
		colorBtnAcelerar = cBtnAcelerarApagado;
	}

	private void acelerar() {
		textoTitulo = tTituloAcelerando;
		colorTitulo = cTituloAcelerando;
	}
	
	public void actualizarBtnOnOff(ActionEvent e) {
		switch (estado) {
		case APAGADO:
			encender();
			estado = ENCENDIDO;
			break;
		case ENCENDIDO:
			apagar();
			estado = APAGADO;
			break;
		case ACELERANDO:
			apagar();
			estado = APAGADO;
			break;
		default:
			break;
		}
	}
	
	public void actualizarBtnAcelerar(ActionEvent e) {
		switch (estado) {
		case APAGADO:
			break;
		case ENCENDIDO:
			acelerar();
			estado = ACELERANDO;
			break;
		case ACELERANDO:
			acelerar();
			estado = ACELERANDO;
			break;
		default:
			break;
		}
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
	
	public String getColorBtnAcelerar() {
		return colorBtnAcelerar;
	}

	public void setColorBtnAcelerar(String colorBtnAcelerar) {
		this.colorBtnAcelerar = colorBtnAcelerar;
	}
}
