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

    public ArrayList<Product> getAllProductsOfOneWarehouse(String dbName, int warehouseId) {

        ArrayList<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select p.ProductName, p.ProductID, p.Price, p.Unit, p.Image, i.Quantity\n"
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
    
    public ArrayList<Product> getAllProducts(String dbName) {

        ArrayList<Product> list = new ArrayList<>();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select ProductName, ProductID, Price, Unit, Image\n"
                    + "from Product";
            Statement st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("ProductID"));
                    p.setProductName(rs.getString("ProductName"));
                    p.setPrice(rs.getBigDecimal("Price"));
                    p.setUnit(rs.getString("Unit"));
                    p.setImage(rs.getString("Image"));
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

    public void addProduct(String name, String image, String dbName, BigDecimal price, String unit) {
        try {
            String sql = "INSERT INTO Product (ProductName, Price, Unit, [Image]) VALUES (?, ?, ?, ?)";
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setBigDecimal(2, price);
            ps.setString(3, unit);
            ps.setString(4, image);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    
    public Product getProductById(String dbName, int id, int warehouseId){
        Product p = new Product();
        try {
            connection = new DBContext().getConnection(dbName);
            String sql = "select p.ProductName, p.ProductID, p.Price, p.Unit, p.Image, i.Quantity\n"
                    + "from Product p\n"
                    + "join Inventory i on p.ProductID = i.ProductID\n"
                    + "where i.WarehouseID =" + warehouseId +" and p.ProductID ="+ id;
            Statement st = connection.createStatement();
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) {
                    p.setProductId(rs.getInt("ProductID"));
                    p.setProductName(rs.getString("ProductName"));
                    p.setPrice(rs.getBigDecimal("Price"));
                    p.setUnit(rs.getString("Unit"));
                    p.setImage(rs.getString("Image"));
                    p.setQuantity(rs.getInt("Quantity"));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return p;
    }
    
    public void updatePrice(int id, BigDecimal price, String dbName) {
        String sql = "update Product set Price = ? where ProductID = ?";
        try {
            connection = new DBContext().getConnection(dbName);
            Statement st = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(2, id);
            ps.setBigDecimal(1, price);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
