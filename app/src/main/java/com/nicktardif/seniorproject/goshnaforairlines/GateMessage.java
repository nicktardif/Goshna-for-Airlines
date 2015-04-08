package com.nicktardif.seniorproject.goshnaforairlines;

/**
 * Created by tick on 4/7/15.
 */
public class GateMessage {
    public String text;
    public int time;
    public int gate_id;
    public int airport_id;

    public GateMessage(String text, int time, int gate_id, int airport_id) {
        this.text = text;
        this.time = time;
        this.gate_id = gate_id;
        this.airport_id = airport_id;
    }

    @Override
    public String toString() {
        return "GateMessage{" +
                "text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", gate_id=" + gate_id +
                ", airport_id=" + airport_id +
                '}';
    }
}
