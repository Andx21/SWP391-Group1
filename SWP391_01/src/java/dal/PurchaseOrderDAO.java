/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author Hung
 */
public class PurchaseOrderDAO extends DBContext{
    
    Connection connection = null;
    
    public int addPurchaseOrder(String DBName, int WarehouseID, BigDecimal TotalAmount) {
        int id = -1;
        try {
            LocalDate date = LocalDate.now();
            String sql = "INSERT INTO PurchaseOrder (WarehouseID, OrderDate, TotalAmount) VALUES (?, ?, ?)";
            connection = new DBContext().getConnection(DBName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, WarehouseID);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setBigDecimal(3, TotalAmount);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public void addPurchaseOrderDetail(int purchaseId, int productId, int quantity, BigDecimal purchasePrice, BigDecimal sellingPrice, String dbName, int supplierId) {
        try {
            String sql = "INSERT INTO PurchaseOrderDetail (PurchaseID, ProductID, Quantity, PurchasePrice, SellingPrice, SupplierID) VALUES (?, ?, ?, ?, ?, ?)";
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, purchaseId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setBigDecimal(4, purchasePrice);
            ps.setBigDecimal(5, sellingPrice);
            ps.setInt(6, supplierId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
