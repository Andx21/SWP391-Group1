/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Hung
 */
public class AccountDAO {
    
    Connection connection = null;
    
    public void addUser(String name, String dbName, String pass) {
        try {
            String sql = "INSERT INTO [User] (Username, [Password], RoleID) VALUES (?, ?, ?)";
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setInt(3, 1);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
