package com.frassenger.modelo;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.frassenger.controlador.ControladorServidor;
import com.frassenger.vista.PanelServidor;

/**
 * PrincipalServidor.
 * 
 * @author Francisco Rodríguez García
 */
public class PrincipalServidor {
	
	//=====================================================================================================
	// CONSTANTES
	//=====================================================================================================
	public static final int SERVIDOR_PUERTO = 1150;
	public static final int SERVIDOR_MAXIMO_CONEXIONES = 5;
	public static final String SERVIDOR_CODIGO_NOMBRE_USUARIO = "/nombre_usuario";
	public static final String SERVIDOR_CODIGO_SALIDA = "/salir";
	public static final String SERVIDOR_CODIGO_LISTAR = "/listar";
	public static final String SERVIDOR_CODIGO_ACTUALIZAR = "/actualizar";
	public static final String SERVIDOR_CODIGO_PLAZA_LIBRE = "/plaza";
	public static final String SERVIDOR_CODIGO_LLENO = "/lleno";
	
	//=====================================================================================================
	// VARIABLES ESTÁTICAS
	//=====================================================================================================
	private static JFrame ventana;
	private static PanelServidor panel;
	private static ControladorServidor controlador;
	private static ServerSocket servidor;
	private static Clientes clientes;

	//=====================================================================================================
	// PRINCIPAL
	//=====================================================================================================
	public static void main(String[] args) {
		// Se crea y configura la ventana.
		crearYConfigurarVentana();
		
		try {
			// Se inicia el servidor.
			iniciarServidor();
			
			// Mientras el servidor esté conectado...
			while (!servidor.isClosed()) {
				
				// Se manejan los clientes.
				manejarClientes();
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(ventana, "Error:\n" + e.getMessage(), "FRASSENGER - ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//=====================================================================================================
	// GETTERS
	//=====================================================================================================
	/**
	 * Get del Controlador.
	 * @return Devuelve el Controlador.
	 */
	public static ControladorServidor getControlador() {
		return controlador;
	}

	/**
	 * Get de los Clientes.
	 * @return Devuelve los Clientes.
	 */
	public static Clientes getClientes() {
		return clientes;
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	private static void crearYConfigurarVentana() {
		// Se inicializa la ventana, con el panel y su controlador asociado.
		ventana = new JFrame("FRASSENGER - SERVIDOR");
		panel = new PanelServidor();
		ventana.setContentPane(panel);
		controlador = new ControladorServidor(panel);
		panel.controlador(controlador);
		
		// Configuración de la ventana.
		ventana.pack();
		ventana.setVisible(true);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static void iniciarServidor() throws IOException {
		try {
			// Se crea e inicia el servidor.
			servidor = new ServerSocket(SERVIDOR_PUERTO);
			
			// Se pasa al controlador.
			controlador.setServidor(servidor);
			
			// Se crea la lista de clientes.
			clientes = new Clientes();
			
			// Se muestra un mensaje en el servidor.
			controlador.ponerMensajeServidor(">>> SERVIDOR INICIADO...");
			
		} catch (IOException e) {
			throw new IOException("Error al iniciar el servidor.");
		}
	}

	private static void manejarClientes() {
		Socket cliente;
		HiloServidor hiloServidor;
		
		try {
			// Se guarda el cliente conectado.
			cliente = servidor.accept();
			
			// Se crea e inicia un hilo con el cliente conectado.
			hiloServidor = new HiloServidor(cliente, controlador);
			hiloServidor.start();
			
			// Si ya está lleno el servidor, se envía un mensaje al cliente, y se cierra.
			if (clientes.obtenerNumeroClientes() == SERVIDOR_MAXIMO_CONEXIONES) {
				hiloServidor.enviarMensajeTCP(SERVIDOR_CODIGO_LLENO);
				cliente.close();
				hiloServidor = null;
				
			} else {
				hiloServidor.enviarMensajeTCP(SERVIDOR_CODIGO_PLAZA_LIBRE);
			}
			
		} catch (IOException e) {
			// TODO: Tratar excepción.
		}
	}
	
//	public static void cerrarServidor() {
//		try {
//			// Se cierra el servidor.
//			servidor.close();
//			
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(ventana, "Error al cerrar el servidor.", "FRASSENGER - ERROR", JOptionPane.ERROR_MESSAGE);
//		}
//	}

	/**
	 * Envía un mensaje tanto al propio servidor, como a todos los clientes.
	 * @param mensaje Mensaje que envía.
	 */
	public static void enviarMensajeATodos(String mensaje) {
		// Se envía a todos los clientes.
		clientes.mostrarMensajeATodos(mensaje);
		
		// Se envía al servidor.
		controlador.ponerMensajeServidor(mensaje);
	}
	
	/**
	 * Envía un mensaje al propio servidor.
	 * @param mensaje Mensaje que envía.
	 */
	public static void enviarMensajeAServidor(String mensaje) {
		controlador.ponerMensajeServidor(mensaje);
	}
	
	/**
	 * Añade un cliente al servidor.
	 * @param hiloServidor Hilo con cliente que añade.
	 */
	public static void anhadirCliente(HiloServidor hiloServidor) {
		String nombreUsuario;
		int numeroClientes;
		
		// Se obtiene el nombre de usuario.
		nombreUsuario = hiloServidor.getNombreUsuario();
		
		// Se añade el cliente.
		clientes.anhadirCliente(nombreUsuario, hiloServidor);
		
		// Se avisa a todos de la incorporación.
		clientes.actualizarClientesConectados();
		
		// Se obtiene el número de clientes.
		numeroClientes = clientes.obtenerNumeroClientes();
		
		// Se cambia el marcador de clientes conectados en el servidor.
		controlador.cambiarClientesConectados(numeroClientes);
	}
	
	/**
	 * Quita un cliente del servidor.
	 * @param nombreUsuario Nombre de usuario del cliente.
	 */
	public static void quitarCliente(String nombreUsuario) {
		int numeroClientes;
		
		// Se elimina el cliente del servidor.
		clientes.eliminarCliente(nombreUsuario);
		
		// Se avisa a todos de la ausencia.
		clientes.actualizarClientesConectados();
		
		// Se obtiene el número de clientes.
		numeroClientes = clientes.obtenerNumeroClientes();
		
		// Se cambia el marcador de clientes conectados en el servidor.
		controlador.cambiarClientesConectados(numeroClientes);
	}
}