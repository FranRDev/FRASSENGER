package com.frassenger.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.frassenger.vista.PanelCliente;

/**
 * Controlador de Cliente.
 * 
 * @author Francisco Rodríguez García
 */
public class ControladorCliente implements ActionListener {
	
	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private PanelCliente panel;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor del Controlador de Cliente.
	 * @param panel Panel que controla.
	 */
	public ControladorCliente(PanelCliente panel) {
		this.panel = panel;
	}

	//=====================================================================================================
	// MÉTODOS SOBREESCRITOS
	//=====================================================================================================
	/**
	 * Se ejecuta cuando se ha producido una acción sobre elementos de la vista con escuchadores de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
		case "enviar":
			enviar();
			break;
		case "limpiar":
			limpiar();
			break;
		case "salir":
			salir();
			break;
		case "usuarios":
			verUsuarios();
			break;
		default:
			break;
		}
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Envía un mensaje.
	 */
	private void enviar() {
		// TODO: Método de enviar mensaje.
	}

	/**
	 * Limpia el chat.
	 */
	private void limpiar() {
		panel.limpiarChat();
	}

	/**
	 * Sale de la aplicación.
	 */
	private void salir() {
		// TODO: Método para salir.
	}

	/**
	 * Muestra los usuarios conectados.
	 */
	private void verUsuarios() {
		// TODO: Método para ver los usuarios.
	}
}