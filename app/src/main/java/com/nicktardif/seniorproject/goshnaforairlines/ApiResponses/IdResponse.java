package com.nicktardif.seniorproject.goshnaforairlines.ApiResponses;

/**
 * Created by tick on 4/7/15.
 */
public class IdResponse {
    public int id;

    public IdResponse(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdResponse{" +
                "id=" + id +
                '}';
    }
}
