package com.frassenger.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.frassenger.controlador.ControladorCliente;

/**
 * Clase Cliente.
 * 
 * @author Francisco Rodríguez García
 */
public class Cliente {
	
	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private ControladorCliente controlador;
	private Socket cliente;
	private BufferedReader flujoEntrada;
	private PrintWriter flujoSalida;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor de Cliente.
	 * @param controlador Controlador.
	 * @param cliente Socket cliente.
	 * @throws IOException Devuelve excepciones.
	 */
	public Cliente(ControladorCliente controlador, Socket cliente) throws IOException {
		this.controlador = controlador;
		this.cliente = cliente;
		this.flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		this.flujoSalida = new PrintWriter(cliente.getOutputStream(), true);
	}
	
	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Maneja los mensajes recibidos.
	 */
	public void manejarMensaje() {
		String mensaje;
		
		// Se obtiene el mensaje.
		try {
			mensaje = recibirMensajeTCP().trim();
			
			switch (mensaje) {
			
			// Si el mensaje es un código actualizar...
			case PrincipalServidor.SERVIDOR_CODIGO_ACTUALIZAR:
				// Se muestra el siguiente mensaje que se recibe con la actualización.
				controlador.ponerMensajeCliente("SERVIDOR --> Usuarios conectados: " + recibirMensajeTCP().trim());
				break;
				
			// Si el mensaje es un código de salida...
			case PrincipalServidor.SERVIDOR_CODIGO_SALIDA:
				controlador.salir();
				controlador.ponerMensajeCliente(">>> SERVIDOR DESCONECTADO...");
				break;
			
			// Si el mensaje no es ningún código...
			default:
				// Se muestra el mensaje en el chat.
				controlador.ponerMensajeCliente(mensaje);
				break;
			}
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Recibe un mensaje TCP.
	 * @return Devuelve el mensaje recibido.
	 * @throws IOException Puede devolver una excepción.
	 */
	public String recibirMensajeTCP() throws IOException {
		String mensaje;
		
		// Se pone a null.
		mensaje = null;
		
		// Bucle hasta que llega un mensaje.
		do {
			// Se lee un mensaje.
			mensaje = flujoEntrada.readLine().trim();
			
		// Mientras el mensaje sea nulo, se repite el proceso.
		} while (mensaje == null);
		
		return mensaje;
	}
	
	/**
	 * Envía un mensaje TCP.
	 * @param mensaje Mensaje a enviar.
	 */
	public void enviarMensajeTCP(String mensaje) {
		flujoSalida.println(mensaje);
	}
	
	/**
	 * Cierra la conexión.
	 */
	public void cerrarConexion() {
		try {
			// Se cierran los flujos y el cliente.
			flujoEntrada.close();
			flujoSalida.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}