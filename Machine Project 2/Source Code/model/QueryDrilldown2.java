package model;

public class QueryDrilldown2 {
    private int location_id;
    private String house_type;
    private String tenur_type;
    private int household_id;
    private String roof_type;
    private String wall_type;
    private String water_type;
    private String welec_status;

    public QueryDrilldown2(int location_id, String house_type, String tenur_type, int household_id, String roof_type, String wall_type, String water_type, String welec_status) {
        this.location_id = location_id;
        this.house_type = house_type;
        this.tenur_type = tenur_type;
        this.household_id = household_id;
        this.roof_type = roof_type;
        this.wall_type = wall_type;
        this.water_type = water_type;
        this.welec_status = welec_status;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getTenur_type() {
        return tenur_type;
    }

    public void setTenur_type(String tenur_type) {
        this.tenur_type = tenur_type;
    }

    public int getHousehold_id() {
        return household_id;
    }

    public void setHousehold_id(int household_id) {
        this.household_id = household_id;
    }

    public String getRoof_type() {
        return roof_type;
    }

    public void setRoof_type(String roof_type) {
        this.roof_type = roof_type;
    }

    public String getWall_type() {
        return wall_type;
    }

    public void setWall_type(String wall_type) {
        this.wall_type = wall_type;
    }

    public String getWater_type() {
        return water_type;
    }

    public void setWater_type(String water_type) {
        this.water_type = water_type;
    }

    public String getWelec_status() {
        return welec_status;
    }

    public void setWelec_status(String welec_status) {
        this.welec_status = welec_status;
    }

    
}
