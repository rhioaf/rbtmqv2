package com.xtremax.rbtmqv2.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ORDERS")
public class Order {

  @Id
  private String orderId;
  @Column
  private String name;
  @Column
  private int quantity;
  @Column
  private double price;

  public Order() {
  }

  public Order(String orderId, String name, int quantity, double price) {
    this.orderId = orderId;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
