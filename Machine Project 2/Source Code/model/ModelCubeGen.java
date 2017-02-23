package model;

import db_connection.MySQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelCubeGen {
    public static ArrayList<QueryCubeGen> getAll(){
        ArrayList<QueryCubeGen> objectList = new ArrayList<QueryCubeGen>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT * FROM cube_gen_calamity");
            while(rsList.next()) {
                objectList.add(new QueryCubeGen(rsList.getInt("location_id"),
                                 rsList.getString("calamity_desc"),
                                 rsList.getFloat("frequency_sum"),
                                 rsList.getFloat("aid_avg"),
                                 rsList.getFloat("strong_avg"),
                                 rsList.getFloat("weak_avg"),
                                 rsList.getFloat("others_avg")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Cube Gen Query - " + objectList.size() + " rows");
        return objectList;
    }
}
