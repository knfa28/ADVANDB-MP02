package model;

import db_connection.MySQLConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationModel {
    public static ArrayList<Integer> getMun(){
        ArrayList<Integer> munList = new ArrayList<Integer>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT DISTINCT(mun)\n" +
                                                            "FROM constellation.dim_location\n" +
                                                            "ORDER BY mun;");
            while(rsList.next()) {
                munList.add(rsList.getInt("mun"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return munList;
    }
    
    public static ArrayList<Integer> getZone(){
        ArrayList<Integer> zoneList = new ArrayList<Integer>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT DISTINCT(zone)\n" +
                                                            "FROM constellation.dim_location\n"+
                                                            "ORDER BY zone;");
            while(rsList.next()) {
                zoneList.add(rsList.getInt("zone"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zoneList;
    }
    
    public static ArrayList<Integer> getBrgy(){
        ArrayList<Integer> brgyList = new ArrayList<Integer>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT DISTINCT(brgy)\n" +
                                                            "FROM constellation.dim_location\n"+
                                                            "ORDER BY brgy;");
            while(rsList.next()) {
                brgyList.add(rsList.getInt("brgy"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return brgyList;
    }
    
    public static ArrayList<Integer> getPurok(){
        ArrayList<Integer> purokList = new ArrayList<Integer>();
        try {
            ResultSet rsList = MySQLConnector.executeQuery("SELECT DISTINCT(purok)\n" +
                                                            "FROM constellation.dim_location\n"+
                                                            "ORDER BY purok;");
            while(rsList.next()) {
                purokList.add(rsList.getInt("purok"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purokList;
    }
}
