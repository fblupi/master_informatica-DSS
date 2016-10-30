package interfaz;
import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import modelo.Usuario;

public class Cliente extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JPanel panelContenido;
	private JTable table;
	private JScrollPane scroll;
	
	private JToolBar toolBar;
	private JButton btnToolbarAniadirUsuario;

	private JPanel panelAniadir;
	private JLabel lblNombre;
	private JTextField tfNombre;
	private JLabel lblApellido;
	private JTextField tfApellido;
	private JLabel lblEmail;
	private JTextField tfEmail;
	private JPanel botonera;
	private JButton btnAniadir;
	private JButton btnCancelar;

	private static final String EXPR_REG_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	final Pattern pattern = Pattern.compile(EXPR_REG_EMAIL);
	
	public static String urlString = "http://localhost:8080/P2/ListaCorreosServlet";

	private TablaUsuarios modeloTablaUsuarios;
	
	//Para lanzar la aplicacion
	public static void main(String[] args) {
		try { // Queremos que se vea con el look-and-feel del sistema, no con el normal de Swing
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{ System.err.println("Unable to load System look and feel"); }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//Ahora crearemos la ventana
	public Cliente() {
		setTitle("Desarrollo de Sistemas Basados en Componentes y Servicios-2015. Pr\u00E1ctica 2");
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		setLocationRelativeTo(null);
		panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenido);
		panelContenido.setLayout(new BorderLayout(0, 0));
		List<Usuario> listaUsuarios = obtenerListaUsuarios();
		modeloTablaUsuarios = new TablaUsuarios(listaUsuarios);
		table = new JTable(modeloTablaUsuarios) {
			private static final long serialVersionUID = 1L;
			@Override
			public void editingStopped(ChangeEvent e) {
		        TableCellEditor editor = getCellEditor();
		        if (editor != null) {
		            Object value = editor.getCellEditorValue();
		            Usuario usuario = new Usuario(modeloTablaUsuarios.getUsuarioAt(editingRow));
		            if (editingColumn < 2) {
			            switch (editingColumn) {
				            case 0:
				                usuario.setNombre((String) value);
								break;
				            case 1:
				                usuario.setApellido((String) value);
								break;
							default:
								break;
						}
						try {
							Map<String,String> parametros = new HashMap<String, String>();
							parametros.put("action", "actualizarUsuario");
							parametros.put("nombre", usuario.getNombre());
							parametros.put("apellido", usuario.getApellido());
							parametros.put("email", usuario.getEmail());
							ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
							int codigo = respuesta.readInt();
							String mensaje = (String) respuesta.readObject();
							switch (codigo) {
								case 0:
						            setValueAt(value, editingRow, editingColumn);
									break;
								default:
									JOptionPane.showMessageDialog(Cliente.this,
										    mensaje,
										    "Error",
										    JOptionPane.ERROR_MESSAGE);
									break;
							}							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}//if
		            removeEditor();
		        }//if
		    }//editingStopped()
		};//Table
		//Programamos ahora una accion en los botones de la tabla para eliminar a un usuario (fila de la tabla)
		Action borrarFila = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent e) {
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        Usuario usuario = modeloTablaUsuarios.getUsuarioAt(modelRow);
		        
		        int resultadoDialogo = JOptionPane.showConfirmDialog(
		        		Cliente.this, 
		        		"¿Quieres eliminar el usuario <"+usuario.getEmail()+">?",
		        		"Eliminar usuario",
		        		JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		        if (resultadoDialogo == JOptionPane.YES_OPTION) {
					try {
						Map<String,String> parametros = new HashMap<String, String>();
						parametros.put("action", "eliminarUsuario");
						parametros.put("email", usuario.getEmail());
						ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
						int codigo = respuesta.readInt();
						String mensaje = (String) respuesta.readObject();
						switch (codigo) {
							case 0:
						        modeloTablaUsuarios.removeRow(modelRow);
								break;
							default:
								JOptionPane.showMessageDialog(Cliente.this,
									    mensaje,
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
								break;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			    }
		    }
		};//borrar fila
		//Ahora vamos a programar que la cuarta columna de la tabla sea el boton para eliminar una fila (usuario) de la tabla (BD)
		new ButtonColumn(table, borrarFila, 3);
		table.putClientProperty("terminateEditOnFocusLost", true);
		scroll = new JScrollPane(table);
		panelContenido.add(scroll, BorderLayout.CENTER);		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panelContenido.add(toolBar, BorderLayout.NORTH);
		btnToolbarAniadirUsuario = new JButton("A\u00F1adir Usuario");
		btnToolbarAniadirUsuario.setActionCommand("ADDUSER");
		btnToolbarAniadirUsuario.addActionListener(this);
		toolBar.add(btnToolbarAniadirUsuario);
		panelAniadir = new JPanel();
		panelAniadir.setVisible(false);
		panelContenido.add(panelAniadir, BorderLayout.EAST);
		GridBagLayout gbl_panelAniadir = new GridBagLayout();
		gbl_panelAniadir.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelAniadir.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelAniadir.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelAniadir.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelAniadir.setLayout(gbl_panelAniadir);
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panelAniadir.add(lblNombre, gbc_lblNombre);
		tfNombre = new JTextField();
		GridBagConstraints gbc_tfNombre = new GridBagConstraints();
		gbc_tfNombre.insets = new Insets(0, 0, 5, 5);
		gbc_tfNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNombre.gridx = 1;
		gbc_tfNombre.gridy = 0;
		panelAniadir.add(tfNombre, gbc_tfNombre);
		tfNombre.setColumns(10);
		lblApellido = new JLabel("Apellido: ");
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.EAST;
		gbc_lblApellido.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 1;
		panelAniadir.add(lblApellido, gbc_lblApellido);
		tfApellido = new JTextField();
		GridBagConstraints gbc_tfApellido = new GridBagConstraints();
		gbc_tfApellido.insets = new Insets(0, 0, 5, 5);
		gbc_tfApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfApellido.gridx = 1;
		gbc_tfApellido.gridy = 1;
		panelAniadir.add(tfApellido, gbc_tfApellido);
		tfApellido.setColumns(10);
		lblEmail = new JLabel("Email: ");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		panelAniadir.add(lblEmail, gbc_lblEmail);
		tfEmail = new JTextField();
		GridBagConstraints gbc_tfEmail = new GridBagConstraints();
		gbc_tfEmail.insets = new Insets(0, 0, 5, 5);
		gbc_tfEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEmail.gridx = 1;
		gbc_tfEmail.gridy = 2;
		panelAniadir.add(tfEmail, gbc_tfEmail);
		tfEmail.setColumns(10);
		tfEmail.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		       btnAniadir.doClick();
		    }
		});
		botonera = new JPanel();
		GridBagConstraints gbc_botonera = new GridBagConstraints();
		gbc_botonera.gridwidth = 2;
		gbc_botonera.insets = new Insets(0, 0, 0, 5);
		gbc_botonera.fill = GridBagConstraints.BOTH;
		gbc_botonera.gridx = 0;
		gbc_botonera.gridy = 7;
		panelAniadir.add(botonera, gbc_botonera);
		botonera.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnAniadir = new JButton("A\u00F1adir");
		btnAniadir.setActionCommand("EXEC_ANIADIR");
		btnAniadir.addActionListener(this);
		botonera.add(btnAniadir);
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("CANCELAR");
		btnCancelar.addActionListener(this);
		botonera.add(btnCancelar);
	}//Cliente
	
	//Para obtener la lista de usuarios desde el ListaCorreosServlet
	@SuppressWarnings("unchecked")
	private List<Usuario> obtenerListaUsuarios() {
		try {
			Map<String,String> parametros = new HashMap<String, String>();
			parametros.put("action", "listarUsuarios");
			ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
			List<Usuario> listaUsuarios = (List<Usuario>) respuesta.readObject();	
			return listaUsuarios;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//El metodo que se encarga de ejecutar la accion asociada al evento de picar en un boton
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ADDUSER")) {
			panelAniadir.setVisible(true);
			tfNombre.requestFocusInWindow();
		} 
		else if (e.getActionCommand().equals("EXEC_ANIADIR")) {
			Matcher matcher = pattern.matcher(tfEmail.getText());
			String nombre   = tfNombre.getText(),
				   apellido = tfApellido.getText(),
				   email    = tfEmail.getText();
			String fraseError = "";
			boolean error = false;
			if (nombre.equals("")) {
				error = true;
				fraseError += "\n · Debe introducir un nombre.";
			}
			if (apellido.equals("")) {
				error = true;
				fraseError += "\n · Debe introducir un apellido.";
			}
			if (!matcher.matches()) {
				error = true;
				fraseError += "\n · Correo electr\u00F3nico no v\u00E1lido.";
			}		
			if (!error) {
				try {
					Map<String,String> parametros = new HashMap<String, String>();
					parametros.put("action", "aniadirUsuario");
					parametros.put("nombre", nombre);
					parametros.put("apellido", apellido);
					parametros.put("email", email);		
					ObjectInputStream respuesta = new ObjectInputStream(realizarPeticionPost(urlString, parametros));
					int codigo = respuesta.readInt();
					String mensaje = (String) respuesta.readObject();					
					switch (codigo) {
						case 0:
							Usuario usuario = new Usuario();
							usuario.setNombre(tfNombre.getText());
							usuario.setApellido(tfApellido.getText());
							usuario.setEmail(tfEmail.getText());
							modeloTablaUsuarios.add(usuario);
							tfNombre.setText("");
							tfApellido.setText("");
							tfEmail.setText("");
							break;
						default:
							JOptionPane.showMessageDialog(Cliente.this,
								    mensaje,
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
							break;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(Cliente.this,
					    fraseError,
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		} 
		else if (e.getActionCommand().equals("CANCELAR")) {
			tfNombre.setText("");
			tfApellido.setText("");
			tfEmail.setText("");
			panelAniadir.setVisible(false);
		}
	}
	//Realiza una peticion POST a una URL con los parametros que se le pasan
	//@param urlString : Direccion a la que se desea realizar la peticion
	//@param parametros : Parametros de la peticion
	//@return : Respuesta obtenida tras la peticion o <tt>null</tt> en caso de fallar la peticion/respuesta 
	@SuppressWarnings("deprecation")
	public InputStream realizarPeticionPost(String urlString, Map<String,String> parametros) {
		String cadenaParametros = "";
		boolean primerPar = true;
		for (Map.Entry<String, String> entry : parametros.entrySet()) {
			if (!primerPar) {
				cadenaParametros += "&";
			} else {
				primerPar = false;
			}
		    String parDeParametro = String.format("%s=%s", 
					URLEncoder.encode(entry.getKey()), 
					URLEncoder.encode(entry.getValue()));
		    cadenaParametros += parDeParametro;
		}
		try {
			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setUseCaches(false);
			conexion.setRequestMethod("POST");
			conexion.setDoOutput(true);
			OutputStream output = conexion.getOutputStream();
			output.write(cadenaParametros.getBytes());
			output.flush();
			output.close();
			return conexion.getInputStream();
		} catch (MalformedURLException | ProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}