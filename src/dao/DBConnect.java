package dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duncan Adjei
 */
public class DBConnect extends ConnectAbstract {

    private static Connection con;

    private DBConnect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //the ip will will call a class from the static object and check what the ip is before sending to this 
        //con = DriverManager.getConnection("jdbc:mysql://10.98.190.19:3306/backend?autoRconnect=true&useSSL=false", "root", "");
//        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/storeConnect?autoRconnect=true&useSSL=false", "root", "");
           con = DriverManager.getConnection("jdbc:mysql://192.168.64.2:3306/storeConnect?autoRconnect=true&useSSL=false", "root", "root");
//        con = DriverManager.getConnection("jdbc:mysql://10.98.190.28:3306/storeConnect?autoRconnect=true&useSSL=false", "root", "");
//        con = DriverManager.getConnection("jdbc:mysql://10.98.190.160:3306/storeConnect?autoRconnect=true&useSSL=false", "root", "mrcdata2019");

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            con = null;
        }
//        try {
//            System.setProperty("derby.system.home", new File("./StoreData").getCanonicalPath());
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//            con = DriverManager.getConnection("jdbc:derby:TACT;create=true;user=TACT;password=..traces.exe?;");
//        } catch (ClassNotFoundException | SQLException | IOException ex) {
//            con = null;
//        }
    //  createTables();
    }

    private static class InstanceHolder {

        private static final DBConnect INSTANCE = new DBConnect();
    }

    public static DBConnect getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Connection connect() {
        return con;
    }

    private List<String> isTablesExist() {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData data = con.getMetaData();
            ResultSet rs = data.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tables;
    }

    private enum databaseTables {

        STUDENTSREGISTRATION, USERS
    }

    private void createTables() {

        List<String> tables = isTablesExist();
 
        if (!tables.contains(databaseTables.STUDENTSREGISTRATION.toString())) {
            try {
                con.createStatement().execute(STUDENTSREGISTRATIONMODEL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (!tables.contains(databaseTables.USERS.toString())) {
            try {
                con.createStatement().execute(USERMODEL);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    
    //  WILL WORK ON THIS LATER IN UPDATES
    private final String STUDENTSREGISTRATIONMODEL
            = "CREATE TABLE "
            + "`customermodel` (`ID` int(11) NOT NULL, "
            + "`Name` varchar(255) DEFAULT NULL,  "
            + "`Address` varchar(255) DEFAULT NULL,  "
            + "`City` varchar(255) DEFAULT NULL,  "
            + "`Phone` int(100) DEFAULT NULL,  "
            + "`Email` varchar(255) DEFAULT NULL,  "
            + "`mobilenumber` int(100) DEFAULT NULL,  "
            + "`Notes` varchar(255) DEFAULT NULL,  "
            + "PRIMARY KEY (`ID`))";

    private final String USERMODEL
            = "CREATE TABLE `suppliermodel` "
            + "(`ID` int(11) NOT NULL,`Name` varchar(255) DEFAULT NULL, "
            + "`Address` varchar(255) DEFAULT NULL, "
            + "`City` varchar(255) DEFAULT NULL, "
            + "`Phone` int(100) DEFAULT NULL, "
            + "`Email` varchar(255) DEFAULT NULL, "
            + "`mobilenumber` int(100) DEFAULT NULL, "
            + "`Notes` varchar(255) DEFAULT NULL, "
            + "PRIMARY KEY (`ID`))";

}
