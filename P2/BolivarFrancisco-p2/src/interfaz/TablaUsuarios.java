package interfaz;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Usuario;

public class TablaUsuarios extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	//Lista con los usuarios de la base de datos
	private List<Usuario> lista;
	//identificadores de las columnas de la tabla
    private String[] columnNames = { "Nombre", "Apellido", "Email", "Acci\u00F3n" };
    public TablaUsuarios(List<Usuario> usuarios){
        this.lista = usuarios;
    }
    @Override
    public int getRowCount() {
        return lista.size();
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if (columnIndex == 2) {
			return false;
		}
    	return true;
    }
    //Para añadir un usuario a la tabla
    public void add(Usuario usuario) {
        lista.add(usuario);
        fireTableDataChanged();
    }
    //Para eliminar un usuario e la tabla
    public void remove(Usuario usuario) {
        if (lista.contains(usuario)) {
            lista.remove(usuario);
            fireTableDataChanged();
        }
    }
    //Para eliminar una fila (usuario) de la tabla (BD)
    public void removeRow(int rowIndex) {
    	lista.remove(rowIndex);
        fireTableDataChanged();
    }
    //Para devolver el usuario correspondiente a una fila de la tabla
    public Usuario getUsuarioAt(int rowIndex) {
    	return lista.get(rowIndex);
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = lista.get(rowIndex);
        switch (columnIndex){
            case 0:
                return usuario.getNombre();
            case 1:
                return usuario.getApellido();
            case 2:
                return usuario.getEmail();
            case 3: 
            	return "Borrar";
        }
        return "";
    }
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Usuario usuario = lista.get(rowIndex);
        switch (columnIndex){
	        case 0:
	            usuario.setNombre((String) value);
				break;
	        case 1:
	            usuario.setApellido((String) value);
				break;
	        case 2:
	            usuario.setEmail((String) value);
				break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}