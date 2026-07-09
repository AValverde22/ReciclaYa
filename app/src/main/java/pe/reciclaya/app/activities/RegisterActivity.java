package pe.reciclaya.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import pe.reciclaya.app.R;
import pe.reciclaya.app.fragments.fragment_register_datos;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FLRegister, new fragment_register_datos())
                .commit();
    }

    public void cambiarFragment(Fragment fragment, @Nullable Bundle bundle){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.FLRegister, fragment.getClass(), bundle)
                .addToBackStack(null)
                .commit();
    }

    public void eliminarFragment() {getSupportFragmentManager().popBackStack();}
}