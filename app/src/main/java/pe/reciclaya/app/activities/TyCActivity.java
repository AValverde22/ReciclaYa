package pe.reciclaya.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pe.reciclaya.app.R;

public class TyCActivity extends AppCompatActivity {

    private int tipo;

    private TextView TVTitulo, TVTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyc_activity);

        TVTitulo = findViewById(R.id.TVTituloTyC);
        TVTexto = findViewById(R.id.TVTextoTyC);

        Intent intent = getIntent();
        tipo = intent.getIntExtra("Tipo", 0);

        if(tipo == 0){
            TVTitulo.setText(getResources().getText(R.string.TerminosDeServicio));
            TVTexto.setText(getResources().getText(R.string.MensajeTerminosDeServicio));
        } else {
            TVTitulo.setText(getResources().getText(R.string.PoliticaDePrivaciadad));
            TVTexto.setText(getResources().getText(R.string.MensajePoliticaDePrivacidad));
        }
    }
}