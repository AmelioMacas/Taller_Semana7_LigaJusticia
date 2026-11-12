package ligajusticia;

/**
 * Clase que representa a un héroe de la Liga de la Justicia
 * con la misión que tiene asignada.
 */
public class Heroe {

    // Atributos
    private String id;
    private String nombre;
    private String superpoder;
    private String mision;
    private int nivelDificultad;   // de 1 a 5
    private double pagoMensual;    // en dólares

    // Constructor
    public Heroe(String id, String nombre, String superpoder, String mision,
                 int nivelDificultad, double pagoMensual) {
        this.id = id;
        this.nombre = nombre;
        this.superpoder = superpoder;
        this.mision = mision;
        this.nivelDificultad = nivelDificultad;
        this.pagoMensual = pagoMensual;
    }

    // Aporte al Fondo de Héroes: 10% del pago mensual
    public double calcularAporteFondo() {
        return pagoMensual * 0.10;
    }

    // Impuesto del gobierno: se calcula sobre el pago ANUAL
    // y se devuelve el valor MENSUAL (impuesto anual / 12)
    public double calcularImpuesto() {
        double pagoAnual = pagoMensual * 12;
        double impuestoAnual;

        if (pagoAnual <= 60000) {
            impuestoAnual = 0;
        } else if (pagoAnual <= 120000) {
            impuestoAnual = (pagoAnual - 60000) * 0.12;
        } else if (pagoAnual <= 240000) {
            impuestoAnual = (pagoAnual - 120000) * 0.25;
        } else {
            impuestoAnual = (pagoAnual - 240000) * 0.35;
        }
        return impuestoAnual / 12;
    }

    // Pago neto = pago mensual - aporte al fondo - impuesto
    public double calcularPagoNeto() {
        return pagoMensual - calcularAporteFondo() - calcularImpuesto();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSuperpoder() {
        return superpoder;
    }

    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(int nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public double getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(double pagoMensual) {
        this.pagoMensual = pagoMensual;
    }
}
