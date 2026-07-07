package pe.reciclaya.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import pe.reciclaya.app.R;
import pe.reciclaya.app.config.BackendClient;
import pe.reciclaya.app.requests.UserValidate;
import pe.reciclaya.app.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private EditText Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.ETEmailLogin);
        Password = findViewById(R.id.ETPasswordLogin);
    }

    public void IniciarSesion(View view){
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if(email.isBlank() || password.isBlank()) {
            Toast.makeText(
                    this,
                    "Los campos no pueden estar vacíos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        UserService apiService = BackendClient.buildService(UserService.class);
        UserValidate body = new UserValidate(email, password);

        apiService.validate(body).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        int id = response.body();

                        if(id == 0){
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Usuario y/o contraseña inválidos.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Error en el servidor, vuelva a intentarlo más tarde.",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(
                        LoginActivity.this,
                        "Error en la red, vuelva a intentarlo.",
                        Toast.LENGTH_SHORT
                        ).show();
            }
        });

        try {
            Response<Integer> response = apiService.validate(body).execute();

            if(response.isSuccessful() && response.body() != null) {
                int id =  response.body();
                if(id == 0) {
                    Toast.makeText(
                            this,
                            "Usuario y/o contraseña inválidos.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(
                    this,
                    "Error de red. No se pudo validar el usuario.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}