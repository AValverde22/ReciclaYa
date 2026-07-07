package pe.reciclaya.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import pe.reciclaya.app.R;
import pe.reciclaya.app.activities.LoginActivity;
import pe.reciclaya.app.config.BackendClient;
import pe.reciclaya.app.requests.UserExistsEmail;
import pe.reciclaya.app.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_register_datos extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public fragment_register_datos() {}

    public static fragment_register_datos newInstance(String param1, String param2) {
        fragment_register_datos fragment = new fragment_register_datos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private EditText ETFullName, ETEmail, ETPassword, ETConfirmPassword;
    private CheckBox checkBox;
    private Button btnCrearCuenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_datos, container, false);

        ETFullName = view.findViewById(R.id.ETFullNameRD);
        ETEmail = view.findViewById(R.id.ETEmailRD);
        ETPassword = view.findViewById(R.id.ETPasswordRD);
        ETConfirmPassword = view.findViewById(R.id.ETConfirmPasswordRD);

        checkBox = view.findViewById(R.id.CBRD);
        btnCrearCuenta = view.findViewById(R.id.BtnCrearCuentaRD);

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { crearCuenta(); }
        });

        return view;
    }

    private void crearCuenta(){
        String fullName = ETFullName.getText().toString();
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();
        String confirmPassword = ETConfirmPassword.getText().toString();

        if(validarCampos(fullName, email, password, confirmPassword)) {
            UserService apiService = BackendClient.getUserService();
            UserExistsEmail body = new UserExistsEmail(email);

            apiService.existsEmail(body).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            boolean existe = response.body();

                            if(existe)  {
                                Toast.makeText(
                                        getContext(),
                                        "El correo ya se encuentra registrado.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                //
                            }
                        }
                    } else {
                        Toast.makeText(
                                getContext(),
                                "Error en el servidor, vuelva a intentarlo más tarde.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(
                            getContext(),
                            "Error en la red, vuelva a intentarlo.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }

    private boolean validarCampos(String fullName, String email, String password, String confirmPassword) {
        if(fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(
                    getContext(),
                    "¡Los campos no pueden estar vacíos!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        } else if(!checkBox.isChecked()) {
            Toast.makeText(
                    getContext(),
                    "¡Debe de aceptar los términos y condiciones!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        } else if(!validarEmail(email)){
            Toast.makeText(
                    getContext(),
                    "¡Escriba un correo válido!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        } else if (!validarPassword(password, confirmPassword)) {
            Toast.makeText(
                    getContext(),
                    "¡Las contraseñas deben de coincidir!",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        return true;
    }

    private boolean validarEmail(String email) { return Patterns.EMAIL_ADDRESS.matcher(email).matches(); }
    private boolean validarPassword(String p1, String p2) {return p1.equals(p2);}
}