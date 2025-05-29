/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Warehouse;

/**
 *
 * @author Hung
 */
public class WarehouseDAO extends DBContext{
    Connection connection = null;

    public ArrayList<Warehouse> getAllWarehouse(String dbName) {

        ArrayList<Warehouse> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select WarehouseID, WarehouseName, Location from Warehouse";
            Statement st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Warehouse w = new Warehouse();
                    w.setWarehouseId(rs.getInt("WarehouseID"));
                    w.setWarehouseName(rs.getString("WarehouseName"));
                    w.setLocation(rs.getString("Location"));
                    list.add(w);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
