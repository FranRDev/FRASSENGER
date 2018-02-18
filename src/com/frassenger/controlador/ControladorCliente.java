package com.frassenger.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.frassenger.modelo.Cliente;
import com.frassenger.modelo.PrincipalServidor;
import com.frassenger.vista.PanelCliente;

/**
 * Controlador de PrincipalCliente.
 * 
 * @author Francisco Rodríguez García
 */
public class ControladorCliente implements ActionListener {
	
	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private PanelCliente panel;
	private Cliente cliente;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor del Controlador PrincipalCliente.
	 * @param panel Panel que controla.
	 */
	public ControladorCliente(PanelCliente panel) {
		this.panel = panel;
	}
	
	//=====================================================================================================
	// SETTERS
	//=====================================================================================================
	/**
	 * Set de la clase Cliente.
	 * @param cliente Objeto Cliente.
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		String mensaje;
		
		// Se obtiene el mensaje a enviar.
		mensaje = panel.obtenerMensajeEscrito().trim();
		
		// Si no está vacío.
		if (mensaje.length() > 0) {
			
			// Se envía.
			cliente.enviarMensajeTCP(mensaje);
			
			// Se limpia el cuadro de texto del cliente.
			panel.limpiarCuadroDeTexto();
		}
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
	public void salir() {
		// Se envía un código de salida al servidor.
		cliente.enviarMensajeTCP(PrincipalServidor.SERVIDOR_CODIGO_SALIDA);
		
		// Se cierra la conexión del cliente.
		cliente.cerrarConexion();
		
		// Se pone un mensaje en el cliente.
		panel.anhadirMensaje(">>> TE HAS DESCONECTADO DEL SERVIDOR...");
		
		// Se deshabilitan los componentes de la vista.
		panel.deshabilitarVista();
	}

	/**
	 * Muestra los usuarios conectados.
	 */
	private void verUsuarios() {
		// Se envía un código de listar usuarios al servidor.
		cliente.enviarMensajeTCP(PrincipalServidor.SERVIDOR_CODIGO_LISTAR);
	}
	
	/**
	 * Pone un mensaje en el chat del cliente.
	 * @param mensaje Mensaje que pone.
	 */
	public void ponerMensajeCliente(String mensaje) {
		panel.anhadirMensaje(mensaje);
	}
}