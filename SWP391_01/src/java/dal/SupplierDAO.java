/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Supplier;

/**
 *
 * @author Hung
 */
public class SupplierDAO extends DBContext{
    Connection connection = null;

    public ArrayList<Supplier> getAllSupplier(String dbName) {

        ArrayList<Supplier> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select SupplierID, SupplierName, Phone, Email, [Address] from Supplier";
            Statement st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Supplier s = new Supplier();
                    s.setSupplierId(rs.getInt("SupplierID"));
                    s.setSupplierName(rs.getString("SupplierName"));
                    s.setPhone(rs.getString("Phone"));
                    s.setEmail(rs.getString("Email"));
                    s.setAddress(rs.getString("Address"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    
}
