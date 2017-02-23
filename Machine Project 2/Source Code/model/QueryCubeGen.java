package model;

public class QueryCubeGen {
    private int location_id;
    private String calamity_type;
    private float frequency_sum;
    private float aid_avg;
    private float strong_avg;
    private float weak_avg;
    private float others_avg;

    public QueryCubeGen(int location_id, String calamity_type, float frequency_sum, float aid_avg, float strong_avg, float weak_avg, float others_avg) {
        this.location_id = location_id;
        this.calamity_type = calamity_type;
        this.frequency_sum = frequency_sum;
        this.aid_avg = aid_avg;
        this.strong_avg = strong_avg;
        this.weak_avg = weak_avg;
        this.others_avg = others_avg;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getCalamity_type() {
        return calamity_type;
    }

    public void setCalamity_type(String calamity_type) {
        this.calamity_type = calamity_type;
    }

    public float getFrequency_sum() {
        return frequency_sum;
    }

    public void setFrequency_sum(float frequency_sum) {
        this.frequency_sum = frequency_sum;
    }

    public float getAid_avg() {
        return aid_avg;
    }

    public void setAid_avg(float aid_avg) {
        this.aid_avg = aid_avg;
    }

    public float getStrong_avg() {
        return strong_avg;
    }

    public void setStrong_avg(float strong_avg) {
        this.strong_avg = strong_avg;
    }

    public float getWeak_avg() {
        return weak_avg;
    }

    public void setWeak_avg(float weak_avg) {
        this.weak_avg = weak_avg;
    }

    public float getOthers_avg() {
        return others_avg;
    }

    public void setOthers_avg(float others_avg) {
        this.others_avg = others_avg;
    }
}
