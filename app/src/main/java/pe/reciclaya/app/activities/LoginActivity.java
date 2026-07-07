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

    private EditText ETEmail, ETPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETEmail = findViewById(R.id.ETEmailLogin);
        ETPassword = findViewById(R.id.ETPasswordLogin);
    }

    public void IniciarSesion(View view){
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();

        if(validarCampos(email, password)) {
            UserService apiService = BackendClient.getUserService();
            UserValidate body = new UserValidate(email, password);

            apiService.validateUser(body).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            int id = response.body();

                            if (id == 0) {
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
        }
    }

    private boolean validarCampos(String email, String password) {
        if(email.isBlank() || password.isBlank()) {
            Toast.makeText(
                    this,
                    "Los campos no pueden estar vacíos",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        return true;
    }
}