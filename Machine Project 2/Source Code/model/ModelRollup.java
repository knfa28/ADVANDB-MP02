package model;

import db_connection.MySQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelRollup {
        public static ArrayList<QueryRollup> getAll(){
        ArrayList<QueryRollup> objectList = new ArrayList<QueryRollup>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT L.location_id,\n" +
                                                            "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                                                            "AVG(H.roof_id) AS 'roof_avg',\n" +
                                                            "AVG (H.wall_id) AS 'wall_avg',\n" +
                                                            "AVG(H.water_id) AS 'water_avg',\n" +
                                                            "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                                                            "FROM (fact_household H)\n" +
                                                            "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                                                            "GROUP BY L.location_id");
            while(rsList.next()) {
                objectList.add(new QueryRollup(rsList.getInt("location_id"),
                                 rsList.getInt("hh_count"),
                                 rsList.getFloat("roof_avg"),
                                 rsList.getFloat("wall_avg"),
                                 rsList.getFloat("water_avg"),
                                 rsList.getFloat("welec_avg")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Rollup Query - " + objectList.size() + " rows");
        return objectList;
    }
    
    public static ArrayList<QueryRollup> drillDown(boolean isCalam, ArrayList<String> cond){
        ArrayList<QueryRollup> objectList = new ArrayList<QueryRollup>();
        String query;
        String condition = "";
        
        if(!cond.isEmpty()){
            condition = "WHERE ";

            for(int i = 0; i < cond.size() - 1; i ++)
                condition = condition.concat(cond.get(i) + " AND ");

            condition = condition.concat(cond.get(cond.size() - 1));
        }
        
        if(isCalam){
            query = "SELECT L.location_id,\n" +
                    "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                    "AVG(H.roof_id) AS 'roof_avg',\n" +
                    "AVG (H.wall_id) AS 'wall_avg',\n" +
                    "AVG(H.water_id) AS 'water_avg',\n" +
                    "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN fact_calamity AS C ON H.location_id = C.location_id\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    condition + "\n" +
                    "GROUP BY L.location_id";
        } else{
            query = "SELECT L.location_id,\n" +
                    "COUNT(DISTINCT(H.household_id)) AS 'hh_count',\n" +
                    "AVG(H.roof_id) AS 'roof_avg',\n" +
                    "AVG (H.wall_id) AS 'wall_avg',\n" +
                    "AVG(H.water_id) AS 'water_avg',\n" +
                    "2 - AVG(H.welec_status) AS 'welec_avg'\n" +
                    "FROM (fact_household H)\n" +
                    "INNER JOIN dim_location AS L ON H.location_id = L.location_id\n" +
                    condition + "\n" +
                    "GROUP BY L.location_id";
        }
        
        try {
            ResultSet rsList = MySQLConnector.executeQuery(query);
            while(rsList.next()) {
                objectList.add(new QueryRollup(rsList.getInt("location_id"),
                                 rsList.getInt("hh_count"),
                                 rsList.getFloat("roof_avg"),
                                 rsList.getFloat("wall_avg"),
                                 rsList.getFloat("water_avg"),
                                 rsList.getFloat("welec_avg")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Rollup Slice - " + objectList.size() + " rows");
        return objectList;
    }
}
