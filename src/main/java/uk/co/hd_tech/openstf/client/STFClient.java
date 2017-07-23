package uk.co.hd_tech.openstf.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class STFClient {

    private STFService stfService;

    public STFClient(String baseUrl, String apiKey) {

        HttpLoggingInterceptor.Logger logger = System.out::println;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new AuthenticationInterceptor(apiKey))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl + "/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        stfService = retrofit.create(STFService.class);
    }

    public STFService getStfService() {
        return stfService;
    }

    private class AuthenticationInterceptor implements Interceptor {

        private final String apiKey;

        public AuthenticationInterceptor(String apiKey) {
            this.apiKey = apiKey;
        }

        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + apiKey);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
}
