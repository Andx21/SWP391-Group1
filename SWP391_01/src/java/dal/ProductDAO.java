/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.util.ArrayList;
import model.Product;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Hung
 */
public class ProductDAO extends DBContext {

    Connection connection = null;

    public ArrayList<Product> getAllProducts(String dbName, int warehouseId) {

        ArrayList<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select p.ProductName, p.ProductID, p.ProductName, p.Price, p.Unit, p.Image, i.Quantity\n"
                    + "from Product p\n"
                    + "join Inventory i on p.ProductID = i.ProductID\n"
                    + "where i.WarehouseID =" + warehouseId;
            Statement st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("ProductID"));
                    p.setProductName(rs.getString("ProductName"));
                    p.setPrice(rs.getBigDecimal("Price"));
                    p.setUnit(rs.getString("Unit"));
                    p.setImage(rs.getString("Image"));
                    p.setQuantity(rs.getInt("Quantity"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void updateProduct(int id, String name, BigDecimal price, String image, String dbName, String unit) {
        String sql = "update Product set ProductName = ?, Price = ?, [Image] = ?, Unit = ? where ProductID = ?";
        try {
            connection = new DBContext().getConnection(dbName);
            Statement st = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(5, id);
            ps.setString(1, name);
            ps.setBigDecimal(2, price);
            ps.setString(3, image);
            ps.setString(4, unit);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int quantity, String dbName, int warehouseId, int id) {
        String sql = "update Inventory set Quantity = ? where WarehouseID = ? and ProductID = ?";
        try {
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, warehouseId);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int addProduct(String name, String image, String dbName, BigDecimal price, String unit) {
        int productId = -1;
        try {
            String sql = "INSERT INTO Product (ProductName, Price, Unit, [Image]) VALUES (?, ?, ?, ?)";
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setBigDecimal(2, price);
            ps.setString(3, unit);
            ps.setString(4, image);
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    productId = rs.getInt(1);
                }
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productId;
    }

    public void addProductToInventory(String dbName, int id, int warehouseId, int quantity) {
        String sql = "INSERT INTO Inventory (ProductID, WarehouseID, Quantity) VALUES (?, ?, ?)";
        try {
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, warehouseId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
