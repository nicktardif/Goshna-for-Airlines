package com.nicktardif.seniorproject.goshnaforairlines;

import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.GateResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.IdResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.Message;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.MessageResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by tick on 4/7/15.
 */
public interface GoshnaApiService {
    final String baseURL = "/";

    @GET("/messages")
    void getMessages(Callback<MessageResponse> response);

    @POST("/messages")
    void sendMessage(@Body Message body, Callback<IdResponse> response);

    @GET("/airports")
    void getAllAirports(Callback<AirportResponse> response);

    @POST("/gates/find")
    void getAirportGates(@Body Airport airport, Callback<GateResponse> response);
}
