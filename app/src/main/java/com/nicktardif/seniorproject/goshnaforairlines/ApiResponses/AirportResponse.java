package com.nicktardif.seniorproject.goshnaforairlines.ApiResponses;

import com.nicktardif.seniorproject.goshnaforairlines.Airport;

import java.util.List;

/**
 * Created by tick on 4/7/15.
 */
public class AirportResponse {
    public List<Airport> airports;

    public AirportResponse(List<Airport> airports) {
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "AirportResponse{" +
                "airports=" + airports +
                '}';
    }
}
