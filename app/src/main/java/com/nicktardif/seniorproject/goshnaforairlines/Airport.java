package com.nicktardif.seniorproject.goshnaforairlines;

/**
 * Created by tick on 4/7/15.
 */
public class Airport {
    public int id;
    public String airport_short;
    public String airport_full;

    public Airport(int id, String airport_short, String airport_full) {
        this.id = id;
        this.airport_short = airport_short;
        this.airport_full = airport_full;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", airport_short='" + airport_short + '\'' +
                ", airport_full='" + airport_full + '\'' +
                '}';
    }
}
