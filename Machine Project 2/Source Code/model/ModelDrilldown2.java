package model;

import db_connection.MySQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelDrilldown2 {
    public static ArrayList<QueryDrilldown2> getAll(){
        ArrayList<QueryDrilldown2> objectList = new ArrayList<QueryDrilldown2>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id, R.roof_desc, W.wall_desc, WA.water_desc, H.welec_status\n" +
                                                            "FROM (fact_household H)\n" +
                                                            "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                                                            "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                                                            "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                                                            "LEFT JOIN dim_roof AS R ON H.roof_id = R.roof_id\n" +
                                                            "LEFT JOIN dim_wall AS W ON H.wall_id = W.wall_id\n" +
                                                            "LEFT JOIN dim_water AS WA ON H.water_id = WA.water_id\n" +
                                                            "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id\n" +
                                                            "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC, H.household_id ASC;");
            while(rsList.next()) {
                objectList.add(new QueryDrilldown2(rsList.getInt("location_id"),
                                 rsList.getString("house_type_desc"),
                                 rsList.getString("tenur_desc"),
                                 rsList.getInt("household_id"),
                                 rsList.getString("roof_desc"),
                                 rsList.getString("wall_desc"),
                                 rsList.getString("water_desc"),
                                 rsList.getString("welec_status")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Drill Down 2 Query - " + objectList.size() + " rows");
        return objectList;
    }
    
    public static ArrayList<QueryDrilldown2> drillDown(boolean isCalam, ArrayList<String> cond){
        ArrayList<QueryDrilldown2> objectList = new ArrayList<QueryDrilldown2>();
        String query;
        String condition = "";
        
        if(!cond.isEmpty()){
            condition = "WHERE ";

            for(int i = 0; i < cond.size() - 1; i ++)
                condition = condition.concat(cond.get(i) + " AND ");

            condition = condition.concat(cond.get(cond.size() - 1));
        }
        
        if(isCalam){
            query = "SELECT L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id, R.roof_desc, W.wall_desc, WA.water_desc, H.welec_status\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN fact_calamity AS C ON H.location_id = C.location_id\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                    "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                    "LEFT JOIN dim_roof AS R ON H.roof_id = R.roof_id\n" +
                    "LEFT JOIN dim_wall AS W ON H.wall_id = W.wall_id\n" +
                    "LEFT JOIN dim_water AS WA ON H.water_id = WA.water_id\n" +
                    condition +  "\n" +
                    "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id\n" +
                    "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC, H.household_id ASC;";
        } else{
            query = "SELECT L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id, R.roof_desc, W.wall_desc, WA.water_desc, H.welec_status\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                    "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                    "LEFT JOIN dim_roof AS R ON H.roof_id = R.roof_id\n" +
                    "LEFT JOIN dim_wall AS W ON H.wall_id = W.wall_id\n" +
                    "LEFT JOIN dim_water AS WA ON H.water_id = WA.water_id\n" +
                    condition +  "\n" +
                    "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc, H.household_id\n" +
                    "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC, H.household_id ASC;";
        }
        
        try {
            ResultSet rsList = MySQLConnector.executeQuery(query);
            while(rsList.next()) {
                objectList.add(new QueryDrilldown2(rsList.getInt("location_id"),
                                 rsList.getString("house_type_desc"),
                                 rsList.getString("tenur_desc"),
                                 rsList.getInt("household_id"),
                                 rsList.getString("roof_desc"),
                                 rsList.getString("wall_desc"),
                                 rsList.getString("water_desc"),
                                 rsList.getString("welec_status")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Drill Down 2 Slice - " + objectList.size() + " rows");
        return objectList;
    }
}
