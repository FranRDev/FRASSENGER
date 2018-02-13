package com.frassenger.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel Cliente.
 * 
 * @author Francisco Rodríguez García
 */
public class PanelCliente extends JPanel {
	private static final long serialVersionUID = 1L;
	
	//=====================================================================================================
	// ELEMENTOS
	//=====================================================================================================
	private JPanel panelSuperior, panelCentral, panelInferior;
	private JScrollPane panelScroll;
	private JTextArea areaTexto;
	private JTextField cuadroTexto;
	private JButton botonEnviar, botonLimpiar, botonVerUsuarios, botonSalir;
	
	//=====================================================================================================
	// CONSTRUCTOR
	//=====================================================================================================
	/**
	 * Constructor del Panel Cliente.
	 */
	public PanelCliente() {
		iniciarComponentes();
		
		//=================================================================================================
		// PANEL SUPERIOR
		//=================================================================================================
		botonVerUsuarios.setBackground(Color.WHITE);
		panelSuperior.add(botonVerUsuarios);
		botonSalir.setBackground(Color.PINK);
		panelSuperior.add(botonSalir);
		
		//=================================================================================================
		// PANEL CENTRAL
		//=================================================================================================
		areaTexto.setEditable(false);
		panelCentral.add(panelScroll, BorderLayout.NORTH);
		panelCentral.add(cuadroTexto, BorderLayout.SOUTH);
		
		//=================================================================================================
		// PANEL INFERIOR
		//=================================================================================================
		botonEnviar.setBackground(Color.CYAN);
		panelInferior.add(botonEnviar);
		botonLimpiar.setBackground(Color.GRAY);
		panelInferior.add(botonLimpiar);
		
		//=================================================================================================
		// PANEL PRINCIPAL
		//=================================================================================================
		setLayout(new BorderLayout());
		add(panelSuperior, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
	}
	
	//=====================================================================================================
	// GETTERS Y SETTERS
	//=====================================================================================================

	//=====================================================================================================
	// MÉTODOS
	//=====================================================================================================
	/**
	 * Inicialización de los componentes.
	 */
	private void iniciarComponentes() {
		panelSuperior = new JPanel();
		panelCentral = new JPanel(new BorderLayout(0, 1));
		panelInferior = new JPanel();
		areaTexto = new JTextArea(20, 80);
		panelScroll = new JScrollPane(areaTexto);
		cuadroTexto = new JTextField();
		botonEnviar = new JButton("ENVIAR");
		botonLimpiar = new JButton("LIMPIAR");
		botonVerUsuarios = new JButton("VER USUARIOS CONECTADOS");
		botonSalir = new JButton("DESCONECTAR");
	}

	public void controlador(ActionListener al) {
		// Botón enviar.
		botonEnviar.addActionListener(al);
		botonEnviar.setActionCommand("enviar");
		
		// Botón limpiar.
		botonLimpiar.addActionListener(al);
		botonLimpiar.setActionCommand("limpiar");
		
		// Botón desconectar.
		botonSalir.addActionListener(al);
		botonSalir.setActionCommand("salir");
		
		// Botón ver usuarios.
		botonVerUsuarios.addActionListener(al);
		botonVerUsuarios.setActionCommand("usuarios");
	}
	
	public void limpiarChat() {
		this.areaTexto.setText("");
	}
}