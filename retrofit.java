public class ConfigRetrofit {

    /**
     * this function return logging your request & response headers & body
     * */
    private static HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }

    /**
     * this function return sets the default for new connections
     * the default value is 10 seconds
     * */
    private static OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(provideLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    /**
     * this function for request & response connections to base url
     * */
    private static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance() {
        return provideRetrofit().create(ApiService.class);
    }

    public interface ApiService {

        @FormUrlEncoded
        @POST("example.php")
        Call<Response> loginEample(
                @Field("email") String email,
                @Field("passowrd") String password);
    }
}
