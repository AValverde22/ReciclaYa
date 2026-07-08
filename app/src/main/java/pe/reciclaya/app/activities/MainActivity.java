package pe.reciclaya.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pe.reciclaya.app.R;

public class MainActivity extends AppCompatActivity {

    private TextView TVProximamente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("INICIAL", MODE_PRIVATE);
        String role = prefs.getString("role", "error");

        TVProximamente = findViewById(R.id.TVProximamenteMain);

        switch (role) {
            case "usuario": TVProximamente.setText("PROXIMAMENTE: PANTALLA DE USUARIO."); break;
            case "reciclador": TVProximamente.setText("PROXIMAMENTE: PANTALLA DE RECICLADOR."); break;
            default: TVProximamente.setText("ERROR"); break;
        }
    }
}