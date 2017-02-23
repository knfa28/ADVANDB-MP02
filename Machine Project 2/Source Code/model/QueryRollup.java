package model;

public class QueryRollup {
    private int location_id;
    private int hh_count;
    private float roof_avg;
    private float wall_avg;
    private float water_avg;
    private float welec_avg;

    public QueryRollup(int location_id, int hh_count, float roof_avg, float wall_avg, float water_avg, float welec_avg) {
        this.location_id = location_id;
        this.hh_count = hh_count;
        this.roof_avg = roof_avg;
        this.wall_avg = wall_avg;
        this.water_avg = water_avg;
        this.welec_avg = welec_avg;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getHh_count() {
        return hh_count;
    }

    public void setHh_count(int hh_count) {
        this.hh_count = hh_count;
    }

    public float getRoof_avg() {
        return roof_avg;
    }

    public void setRoof_avg(float roof_avg) {
        this.roof_avg = roof_avg;
    }

    public float getWall_avg() {
        return wall_avg;
    }

    public void setWall_avg(float wall_avg) {
        this.wall_avg = wall_avg;
    }

    public float getWater_avg() {
        return water_avg;
    }

    public void setWater_avg(float water_avg) {
        this.water_avg = water_avg;
    }

    public float getWelec_avg() {
        return welec_avg;
    }

    public void setWelec_avg(float welec_avg) {
        this.welec_avg = welec_avg;
    }
    
    
}
