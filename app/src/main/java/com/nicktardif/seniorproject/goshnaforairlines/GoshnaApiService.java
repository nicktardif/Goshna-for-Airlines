package com.nicktardif.seniorproject.goshnaforairlines;

import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.GateResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.IdResponse;
import com.nicktardif.seniorproject.goshnaforairlines.ApiResponses.MessageResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by tick on 4/7/15.
 */
public interface GoshnaApiService {
    final String baseURL = "/";

    @GET("/messages")
    void getMessages(Callback<MessageResponse> response);

    @POST("/messages")
    void sendMessage(@Body GateMessage message, Callback<IdResponse> response);

    @GET("/airports")
    void getAllAirports(Callback<AirportResponse> response);

    @POST("/gates/find")
    void getAirportGates(@Body Airport airport, Callback<GateResponse> response);
}
