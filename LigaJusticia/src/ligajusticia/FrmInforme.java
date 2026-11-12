package ligajusticia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana del informe: muestra por cada héroe su pago mensual,
 * el aporte al Fondo de Héroes, el impuesto y el pago neto.
 * El diseño gráfico está en FrmInforme.form (GUI Designer de IntelliJ).
 */
public class FrmInforme extends JFrame {

    // ===== Componentes enlazados al FrmInforme.form =====
    private JPanel panelInforme;
    private JLabel lblTitulo;
    private JTable tblInforme;
    private JLabel lblTotales;
    private JButton btnCerrar;

    private GestorHeroes gestor;

    public FrmInforme(GestorHeroes gestor) {
        this.gestor = gestor;
        setContentPane(panelInforme);
        setTitle("Informe de pagos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // cierra solo esta ventana

        String[] columnas = {"Nombre", "Superpoder", "Pago mensual",
                             "Aporte Fondo (10%)", "Impuesto gobierno", "Pago neto"};
        tblInforme.setModel(new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        });

        btnCerrar.addActionListener(e -> dispose());

        cargarInforme();
        pack();
        setLocationRelativeTo(null);
    }

    // Llena la tabla con los cálculos de cada héroe y muestra los totales
    private void cargarInforme() {
        DefaultTableModel modelo = (DefaultTableModel) tblInforme.getModel();
        modelo.setRowCount(0);

        double totalPagos = 0;
        double totalFondo = 0;
        double totalImpuestos = 0;
        double totalNeto = 0;

        for (Heroe h : gestor.listar()) {
            double aporte = h.calcularAporteFondo();
            double impuesto = h.calcularImpuesto();
            double neto = h.calcularPagoNeto();

            Object[] fila = {
                h.getNombre(),
                h.getSuperpoder(),
                String.format("$%.2f", h.getPagoMensual()),
                String.format("$%.2f", aporte),
                String.format("$%.2f", impuesto),
                String.format("$%.2f", neto)
            };
            modelo.addRow(fila);

            totalPagos += h.getPagoMensual();
            totalFondo += aporte;
            totalImpuestos += impuesto;
            totalNeto += neto;
        }

        lblTotales.setText(String.format(
                "Totales:   Pagos $%.2f   |   Fondo de Héroes $%.2f   |   Impuestos $%.2f   |   Neto $%.2f",
                totalPagos, totalFondo, totalImpuestos, totalNeto));
    }
}
