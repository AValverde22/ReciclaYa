package pe.reciclaya.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
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

import java.util.regex.Pattern;

import pe.reciclaya.app.R;
import pe.reciclaya.app.activities.LoginActivity;
import pe.reciclaya.app.activities.RegisterActivity;
import pe.reciclaya.app.activities.TyCActivity;
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
    private TextView TVIrALogin, TVIrATyC;

    private ImageView IVOjo1, IVOjo2;
    private boolean bloquear1 = true, bloquear2 = true;


    private boolean flagCampos = true;
    private boolean flagEmail = true;
    private boolean flagPassword = true;
    private boolean flagConfirmPassword = true;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$^&+=])" +
                    "(?=\\S+$)" +
                    ".{8,}" +
                    "$");

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
        TVIrATyC = view.findViewById(R.id.TVTyCRD);

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

        TVIrATyC.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString content = new SpannableString("Acepto los Términos de Servicio y la Política de Privacidad.");
        content.setSpan(new UnderlineSpan(), 11, 31, 0);
        content.setSpan(new UnderlineSpan(), 37, 59, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getContext(), TyCActivity.class);
                intent.putExtra("Tipo", 0);
                getContext().startActivity(intent);

            }
        }, 11, 31, 0);

        content.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(getContext(), TyCActivity.class);
                intent.putExtra("Tipo", 1);
                getContext().startActivity(intent);
            }
        }, 37, 59, 0);

        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde)), 11, 31, 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.verde)), 37, 59, 0);

        TVIrATyC.setText(content);

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

        validarEmail();
        validarPassword();
        validarCoincidenciaPassword();

        return view;
    }

    private void crearCuenta(){
        String fullName = ETFullName.getText().toString();
        String email = ETEmail.getText().toString();
        String password = ETPassword.getText().toString();
        String confirmPassword = ETConfirmPassword.getText().toString();

        ETFullName.clearFocus();
        ETEmail.clearFocus();
        ETPassword.clearFocus();
        ETConfirmPassword.clearFocus();

        validarCampos(fullName, email, password, confirmPassword);

        if(!flagCampos && !flagEmail && !flagPassword && !flagConfirmPassword) {
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

    private void validarCampos(String fullName, String email, String password, String confirmPassword){
        if(fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            flagCampos = true;

            if(fullName.isBlank()) ETFullName.setError("El campo no puede estar vacío");
            if(email.isBlank()) ETEmail.setError("El campo no puede estar vaío");
            if(password.isBlank()) ETPassword.setError("El campo no puede estar vacío");
            if(confirmPassword.isBlank()) ETConfirmPassword.setError("El campo no puede estar vacío");

        } else if(!checkBox.isChecked()) {
            Toast.makeText(
                    getContext(),
                    "Para seguir, debe de aceptar los términos y condiciones.",
                    Toast.LENGTH_SHORT
            ).show();

            flagCampos = true;
        }  else flagCampos = false;
    }

    private void validarEmail(){
        ETEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String email = ETEmail.getText().toString();

                if(!hasFocus && !email.isEmpty()){
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) flagEmail = false;
                    else {ETEmail.setError("Por favor, ingrese un correo válido"); flagEmail = true;}
                } else flagEmail = true;
            }
        });
    }

    private void validarPassword(){
        ETPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = ETPassword.getText().toString();
                String confirmPassword = ETConfirmPassword.getText().toString();

                if(!hasFocus && !password.isEmpty()) {
                    if(PASSWORD_PATTERN.matcher(password).matches()) flagPassword = false;
                    else {
                        ETPassword.setError(
                                "La contraseña debe de contenter al menos:\n" +
                                        "   • Un caracter especial\n" +
                                        "   • Una mayúscula\n" +
                                        "   • 8 caractéres");
                        flagPassword = true;
                    }

                    if(!confirmPassword.isEmpty()) {
                        if(password.equals(confirmPassword)) flagConfirmPassword = false;
                        else {
                            ETConfirmPassword.setError("Las contraseñas deben de coincidir");
                            flagConfirmPassword = true;
                        }
                    }
                } else flagPassword = true;
            }
        });
    }

    private void validarCoincidenciaPassword(){
        ETConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password = ETPassword.getText().toString();
                String confirmPassword = ETConfirmPassword.getText().toString();

                if(!hasFocus && !confirmPassword.isEmpty()){
                    if(confirmPassword.equals(password)) flagConfirmPassword = false;
                    else {ETConfirmPassword.setError("Las contraseñas deben de coincidir"); flagConfirmPassword = true;}
                } else flagConfirmPassword = true;
            }
        });
    }
}