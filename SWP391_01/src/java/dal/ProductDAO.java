/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import model.Product;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

/**
 *
 * @author Hung
 */
public class ProductDAO extends DBContext {

    Connection connection = null;
    
    public ArrayList<Product> getAllProducts(String dbName) {
        
        ArrayList<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select ProductID, ProductName, Price, Unit, [Image] from Product";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("ProductID"));
                p.setProductName(rs.getString("ProductName"));
                p.setPrice(rs.getBigDecimal("Price"));
                p.setUnit(rs.getString("Unit"));
                p.setImage(rs.getString("Image"));
                list.add(p);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
