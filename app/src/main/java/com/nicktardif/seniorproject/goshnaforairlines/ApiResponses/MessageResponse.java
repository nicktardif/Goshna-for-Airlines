package com.nicktardif.seniorproject.goshnaforairlines.ApiResponses;

/**
 * Created by tick on 4/7/15.
 */
public class MessageResponse {
    public int id;
    public String text;
    public String time;

    public MessageResponse(int id, String text, String time) {
        this.id = id;
        this.text = text;
        this.time = time;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
