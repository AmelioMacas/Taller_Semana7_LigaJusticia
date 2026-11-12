package ligajusticia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal: registrar, buscar, modificar y listar héroes.
 * El diseño gráfico está en FrmPrincipal.form (GUI Designer de IntelliJ).
 */
public class FrmPrincipal extends JFrame {

    // ===== Componentes enlazados al FrmPrincipal.form (los crea el GUI Designer) =====
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblId;
    private JTextField txtId;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblSuperpoder;
    private JTextField txtSuperpoder;
    private JLabel lblMision;
    private JTextField txtMision;
    private JLabel lblNivel;
    private JComboBox<String> cmbNivel;
    private JLabel lblPago;
    private JTextField txtPago;
    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnModificar;
    private JButton btnLimpiar;
    private JButton btnInforme;
    private JLabel lblLista;
    private JTable tblHeroes;

    // ===== Atributos propios =====
    private GestorHeroes gestor = new GestorHeroes();
    private DefaultTableModel modeloTabla;

    public FrmPrincipal() {
        setContentPane(panelPrincipal);   // panel diseñado en el .form
        setTitle("Liga de la Justicia - Rol de Misiones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Columnas de la tabla (las celdas no se pueden editar)
        String[] columnas = {"ID", "Nombre", "Superpoder", "Misión", "Nivel", "Pago mensual"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tblHeroes.setModel(modeloTabla);

        // Eventos de los botones
        btnRegistrar.addActionListener(e -> registrar());
        btnBuscar.addActionListener(e -> buscar());
        btnModificar.addActionListener(e -> modificar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnInforme.addActionListener(e -> abrirInforme());

        // Clic en una fila de la tabla: carga ese héroe en el formulario
        tblHeroes.getSelectionModel().addListSelectionListener(e -> {
            int fila = tblHeroes.getSelectedRow();
            if (fila >= 0 && !e.getValueIsAdjusting()) {
                String id = tblHeroes.getValueAt(fila, 0).toString();
                mostrarEnFormulario(gestor.buscar(id));
            }
        });

        pack();
        setLocationRelativeTo(null); // centrar la ventana
    }

    // ================== ACCIONES DE LOS BOTONES ==================

    private void registrar() {
        Heroe heroe = leerFormulario();
        if (heroe == null) {
            return; // hubo un error de validación
        }
        if (gestor.registrar(heroe)) {
            JOptionPane.showMessageDialog(this, "Héroe registrado correctamente.");
            actualizarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Ya existe un héroe con el ID " + heroe.getId() + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String id = txtId.getText().trim().toUpperCase();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del héroe que desea buscar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Heroe heroe = gestor.buscar(id);
        if (heroe == null) {
            JOptionPane.showMessageDialog(this, "No existe un héroe con el ID " + id + ".",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            mostrarEnFormulario(heroe);
        }
    }

    private void modificar() {
        Heroe heroe = leerFormulario();
        if (heroe == null) {
            return;
        }
        if (gestor.modificar(heroe)) {
            JOptionPane.showMessageDialog(this, "Datos del héroe " + heroe.getId() + " modificados.");
            actualizarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "No existe un héroe con el ID " + heroe.getId()
                    + ". Primero regístrelo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirInforme() {
        if (gestor.cantidad() == 0) {
            JOptionPane.showMessageDialog(this, "No hay héroes registrados.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        FrmInforme informe = new FrmInforme(gestor);
        informe.setVisible(true);
    }

    // ================== MÉTODOS DE APOYO ==================

    /**
     * Lee y valida los datos del formulario.
     * Devuelve el héroe creado, o null si algún dato es incorrecto.
     */
    private Heroe leerFormulario() {
        String id = txtId.getText().trim().toUpperCase();
        String nombre = txtNombre.getText().trim();
        String superpoder = txtSuperpoder.getText().trim();
        String mision = txtMision.getText().trim();
        String textoPago = txtPago.getText().trim();

        // 1. Campos obligatorios
        if (id.isEmpty() || nombre.isEmpty() || superpoder.isEmpty()
                || mision.isEmpty() || textoPago.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // 2. El pago debe ser un número mayor a 0
        double pago;
        try {
            pago = Double.parseDouble(textoPago.replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El pago mensual debe ser un número (ejemplo: 4500.50).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (pago <= 0) {
            JOptionPane.showMessageDialog(this, "El pago mensual debe ser mayor a 0.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        // 3. El nivel viene del combo (1 a 5), por eso siempre es válido
        int nivel = Integer.parseInt(cmbNivel.getSelectedItem().toString());

        return new Heroe(id, nombre, superpoder, mision, nivel, pago);
    }

    // Muestra los datos de un héroe en las cajas de texto
    private void mostrarEnFormulario(Heroe heroe) {
        txtId.setText(heroe.getId());
        txtNombre.setText(heroe.getNombre());
        txtSuperpoder.setText(heroe.getSuperpoder());
        txtMision.setText(heroe.getMision());
        cmbNivel.setSelectedItem(String.valueOf(heroe.getNivelDificultad()));
        txtPago.setText(String.valueOf(heroe.getPagoMensual()));
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtSuperpoder.setText("");
        txtMision.setText("");
        cmbNivel.setSelectedIndex(0);
        txtPago.setText("");
        tblHeroes.clearSelection();
        txtId.requestFocus();
    }

    // Vuelve a llenar la tabla con todos los héroes registrados
    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // borrar filas anteriores
        for (Heroe h : gestor.listar()) {
            Object[] fila = {
                h.getId(),
                h.getNombre(),
                h.getSuperpoder(),
                h.getMision(),
                h.getNivelDificultad(),
                String.format("$%.2f", h.getPagoMensual())
            };
            modeloTabla.addRow(fila);
        }
    }

    // ================== PROGRAMA PRINCIPAL ==================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
}
