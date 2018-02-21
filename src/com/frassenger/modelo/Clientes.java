package com.frassenger.modelo;

import java.util.HashMap;
import java.util.Set;

/**
 * Lista de clientes.
 * 
 * @author Francisco Rodríguez García
 */
public class Clientes {
	
	//=====================================================================================================
	// ATRIBUTOS
	//=====================================================================================================
	private HashMap<String, HiloServidor> listaClientes;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor de Clientes.
	 */
	public Clientes() {
		this.listaClientes = new HashMap<String, HiloServidor>();
	}
	
	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Añadir cliente a la lista.
	 * @param nombre Nombre del cliente.
	 * @param hiloServidor Hilo del servidor en el que se encuentra.
	 */
	public void anhadirCliente(String nombre, HiloServidor hiloServidor) {
		listaClientes.put(nombre, hiloServidor);
	}
	
	/**
	 * Eliminar cliente.
	 * @param nombre Nombre del cliente.
	 */
	public void eliminarCliente(String nombre) {
		listaClientes.remove(nombre);
	}
	
	/**
	 * Obtener número de clientes.
	 * @return Devuelve el número de clientes.
	 */
	public int obtenerNumeroClientes() {
		return listaClientes.size();
	}
	
	/**
	 * Comprueba si existe un cliente.
	 * @param nombre Nombre del cliente.
	 * @return Devuelve un boolean indicando si existe.
	 */
	public boolean existeCliente(String nombre) {
		return listaClientes.containsKey(nombre);
	}
	
	/**
	 * Devuelve una cadena con los clientes.
	 * @return Devuelve la cadena.
	 */
	public String obtenerCadenaClientes() {
		StringBuilder cadena;
		Set<String> nombres;
		
		cadena = new StringBuilder();
		nombres = listaClientes.keySet();
		
		// Añade cada nombre de la lista.
		for (String nombre : nombres) {
			cadena.append(nombre + ", ");
		}
		
		cadena.setLength(cadena.length() - 2);
		cadena.append(".");
		
		return cadena.toString();
	}
	
	/**
	 * Muestra un mensaje a todos los clientes.
	 * @param mensaje Mensaje a mostrar.
	 */
	public void mostrarMensajeATodos(String mensaje) {
		// Expresión lambda que recorre el HashMap y envía el mensaje a cada hilo.
		listaClientes.forEach((k,v) -> v.enviarMensajeTCP(mensaje));
	}
	
	/**
	 * Desconecta a todos los clientes.
	 */
	public void desconectarATodos() {
		// Expresión lambda que recorre el HashMap y cierra cada hilo.
		listaClientes.forEach((k,v) -> v.cerrarConexion());
	}
	
	/**
	 * Actualiza la información de los clientes conectados.
	 */
	public void actualizarClientesConectados() {
		mostrarMensajeATodos(PrincipalServidor.SERVIDOR_CODIGO_ACTUALIZAR);
		mostrarMensajeATodos(obtenerNumeroClientes() + "/" + PrincipalServidor.SERVIDOR_MAXIMO_CONEXIONES);
	}
}