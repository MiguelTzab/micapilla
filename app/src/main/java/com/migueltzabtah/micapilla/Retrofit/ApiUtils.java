package com.migueltzabtah.micapilla.Retrofit;

/**
 * Created by migueltzabtah on 14/08/18.
 */

public class ApiUtils {
    private ApiUtils() {}
    //Url Remoto
    public static final String BASE_URL = "http://micapilla.lighthousecode.com/";
    //Url Local
    //public static final String BASE_URL = "http://10.0.2.2/";

    public static APIService getAPIService() {

        return ClsRetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
