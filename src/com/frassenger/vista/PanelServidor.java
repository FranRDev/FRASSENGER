package com.frassenger.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.frassenger.modelo.PrincipalServidor;

public class PanelServidor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//=====================================================================================================
	// ELEMENTOS
	//=====================================================================================================
	private JPanel panelSuperior, panelInferior;
	private JScrollPane panelCentral;
	private JTextArea areaTexto;
	private JLabel etiquetaClientesConectados, etiquetaEstadoServidor, etiquetaPuerto;
	private JButton botonSalir;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor del Panel PrincipalServidor.
	 */
	public PanelServidor() {
		iniciarComponentes();
		
		//=================================================================================================
		// PANEL SUPERIOR
		//=================================================================================================
		etiquetaPuerto.setHorizontalAlignment(JLabel.CENTER);
		panelSuperior.add(etiquetaPuerto);
		etiquetaEstadoServidor.setHorizontalAlignment(JLabel.CENTER);
		panelSuperior.add(etiquetaEstadoServidor);
		etiquetaClientesConectados.setHorizontalAlignment(JLabel.CENTER);
		panelSuperior.add(etiquetaClientesConectados);
		
		//=================================================================================================
		// PANEL INFERIOR
		//=================================================================================================
		botonSalir.setBackground(Color.PINK);
		panelInferior.add(botonSalir);
		
		//=================================================================================================
		// PANEL PRINCIPAL
		//=================================================================================================
		setLayout(new BorderLayout());
		add(panelSuperior, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
	}

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Inicialización de componentes.
	 */
	private void iniciarComponentes() {
		panelSuperior = new JPanel(new GridLayout(1, 3));
		panelInferior = new JPanel();
		areaTexto = new JTextArea(20, 80);
		panelCentral = new JScrollPane(areaTexto);
		etiquetaClientesConectados = new JLabel("Clientes conectados: 0/5");
		etiquetaEstadoServidor = new JLabel("SERVIDOR CONECTADO");
		etiquetaPuerto = new JLabel("Puerto: " + PrincipalServidor.SERVIDOR_PUERTO);
		botonSalir = new JButton("DESCONECTAR SERVIDOR");
	}
	
	/**
	 * Asigna un controlador a los elementos necesarios.
	 * @param al Controlador de ActionListener.
	 */
	public void controlador(ActionListener al) {
		// Botón salir (desconectar servidor).
		botonSalir.addActionListener(al);
		botonSalir.setActionCommand("desconectar");
	}
	
	/**
	 * Añade un mensaje al chat.
	 *@param mensaje Mensaje que se añade.
	 */
	public void anhadirMensaje(String mensaje) {
		areaTexto.append(mensaje.trim() + "\n");
	}
	
	/**
	 * Deshabilita el botón de salir.
	 */
	public void deshabilitarBotonSalir() {
		botonSalir.setEnabled(false);
	}
	
	public void deshabilitarAreaDeTexto() {
		areaTexto.setEnabled(false);
	}
	
	/**
	 * Cambia el texto de la etiqueta de clientes conectados.
	 * @param texto Texto que pone.
	 */
	public void cambiarEtiquetaClientesConectados(String texto) {
		etiquetaClientesConectados.setText(texto);
	}
	
	public void cambiarEtiquetaPuerto(String mensaje) {
		etiquetaPuerto.setText(mensaje);
	}
	
	/**
	 * Cambia el texto de la etiqueta del estado del servidor.
	 * @param texto Texto que pone.
	 */
	public void cambiarEtiquetaEstadoServidor(String texto) {
		etiquetaEstadoServidor.setText(texto);
	}
}