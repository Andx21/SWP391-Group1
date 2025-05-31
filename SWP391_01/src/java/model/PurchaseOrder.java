/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Hung
 */
public class PurchaseOrder {
    private int purchaseId, warehouseId, supplierId, productId, quantity;
    private BigDecimal totalAmount, purchasePrice, sellingPrice;
    private LocalDate date;

    public PurchaseOrder() {
    }

    public PurchaseOrder(int purchaseId, int warehouseId, int supplierId, int productId, int quantity, BigDecimal totalAmount, BigDecimal purchasePrice, BigDecimal sellingPrice, LocalDate date) {
        this.purchaseId = purchaseId;
        this.warehouseId = warehouseId;
        this.supplierId = supplierId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.date = date;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
