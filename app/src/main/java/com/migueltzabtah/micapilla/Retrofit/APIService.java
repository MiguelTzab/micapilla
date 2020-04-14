package com.migueltzabtah.micapilla.Retrofit;

import com.migueltzabtah.micapilla.models.Ciudad;
import com.migueltzabtah.micapilla.models.Estado;
import com.migueltzabtah.micapilla.models.Evento;
import com.migueltzabtah.micapilla.models.Iglesia;
import com.migueltzabtah.micapilla.models.Itinerario;
import com.migueltzabtah.micapilla.models.Usuario;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by migueltzabtah on 14/08/18.
 */

public interface APIService {

    //Url Remoto
    String url = "api/";
    //Url local
    //String url = "api.micapilla/";

    @GET(url+"usuarios/{user}/eventos")
    Call<Usuario> getEventos(@Path("user") String User_ID);

    @GET(url+"iglesias")
    Call<Iglesia> getIglesias(@Query("ciudad_id") String ciudad_id,
                              @Query("estado") String estado);

    @GET(url+"estados")
    Call<Estado> getEstados();

    @GET(url+"ciudades")
    Call<Ciudad> getCiudades(@Query("Estado") String estado_id);

    @GET(url+"usuarios")
    Call<Usuario> login(@Query("email") String email,
                              @Query("password") String password);

    @GET(url+"iglesias/{iglesia}/itinerarios")
    Call<Itinerario> getItinerarios(@Path("iglesia") String Iglesia_ID);

    @GET(url+"iglesias/{iglesia}/eventos")
    Call<Evento> getEventosIglesia(@Path("iglesia") String Iglesia_ID,
                                   @Query("usuario_id") String usuario_id);

    @POST(url+"usuarios")
    @FormUrlEncoded
    Call<Usuario> createUser(@Field("nombre") String nombre,
                             @Field("email") String email,
                             @Field("password") String password);


    @POST(url+"eventos/{id}")
    @FormUrlEncoded
    Call<Evento> asistirEvento(@Path("id") String id_evento,
                               @Field("usuario_id") String usuario_id);

    @DELETE(url+"usuarios/{user}/eventos/{event}")
    Call<Evento> eliminarAsistencia(@Path("user") String id_user,
                                    @Path("event") String id_evento);


    @PUT(url+"usuarios/{user}")
    @FormUrlEncoded
    Call<Usuario> updatePerfil(@Path("user") String id_user,
                               @Field("nombre") String nombre,
                               @Field("email") String email,
                               @Field("password") String password);

}
