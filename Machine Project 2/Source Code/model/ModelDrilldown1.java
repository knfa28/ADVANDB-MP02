package model;

import db_connection.MySQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelDrilldown1 {
    public static ArrayList<QueryDrilldown1> getAll(){
        ArrayList<QueryDrilldown1> objectList = new ArrayList<QueryDrilldown1>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT L.location_id, HT.house_type_desc, T.tenur_desc,\n" +
                                                            "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                                                            "AVG(H.roof_id) AS 'roof_avg',\n" +
                                                            "AVG (H.wall_id) AS 'wall_avg',\n" +
                                                            "AVG(H.water_id) AS 'water_avg',\n" +
                                                            "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                                                            "FROM (fact_household H)\n" +
                                                            "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                                                            "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                                                            "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                                                            "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc\n" +
                                                            "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC;");
            while(rsList.next()) {
                objectList.add(new QueryDrilldown1(rsList.getInt("location_id"),
                                 rsList.getString("house_type_desc"),
                                 rsList.getString("tenur_desc"),
                                 rsList.getInt("hh_count"),
                                 rsList.getFloat("roof_avg"),
                                 rsList.getFloat("wall_avg"),
                                 rsList.getFloat("water_avg"),
                                 rsList.getFloat("welec_avg")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Drill Down 1 Query - " + objectList.size() + " rows");
        return objectList;
    }
    
    public static ArrayList<QueryDrilldown1> drillDown(boolean isCalam, ArrayList<String> cond){
        ArrayList<QueryDrilldown1> objectList = new ArrayList<QueryDrilldown1>();
        String query;
        String condition = "";
        
        if(!cond.isEmpty()){
            condition = "WHERE ";

            for(int i = 0; i < cond.size() - 1; i ++)
                condition = condition.concat(cond.get(i) + " AND ");

            condition = condition.concat(cond.get(cond.size() - 1));
        }
        
        if(isCalam){
            query = "SELECT L.location_id, HT.house_type_desc, T.tenur_desc,\n" +
                    "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                    "AVG(H.roof_id) AS 'roof_avg',\n" +
                    "AVG (H.wall_id) AS 'wall_avg',\n" +
                    "AVG(H.water_id) AS 'water_avg',\n" +
                    "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN fact_calamity AS C ON H.location_id = C.location_id\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                    "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                    condition + "\n" +
                    "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc\n" +
                    "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC;";
        } else{
            query = "SELECT L.location_id, HT.house_type_desc, T.tenur_desc,\n" +
                    "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                    "AVG(H.roof_id) AS 'roof_avg',\n" +
                    "AVG (H.wall_id) AS 'wall_avg',\n" +
                    "AVG(H.water_id) AS 'water_avg',\n" +
                    "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    "LEFT JOIN dim_house_type AS HT ON H.house_type_id = HT.house_type_id\n" +
                    "LEFT JOIN dim_tenur AS T ON H.tenur_id = T.tenur_id\n" +
                    condition + "\n" +
                    "GROUP BY L.location_id, HT.house_type_desc, T.tenur_desc\n" +
                    "ORDER BY L.location_id ASC, HT.house_type_desc ASC, T.tenur_desc ASC;";
        }
        
        try {
            ResultSet rsList = MySQLConnector.executeQuery(query);
            while(rsList.next()) {
                objectList.add(new QueryDrilldown1(rsList.getInt("location_id"),
                                 rsList.getString("house_type_desc"),
                                 rsList.getString("tenur_desc"),
                                 rsList.getInt("hh_count"),
                                 rsList.getFloat("roof_avg"),
                                 rsList.getFloat("wall_avg"),
                                 rsList.getFloat("water_avg"),
                                 rsList.getFloat("welec_avg")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Drill Down 1 Slice - " + objectList.size() + " rows");
        return objectList;
    }
}
