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
	public Clientes() {
		this.listaClientes = new HashMap<String, HiloServidor>();
	}
	
	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	public void anhadirCliente(String nombre, HiloServidor hiloServidor) {
		listaClientes.put(nombre, hiloServidor);
	}
	
	public void eliminarCliente(String nombre) {
		listaClientes.remove(nombre);
	}
	
	public int obtenerNumeroClientes() {
		return listaClientes.size();
	}
	
	public boolean existeCliente(String nombre) {
		return listaClientes.containsKey(nombre);
	}
	
	public String obtenerCadenaClientes() {
		StringBuilder cadena;
		Set<String> nombres;
		
		cadena = new StringBuilder();
		nombres = listaClientes.keySet();
		
		for (String nombre : nombres) {
			cadena.append(nombre + ", ");
		}
		
		cadena.setLength(cadena.length() - 2);
		cadena.append(".");
		
		return cadena.toString();
	}
	
	public void mostrarMensajeATodos(String mensaje) {
		// Expresión lambda que recorre el HashMap y envía el mensaje a cada hilo.
		listaClientes.forEach((k,v) -> v.enviarMensajeTCP(mensaje));
	}
	
	public void desconectarATodos() {
		// Expresión lambda que recorre el HashMap y cierra cada hilo.
		listaClientes.forEach((k,v) -> v.cerrarConexion());
	}
	
	public void actualizarClientesConectados() {
		mostrarMensajeATodos(PrincipalServidor.SERVIDOR_CODIGO_ACTUALIZAR);
		mostrarMensajeATodos(obtenerNumeroClientes() + "/" + PrincipalServidor.SERVIDOR_MAXIMO_CONEXIONES);
	}
}