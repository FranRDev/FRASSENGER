package com.frassenger.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

import com.frassenger.modelo.PrincipalServidor;
import com.frassenger.vista.PanelServidor;

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
		
		comando = ae.getActionCommand();
		
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
	
	public void ponerMensajeServidor(String mensaje) {
		panel.anhadirMensaje(mensaje);
	}
	
	public void cambiarClientesConectados(int numeroClientes) {
		panel.cambiarEtiquetaClientesConectados("Clientes conectados: " + numeroClientes + "/" + PrincipalServidor.SERVIDOR_MAXIMO_CONEXIONES);
	}
}