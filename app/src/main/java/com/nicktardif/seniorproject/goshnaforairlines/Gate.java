package com.nicktardif.seniorproject.goshnaforairlines;

/**
 * Created by tick on 4/7/15.
 */
public class Gate {
    public int id;
    public String name;
    public int airport_id;

    public Gate(int id, String name, int airport_id) {
        this.id = id;
        this.name = name;
        this.airport_id = airport_id;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", airport_id=" + airport_id +
                '}';
    }
}
