package pe.reciclaya.app.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendClient {

    private static final String BASE_URL = "";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <T> T buildService(Class<T> service) {return retrofit.create(service);}
}
