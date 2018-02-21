package com.frassenger.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import com.frassenger.modelo.PrincipalServidor;
import com.frassenger.vista.PanelServidor;

/**
 * Controlador de PrincipalServidor.
 * 
 * @author Francisco Rodríguez García
 */
public class ControladorServidor implements ActionListener {

	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private PanelServidor panel;
	private ServerSocket servidor;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor del Controlador PrincipalServidor.
	 * @param panel Panel que controla.
	 */
	public ControladorServidor(PanelServidor panel) {
		this.panel = panel;
	}
	
	//=====================================================================================================
	// SETTERS
	//=====================================================================================================
	/**
	 * Set del servidor.
	 * @param servidor Servidor que establece.
	 */
	public void setServidor(ServerSocket servidor) {
		this.servidor = servidor;
	}
	
	//=====================================================================================================
	// MÉTODOS SOBREESCRITOS
	//=====================================================================================================
	/**
	 * Se ejecuta cuando se ha producido una acción sobre elementos de la vista con escuchadores de acción.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		String comando;
		
		// Se obtiene el comando.
		comando = ae.getActionCommand();
		
		// Si el comando es desconectar, hace todo lo necesario para ello.
		if (comando.equalsIgnoreCase("desconectar")) {
			try {
				do {
					PrincipalServidor.getClientes().desconectarATodos();
					servidor.close();
					panel.deshabilitarBotonSalir();
					panel.deshabilitarAreaDeTexto();
					panel.cambiarEtiquetaClientesConectados("");
					panel.cambiarEtiquetaPuerto("");
					panel.cambiarEtiquetaEstadoServidor("SERVIDOR DESCONECTADO");
				} while (!servidor.isClosed());
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Poner mensaje en servidor.
	 * @param mensaje Mensaje que se pone.
	 */
	public void ponerMensajeServidor(String mensaje) {
		panel.anhadirMensaje(mensaje);
	}
	
	/**
	 * Cambiar número de clientes conectados.
	 * @param numeroClientes Número de clientes a poner.
	 */
	public void cambiarClientesConectados(int numeroClientes) {
		panel.cambiarEtiquetaClientesConectados("Clientes conectados: " + numeroClientes + "/" + PrincipalServidor.SERVIDOR_MAXIMO_CONEXIONES);
	}
}