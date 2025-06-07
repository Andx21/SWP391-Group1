/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 *
 * @author Hung
 */
public class DBsuperAdminDAO extends DBContext {

    Connection connection = null;

    public int addAccount(String DBName, String user, String email, String pass, String role, String db) {
        int id = -1;
        try {
            String sql = "INSERT INTO Users (Username, Email, Password, Role, DatabaseName) VALUES (?, ?, ?, ?, ?)";
            connection = new DBContext().getConnection(DBName);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, role);
            ps.setString(5, db);
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

    public void addStore(String name, String dbName, int id) {
        try {
            String sql = "INSERT INTO Stores (UserID, StoreName) VALUES (?, ?)";
            connection = new DBContext().getConnection(dbName);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(2, name);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewShopDatabase(String newDbName) throws Exception {
        if (newDbName == null || newDbName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên database không được để trống.");
        }

        String sourceDb = "SalesManagement";
        String user = "sa", pass = "sa";
        String adminUrl = "jdbc:sqlserver://localhost:1433;encrypt=false";
        String sourceUrl = "jdbc:sqlserver://localhost:1433;databaseName=" + sourceDb + ";encrypt=false";

        // 1. Tạo database mới
        try (Connection adminConn = DriverManager.getConnection(adminUrl, user, pass); Statement stmt = adminConn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE [" + newDbName + "]");
        }

        try (Connection sourceConn = DriverManager.getConnection(sourceUrl, user, pass)) {

            // 2. Lấy danh sách bảng và sao chép
            try (Statement tableStmt = sourceConn.createStatement(); ResultSet tables = tableStmt.executeQuery(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE'")) {

                while (tables.next()) {
                    String table = tables.getString("TABLE_NAME");

                    String sql = String.format(
                            "SELECT * INTO [%s].dbo.[%s] FROM [%s].dbo.[%s] WHERE 1=0",
                            newDbName, table, sourceDb, table
                    );
                    try (Connection cloneConn = DriverManager.getConnection(adminUrl, user, pass); Statement cloneStmt = cloneConn.createStatement()) {
                        cloneStmt.executeUpdate(sql);
                    } catch (Exception e) {
                        System.err.println("❌ Lỗi sao chép bảng [" + table + "]: " + e.getMessage());
                    }
                }
            }

            String insertRoleSql = String.format(
                    "INSERT INTO [%s].dbo.[Role] (RoleName) VALUES ('Manager'), ('Cashier'), ('Saler')",
                    newDbName
            );
            try (Connection insertConn = DriverManager.getConnection(adminUrl, user, pass); Statement insertStmt = insertConn.createStatement()) {
                insertStmt.executeUpdate(insertRoleSql);
                System.out.println("✔ Đã thêm dữ liệu mẫu vào bảng [Role]");
            } catch (Exception e) {
                System.err.println("❌ Lỗi khi thêm dữ liệu mẫu vào bảng [Role]: " + e.getMessage());
            }

            // 3. Thêm Primary Keys
            try (Statement pkStmt = sourceConn.createStatement(); ResultSet pkRs = pkStmt.executeQuery(
                    "SELECT tc.TABLE_NAME, kcu.COLUMN_NAME, tc.CONSTRAINT_NAME "
                    + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
                    + "JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu ON tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME "
                    + "WHERE tc.CONSTRAINT_TYPE = 'PRIMARY KEY'")) {

                while (pkRs.next()) {
                    String table = pkRs.getString("TABLE_NAME");
                    String column = pkRs.getString("COLUMN_NAME");
                    String constraint = pkRs.getString("CONSTRAINT_NAME");

                    String sql = String.format(
                            "ALTER TABLE [%s].dbo.[%s] ADD CONSTRAINT [%s] PRIMARY KEY ([%s])",
                            newDbName, table, constraint, column
                    );
                    try (Connection conn = DriverManager.getConnection(adminUrl, user, pass); Statement alterStmt = conn.createStatement()) {
                        alterStmt.executeUpdate(sql);
                    } catch (Exception e) {
                        System.err.println("Lỗi thêm PK [" + constraint + "] vào bảng [" + table + "]: " + e.getMessage());
                    }
                }
            }

            // 4. Thêm Foreign Keys
            try (Statement fkStmt = sourceConn.createStatement(); ResultSet fkRs = fkStmt.executeQuery(
                    "SELECT fk.name AS FK_NAME, tp.name AS TABLE_NAME, cp.name AS COLUMN_NAME, "
                    + "tr.name AS REF_TABLE, cr.name AS REF_COLUMN "
                    + "FROM sys.foreign_keys fk "
                    + "JOIN sys.foreign_key_columns fkc ON fkc.constraint_object_id = fk.object_id "
                    + "JOIN sys.tables tp ON fk.parent_object_id = tp.object_id "
                    + "JOIN sys.columns cp ON fkc.parent_column_id = cp.column_id AND cp.object_id = tp.object_id "
                    + "JOIN sys.tables tr ON fk.referenced_object_id = tr.object_id "
                    + "JOIN sys.columns cr ON fkc.referenced_column_id = cr.column_id AND cr.object_id = tr.object_id")) {

                while (fkRs.next()) {
                    String fkName = fkRs.getString("FK_NAME");
                    String table = fkRs.getString("TABLE_NAME");
                    String column = fkRs.getString("COLUMN_NAME");
                    String refTable = fkRs.getString("REF_TABLE");
                    String refColumn = fkRs.getString("REF_COLUMN");

                    String sql = String.format(
                            "ALTER TABLE [%s].dbo.[%s] ADD CONSTRAINT [%s] FOREIGN KEY ([%s]) REFERENCES [%s].dbo.[%s] ([%s])",
                            newDbName, table, fkName, column, newDbName, refTable, refColumn
                    );
                    try (Connection conn = DriverManager.getConnection(adminUrl, user, pass); Statement alterStmt = conn.createStatement()) {
                        alterStmt.executeUpdate(sql);
                    } catch (Exception e) {
                        System.err.println("Lỗi thêm FK [" + fkName + "] vào bảng [" + table + "]: " + e.getMessage());
                    }
                }
            }

            System.out.println("Đã clone schema từ [" + sourceDb + "] sang [" + newDbName + "]");
        }
    }

}
