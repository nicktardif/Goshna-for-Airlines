package com.nicktardif.seniorproject.goshnaforairlines.ApiResponses;

import com.nicktardif.seniorproject.goshnaforairlines.Gate;

import java.util.List;

/**
 * Created by tick on 4/7/15.
 */
public class GateResponse {
    public List<Gate> gates;

    public GateResponse(List<Gate> gates) {
        this.gates = gates;
    }

    @Override
    public String toString() {
        return "GateResponse{" +
                "gates=" + gates +
                '}';
    }
}
