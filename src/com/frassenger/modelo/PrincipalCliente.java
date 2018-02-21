package com.frassenger.modelo;

import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.frassenger.controlador.ControladorCliente;
import com.frassenger.vista.PanelCliente;

/**
 * PrincipalCliente.
 * 
 * @author Francisco Rodríguez García
 */
public class PrincipalCliente {
	
	//=====================================================================================================
	// CONSTANTES
	//=====================================================================================================
	private static final int PUESRTO_REGISTRADO_MAXIMO = 49151;
	private static final int PUERTO_REGISTRADO_MINIMO = 1024;
	private static final int NOMBRE_USUARIO_MAXIMO = 15;
	
	//=====================================================================================================
	// VARIABLES ESTÁTICAS
	//=====================================================================================================
	private static JFrame ventana;
	private static PanelCliente panel;
	private static ControladorCliente controlador;
	private static String host, nombreUsuario;
	private static int puerto;
	private static Socket cliente;
	private static Cliente claseCliente;

	//=====================================================================================================
	// MÉTODO PRINCIPAL
	//=====================================================================================================
	/**
	 * Principal.
	 * @param args Argumentos.
	 */
	public static void main(String[] args) {
		try {
			// Se crea la Vista.
			crearVistaControlador();

			// Se solicitan los datos requeridos de conexión al servidor.
			solicitarDatosInicio();
			
			// Se inicia el cliente.
			iniciarCliente();
			
			// Mientras el cliente esté conectado...
			while (!cliente.isClosed()) {
				
				// Se manejan los mensajes.
				claseCliente.manejarMensaje();
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana, "Imposible conectar con el servidor.\nComprueba que está conectado.", "FRASSENGER - ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Crea la vista y el controlador.
	 */
	private static void crearVistaControlador() {
		ventana = new JFrame("FRASSENGER - CLIENTE");
		panel = new PanelCliente();
		ventana.setContentPane(panel);
		controlador = new ControladorCliente(panel);
		panel.controlador(controlador);
	}
	
	/**
	 * Solicita los datos de inicio.
	 */
	private static void solicitarDatosInicio() {
		host = solicitarHost(ventana);
		puerto = solicitarPuerto(ventana);
		nombreUsuario = solicitarNombreUsuario(ventana);
	}
	
	/**
	 * Solicita un host.
	 * @param ventana Ventana en la que se muestra.
	 * @return Devuelve el host, en caso de que sea válido.
	 */
	private static String solicitarHost(JFrame ventana) {
		// Variables.
		String host = null;
		InetAddressValidator validadorIp;
		boolean hayError;
		int continuar;

		// Se inicializa el validador de IP.
		validadorIp = new InetAddressValidator();

		try {
			do {
				// Se inicializan los flags.
				hayError = false;
				continuar = JOptionPane.NO_OPTION;

				// Se solicita el host.
				host = JOptionPane.showInputDialog(ventana, "Introduce la IP del servidor (localhost en local):", "FRASSENGER", JOptionPane.QUESTION_MESSAGE);

				// Si el host no es 'localhost' ni una IP válida, hay un error.
				if (!host.equalsIgnoreCase("localhost") && !validadorIp.isValid(host)) {
					hayError = true;

					// Se pregunta si se quiere reintentar.
					continuar = JOptionPane.showConfirmDialog(ventana, "La IP no es correcta.\n¿Quiere introducir otra?", "FRASSENGER", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			// Se repite el proceso si hay fallo y se quiere reintentar.
			} while (hayError && continuar == JOptionPane.OK_OPTION);

			// Si hay fallo y no se quiere reintentar, se termina la aplicación.
			if (hayError && continuar == JOptionPane.NO_OPTION) {
				System.exit(0);
			}

		// Si se pulsa cancelar en el JOptionPane, salta la excepción, y se sale.
		} catch (NullPointerException e) {
			System.exit(0);
		}

		return host;
	}

	/**
	 * Solicita un puerto.
	 * @param ventana Ventana en la que se muestra.
	 * @return Devuelve el puerto, en caso de que sea válido.
	 */
	private static int solicitarPuerto(JFrame ventana) {
		// Variables
		int puerto = 0, continuar;
		boolean hayError;

		try {
			do {
				// Se inicializan los flags.
				hayError = false;
				continuar = JOptionPane.NO_OPTION;
				
				try {
					// Se solicita el puerto.
					puerto = Integer.parseInt(JOptionPane.showInputDialog(ventana, "Introduce el puerto del servidor (" + PrincipalServidor.SERVIDOR_PUERTO + "):", "FRASSENGER", JOptionPane.QUESTION_MESSAGE));
					
					// Si el puerto no entra dentro del rango de puertos registrados disponibles, hay error.
					if (puerto < PUERTO_REGISTRADO_MINIMO || puerto > PUESRTO_REGISTRADO_MAXIMO) {
						hayError = true;
					}
					
				// Si el puerto introducido no es un entero, hay error.
				} catch (NumberFormatException e) {
					hayError = true;
				}
				
				// Si hay error, se pregunta si se quiere volver a intentar.
				if (hayError) {
					continuar = JOptionPane.showConfirmDialog(ventana, "El puerto no es correcto.\n¿Quiere introducir otro?", "FRASSENGER", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			
			// Se repite el proceso si hay error y se quiere volver a intentar.
			} while (hayError && continuar == JOptionPane.OK_OPTION);
			
			// Si hay error y no se quiere volver a intentar, se termina la aplicación.
			if (hayError && continuar == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
			
		// Si se pulsa cancelar en el JOptionPane, salta la excepción, y se sale.
		} catch (NullPointerException e) {
			System.exit(0);
		}

		return puerto;
	}

	/**
	 * Solicita un nombre de usuario.
	 * @param ventana Ventana en la que se muestra.
	 * @return Devuelve el nombre de usuario.
	 */
	private static String solicitarNombreUsuario(JFrame ventana) {
		// Variables.
		String nombreUsuario = null;
		boolean hayError;
		int continuar;
		
		try {
			do {
				// Se inicializan los flags.
				hayError = false;
				continuar = JOptionPane.NO_OPTION;
				
				// Se solicita el nombre de usuario.
				nombreUsuario = JOptionPane.showInputDialog(ventana, "Introduce el nombre de usuario:", "FRASSENGER", JOptionPane.QUESTION_MESSAGE);
				
				// Si el nombre de usuario está vacío o excede el máximo, hay error.
				if (nombreUsuario.isEmpty() || nombreUsuario.length() > NOMBRE_USUARIO_MAXIMO) {
					hayError = true;
					
					// Se pregunta si se quiere reintentar.
					continuar = JOptionPane.showConfirmDialog(ventana, "El nombre de usuario no es válido.\n¿Quiere introducir otro?", "FRASSENGER", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				}
				
			// Si hay error y se quiere volver a intentar, se repite el proceso.
			} while (hayError && continuar == JOptionPane.OK_OPTION);
			
			// Si hay error y no se quiere volver a intentar, se pone un nombre de usuario por defecto.
			if (hayError && continuar == JOptionPane.NO_OPTION) {
				nombreUsuario = "anonymous";
			}
			
		// Si se pulsa cancelar en el JOptionPane, salta la excepción, y se sale.
		} catch (NullPointerException e) {
			System.exit(0);
		}
		
		return nombreUsuario;
	}
	
	/**
	 * Configura la ventana.
	 */
	private static void configurarVentana() {
		ventana.pack();
		ventana.setVisible(true);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Inicia el cliente.
	 * @throws Exception Puede devolver una excepción.
	 */
	private static void iniciarCliente() throws Exception {
		// Se inicia el cliente.
		cliente = new Socket();
		cliente.connect(new InetSocketAddress(host, puerto));
		
		claseCliente = new Cliente(controlador, cliente);
		
		if (claseCliente.recibirMensajeTCP().equals(PrincipalServidor.SERVIDOR_CODIGO_PLAZA_LIBRE)) {
			// Se configura la ventana y se pone visible.
			configurarVentana();
			controlador.setCliente(claseCliente);
			claseCliente.enviarMensajeTCP(nombreUsuario);
			
		} else {
			JOptionPane.showMessageDialog(ventana, "Servidor lleno", "FRASSENGER", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}	
	}
}