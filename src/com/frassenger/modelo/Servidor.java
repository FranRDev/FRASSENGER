package com.frassenger.modelo;

import java.io.*;
import java.net.*;

/**
 * Servidor.
 * 
 * @author Francisco Rodríguez García
 */
public class Servidor {

	// =====================================================================================================
	// PRINCIPAL
	// =====================================================================================================
	public static void main(String[] args) {
		//==================================================================================================
		// VARIABLES
		//=====================================================================================================
		int numeroPuerto;
		ServerSocket servidor;
		Socket clienteConectado;
		InputStream entrada;
		DataInputStream flujoEntrada;
		OutputStream salida;
		DataOutputStream flujoSalida;
		
		//=====================================================================================================
		// DESARROLLO
		//=====================================================================================================
		try {
			// Se declara el puerto.
			numeroPuerto = 6000;

			// Se crea el servidor.
			servidor = new ServerSocket(numeroPuerto);
			System.out.println("Esperando...");

			// Se guarda el cliente conectado.
			clienteConectado = servidor.accept();

			// Se crea un flujo de entrada con el cliente.
			entrada = clienteConectado.getInputStream();
			flujoEntrada = new DataInputStream(entrada);

			// Se recibe el mensaje del cliente.
			System.out.println("He recibido del cliente: " + flujoEntrada.readUTF());

			// Se crea un flujo de salida al cliente.
			salida = clienteConectado.getOutputStream();
			flujoSalida = new DataOutputStream(salida);

			// Se envía un saludo al cliente.
			flujoSalida.writeUTF("Hola cliente soy el servidor");

			// Se cierran la entrada, salida, y el servidor.
			entrada.close();
			flujoEntrada.close();
			salida.close();
			flujoSalida.close();
			clienteConectado.close();
			servidor.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}