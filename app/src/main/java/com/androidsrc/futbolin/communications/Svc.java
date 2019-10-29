package com.androidsrc.futbolin.communications;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import static com.androidsrc.futbolin.utils.Defaultdata.CEP_URL_BASE;
import static com.androidsrc.futbolin.utils.Defaultdata.GOOGLEMAPS_COORDINATES_API;


/**
 * Created by alberto on 18/06/15.
 */
public class Svc {


    private static SvcApi giftSvc_;



    public static synchronized SvcApi initAuth(Context c) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        DatabaseManager db = new DatabaseManager(c);

        if (db.ExistsUser() && db.findUser().getToken() != null) {

            final String token = db.findUser().getToken();

       //     OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(600  , TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .connectTimeout(600, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    Log.e("token", token);

                    Request request = original.newBuilder()
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", "Bearer " + token)
                                    .build();
                    if(chain != null && chain.request() != null && chain.request().body() != null) {
                        try {
                         //   Log.e("SVC", new JSONObject(bodyToString(chain.request())).toString() + "");
                        }catch(Exception e){e.printStackTrace();
                        }
                    }

                    return chain.proceed(request);
                }
            });

           Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

/*
            Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(String.class,
                    new JsonDeserializer<String>() {

                        @Override
                        public String deserialize(JsonElement json, Type typeOfT,
                                                  JsonDeserializationContext context)
                                throws JsonParseException {
                            return null;
                        }
                    }).create();
 */

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Defaultdata.DEFAULT_IP + ":" + Defaultdata.DEFAULT_PORT + "/")
                    .client(httpClient.build())

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            giftSvc_ = retrofit.create(SvcApi.class);

            return giftSvc_;
        } else {
            return null;
        }

    }
    public static synchronized SvcApi initAuthTimeout(Context c) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        DatabaseManager db = new DatabaseManager(c);

        if (db.ExistsUser() && db.findUser().getToken() != null) {

            final String token = db.findUser().getToken();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(600  , TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .connectTimeout(600, TimeUnit.SECONDS);


            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    Log.e("token", token);

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer " + token)
                            .build();
                    if(chain != null && chain.request() != null && chain.request().body() != null) {
                        try {
                            //   Log.e("SVC", new JSONObject(bodyToString(chain.request())).toString() + "");
                        }catch(Exception e){e.printStackTrace();}
                    }

                    return chain.proceed(request);
                }
            });

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

/*
            Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(String.class,
                    new JsonDeserializer<String>() {

                        @Override
                        public String deserialize(JsonElement json, Type typeOfT,
                                                  JsonDeserializationContext context)
                                throws JsonParseException {
                            return null;
                        }
                    }).create();
 */

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Defaultdata.DEFAULT_IP + ":" + Defaultdata.DEFAULT_PORT + "/")
                    .client(httpClient.build())

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            giftSvc_ = retrofit.create(SvcApi.class);

            return giftSvc_;
        } else {
            return null;
        }

    }
    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
    public static synchronized SvcApi initRegisterLogin(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Defaultdata.DEFAULT_IP + ":" + Defaultdata.DEFAULT_PORT + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SvcApi mInterfaceService = retrofit.create(SvcApi.class);
        return mInterfaceService;

    }
    public static synchronized SvcApi initCepApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CEP_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SvcApi mInterfaceService = retrofit.create(SvcApi.class);
        return mInterfaceService;

    }
    public static synchronized SvcApi initGoogleMapsApi(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLEMAPS_COORDINATES_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final SvcApi mInterfaceService = retrofit.create(SvcApi.class);
        return mInterfaceService;

    }

}
