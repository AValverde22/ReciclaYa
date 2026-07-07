package pe.reciclaya.app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.reciclaya.app.R;
import pe.reciclaya.app.config.BackendClient;
import pe.reciclaya.app.requests.UserRegister;
import pe.reciclaya.app.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_register_select extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public fragment_register_select() {}

    public static fragment_register_select newInstance(String param1, String param2) {
        fragment_register_select fragment = new fragment_register_select();
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

    private FrameLayout FLPersona;
    private LinearLayout LLPersona;
    private ImageView IVPersona, IVPersonaSeleccion;
    private TextView TVPersona;

    private FrameLayout FLReciclador;
    private LinearLayout LLReciclador;
    private ImageView IVReciclador, IVRecicladorSeleccion;
    private TextView TVReciclador;

    private Button btnConfirmarRS;
    private int opcion = -1;

    private String fullName, email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_select, container, false);

        fullName = requireArguments().getString("FullName");
        email = requireArguments().getString("Email");
        password = requireArguments().getString("Password");

        FLPersona = view.findViewById(R.id.FLPersonaSeleccionRS);
        LLPersona = view.findViewById(R.id.LLPersonaRS);
        IVPersona = view.findViewById(R.id.IVPersonaRS);
        IVPersonaSeleccion = view.findViewById(R.id.IVPersonaSeleccionRS);
        TVPersona = view.findViewById(R.id.TVPersonaRS);

        FLReciclador = view.findViewById(R.id.FLRecicladorSeleccionRS);
        LLReciclador = view.findViewById(R.id.LLRecicladorRS);
        IVReciclador = view.findViewById(R.id.IVRecicladorRS);
        IVRecicladorSeleccion = view.findViewById(R.id.IVRecicladorSeleccionRS);
        TVReciclador = view.findViewById(R.id.TVRecicladorRS);

        FLPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { seleccionPersona(true); }
        });

        FLReciclador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { seleccionReciclador(true); }
        });

        btnConfirmarRS = view.findViewById(R.id.BtnConfirmarRS);
        btnConfirmarRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Confirmar(); }
        });
        return view;
    }

    private void seleccionPersona(boolean seleccionado){
        if(seleccionado) {
            seleccionReciclador(false);

            LLPersona.setBackgroundResource(R.drawable.linearlayout_verde_seleccion);
            IVPersona.setBackgroundResource(R.drawable.persona_seleccionada);
            IVPersonaSeleccion.setBackgroundResource(R.drawable.seleccionado);
            TVPersona.setTextColor(R.color.verde);

            opcion = 0;
        } else {
            LLPersona.setBackgroundResource(R.drawable.linearlayout_transparente);
            IVPersona.setBackgroundResource(R.drawable.persona_no_seleccionada);
            IVPersonaSeleccion.setBackgroundResource(R.drawable.no_seleccionado);
            TVPersona.setTextColor(R.color.black);

            opcion = -1;
        }
    }

    private void seleccionReciclador(boolean seleccionado) {
        if(seleccionado) {
            seleccionPersona(false);

            LLReciclador.setBackgroundResource(R.drawable.linearlayout_verde_seleccion);
            IVReciclador.setBackgroundResource(R.drawable.reciclador_seleccionado);
            IVRecicladorSeleccion.setBackgroundResource(R.drawable.seleccionado);
            TVReciclador.setTextColor(R.color.verde);

            opcion = 1;
        } else {
            LLReciclador.setBackgroundResource(R.drawable.linearlayout_transparente);
            IVReciclador.setBackgroundResource(R.drawable.reciclador_no_seleccionado);
            IVRecicladorSeleccion.setBackgroundResource(R.drawable.no_seleccionado);
            TVReciclador.setTextColor(R.color.black);

            opcion = -1;
        }
    }
    
    private void Confirmar(){
        if(opcion == -1){
            Toast.makeText(
                    getContext(),
                    "¡Debe de seleccionar un rol!",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            String rol = (opcion == 0) ? "usuario" : "reciclador";

            UserService apiService = BackendClient.getUserService();
            UserRegister body = new UserRegister(fullName, email, password, rol);

            apiService.registerUser(body).enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int id = response.body();

                        // TODO: INTENT

                    } else {
                        Toast.makeText(
                                getContext(),
                                "Error en el servidor, vuelva a intentarlo más tarde.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(
                            getContext(),
                            "Error en la red, vuelva a intentarlo.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }



}