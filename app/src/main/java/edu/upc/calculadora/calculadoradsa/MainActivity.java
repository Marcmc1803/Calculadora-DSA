package edu.upc.calculadora.calculadoradsa;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int op;         // Operación seleccionada
    private double n1;      // Primer número
    private double n2;      // Segundo número
    private double result;  // Resultado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // --- BOTONES DE OPERACIÓN ---
    public void Onsumar(android.view.View v) { leerPrimerNumero(); op = 1; }
    public void Onrestar(android.view.View v) { leerPrimerNumero(); op = 2; }
    public void Onmultiplicar(android.view.View v) { leerPrimerNumero(); op = 3; }
    public void Ondividir(android.view.View v) { leerPrimerNumero(); op = 4; }
    public void Onsin(android.view.View v) { leerPrimerNumero(); op = 6; }
    public void Oncos(android.view.View v) { leerPrimerNumero(); op = 7; }

    // --- BOTÓN LIMPIAR ---
    public void Onlimpiar(android.view.View v) {
        EditText editText = findViewById(R.id.editTextNumberDecimal);
        editText.setText("");
        n1 = 0; n2 = 0; result = 0; op = 0;
    }

    // --- BOTÓN IGUAL ---
    public void Onigual(android.view.View v) {
        EditText editText = findViewById(R.id.editTextNumberDecimal);
        String valorTexto = editText.getText().toString();

        // Leer segundo número solo si la operación necesita dos operandos
        if (!valorTexto.isEmpty() && op != 6 && op != 7) {
            try {
                n2 = Double.parseDouble(valorTexto);
            } catch (NumberFormatException e) {
                editText.setError("Introduce un número válido");
                return;
            }
        }

        switch(op) {
            case 1: result = n1 + n2; break;
            case 2: result = n1 - n2; break;
            case 3: result = n1 * n2; break;
            case 4:
                if (n2 != 0) result = n1 / n2;
                else { editText.setError("No se puede dividir por 0"); return; }
                break;
            case 6: result = Math.sin(Math.toRadians(n1)); break;
            case 7: result = Math.cos(Math.toRadians(n1)); break;
            default: return;
        }

        editText.setText(String.valueOf(result));
        n1 = result; // Para operaciones encadenadas
    }

    // --- MÉTODO AUXILIAR PARA LEER EL PRIMER NÚMERO ---
    private void leerPrimerNumero() {
        EditText editText = findViewById(R.id.editTextNumberDecimal);
        String valorTexto = editText.getText().toString().trim();

        // Intentar leer el número, si está vacío asumimos 0
        try {
            n1 = valorTexto.isEmpty() ? 0 : Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            n1 = 0;
            editText.setError("Introduce un número válido");
        }

        // Limpiar siempre el EditText para el segundo número
        editText.setText("");
    }
}

