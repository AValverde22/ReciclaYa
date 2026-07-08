package pe.reciclaya.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.reciclaya.app.R;
import pe.reciclaya.app.activities.LoginActivity;
import pe.reciclaya.app.activities.RegisterActivity;
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

    private LinearLayout LLLoading;
    private ImageView IVRegresarUnoAtras;

    private EditText ETFullName, ETEmail, ETPassword, ETConfirmPassword;
    private CheckBox checkBox;
    private Button btnCrearCuenta;
    private TextView TVIrALogin;

    private ImageView IVOjo1, IVOjo2;
    private boolean bloquear1 = true, bloquear2 = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_datos, container, false);

        LLLoading = getActivity().findViewById(R.id.LLLoadingRegister);
        IVRegresarUnoAtras = getActivity().findViewById(R.id.IVRegresarRegister);

        ETFullName = view.findViewById(R.id.ETFullNameRD);
        ETEmail = view.findViewById(R.id.ETEmailRD);
        ETPassword = view.findViewById(R.id.ETPasswordRD);
        ETConfirmPassword = view.findViewById(R.id.ETConfirmPasswordRD);

        IVOjo1 = view.findViewById(R.id.IVOjoUnoRegister);
        IVOjo2 = view.findViewById(R.id.IVOjoDosRegister);

        checkBox = view.findViewById(R.id.CBRD);
        btnCrearCuenta = view.findViewById(R.id.BtnCrearCuentaRD);
        TVIrALogin = view.findViewById(R.id.TVIniciarSesionRD);

        IVRegresarUnoAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { crearCuenta(); }
        });

        TVIrALogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });

        IVOjo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloquear1 = !bloquear1;

                if(bloquear1) {
                    ETPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    IVOjo1.setBackgroundResource(R.drawable.desbloquear_password);
                }
                else {
                    ETPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    IVOjo1.setBackgroundResource(R.drawable.bloquear_password);
                }
            }
        });

        IVOjo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloquear2 = !bloquear2;

                if(bloquear2) {
                    ETConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    IVOjo2.setBackgroundResource(R.drawable.desbloquear_password);
                }
                else {
                    ETConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    IVOjo2.setBackgroundResource(R.drawable.bloquear_password);
                }
            }
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

            LLLoading.setVisibility(View.VISIBLE);
            apiService.existsEmail(body).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        LLLoading.setVisibility(View.INVISIBLE);
                        if (response.body() != null) {
                            boolean existe = response.body();

                            if(existe)  {
                                Toast.makeText(
                                        getContext(),
                                        "El correo ya se encuentra registrado.",
                                        Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putString("FullName", fullName);
                                bundle.putString("Email", email);
                                bundle.putString("Password", password);

                                if(getActivity() instanceof RegisterActivity) {
                                    ((RegisterActivity) getActivity())
                                            .cambiarFragment(
                                                    new fragment_register_select(),
                                                    bundle
                                            );
                                }
                            }
                        }
                    } else {
                        LLLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(
                                getContext(),
                                "Error en el servidor, vuelva a intentarlo más tarde.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    LLLoading.setVisibility(View.INVISIBLE);
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

    private boolean validarEmail(String email) { return Patterns.EMAIL_ADDRESS.matcher(email).matches();}
    private boolean validarPassword(String p1, String p2) {return p1.equals(p2);}
}