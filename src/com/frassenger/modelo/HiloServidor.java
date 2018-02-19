package com.frassenger.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.frassenger.controlador.ControladorServidor;

/**
 * Hilo del PrincipalServidor.
 * 
 * @author Francisco Rodríguez García
 */
public class HiloServidor extends Thread {
	
	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private Socket cliente;
	private ControladorServidor controlador;
	private BufferedReader flujoEntrada;
	private PrintWriter flujoSalida;
	private String nombreUsuario;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	public HiloServidor(Socket cliente, ControladorServidor controlador) throws IOException {
		this.cliente = cliente;
		this.cliente.setSoTimeout(5000);
		this.controlador = controlador;
		this.flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
		this.flujoSalida = new PrintWriter(cliente.getOutputStream(), true);
		this.nombreUsuario = "";
	}
	
	//=====================================================================================================
	// GETTERS
	//=====================================================================================================
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	//=====================================================================================================
	// MÉTODO RUN - CORRE EL HILO
	//=====================================================================================================
	@Override
	public void run() {
		String mensaje;
		
		inicializarCliente(); // Se inicializa al cliente.
		
		try {
			do {
				mensaje = recibirMensajeTCP().trim(); // Se recibe un mensaje por TCP.
				tratarMensaje(mensaje); // Se trata el mensaje recibido.
				
			// Se repite el proceso mientras el mensaje no sea el código de salida.
			} while (!mensaje.equals(PrincipalServidor.SERVIDOR_CODIGO_SALIDA));
			
			// Una vez se ha recibido el código de salida, se cierran flujos y el cliente.
			flujoEntrada.close();
			flujoSalida.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		controlador.cambiarClientesConectados(PrincipalServidor.getClientes().obtenerNumeroClientes());
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	private void inicializarCliente() {
		// Se recibe el primer mensaje TCP del cliente, que es el nombre de usuario, y se comprueba que no esté repetido.
		nombreUsuario = comprobarNombre(recibirMensajeTCP());
		
		// Se agrega a la lista de clientes.
		PrincipalServidor.anhadirCliente(this);
		
		// Se actualiza la lista de conectados.
		PrincipalServidor.getClientes().actualizarClientesConectados();
		
		// Se envía un mensaje a todos indicando la unión del nuevo cliente.
		PrincipalServidor.enviarMensajeATodos("SERVIDOR -> " + nombreUsuario + " se ha unido.");
	}

	private String comprobarNombre(String nombre) {
		String nombreFinal;
		int contador;
		
		nombreFinal = nombre;
		contador = 1;
		
		while (PrincipalServidor.getClientes().existeCliente(nombreFinal)) {
			nombreFinal = nombre + contador;
		}
		
		return nombreFinal;
	}
	
	public void enviarMensajeTCP(String mensaje) {
		flujoSalida.println(mensaje);
	}

	private String recibirMensajeTCP() {
		String mensaje;
		
		// Se pone a nulo.
		mensaje = null;
		
		// Bucle hasta que llega un mensaje.
		do {
			try {
				// Se lee un mensaje.
				mensaje = flujoEntrada.readLine().trim();
			
			} catch (IOException e) {
				mensaje = null;
			}
			
		// Mientras el mensaje sea nulo, se repite el proceso.
		} while (mensaje == null);
		
		return mensaje;
	}
	
	/**
	 * Tratamiento de mensaje recibido por el servidor.
	 * @param mensaje Mensaje recibido.
	 */
	private void tratarMensaje(String mensaje) {
		
		switch (mensaje) {
		
		// Si el mensaje es un código de listar...
		case PrincipalServidor.SERVIDOR_CODIGO_LISTAR:
			// Se manda un mensaje con los clientes conectados.
			enviarMensajeTCP("SERVIDOR -> Clientes conectados: " + PrincipalServidor.getClientes().obtenerCadenaClientes());
			break;
		
		// Si el mensaje es un código de salida...
		case PrincipalServidor.SERVIDOR_CODIGO_SALIDA:
			// Se manda un mensaje avisando de quién sale.
			PrincipalServidor.enviarMensajeATodos("SERVIDOR -> " + nombreUsuario + " ha salido.");
			// Se quita al cliente.
			PrincipalServidor.quitarCliente(nombreUsuario);
			break;
			
		// Si no es ninguno de los códigos anteriores, se manda el mensaje a todos.
		default:
			PrincipalServidor.enviarMensajeATodos(nombreUsuario + " -> " + mensaje);
			break;
		}
	}
	
	/**
	 * Cierre de conexión.
	 */
	public void cerrarConexion() {
		enviarMensajeTCP(PrincipalServidor.SERVIDOR_CODIGO_SALIDA);
	}
}