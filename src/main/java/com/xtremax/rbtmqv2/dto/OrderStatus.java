package com.xtremax.rbtmqv2.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "ORDERSTATUS")
public class OrderStatus {

  @Id
  private String id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ORDER_ID")
  private Order order;
  @Enumerated(value = EnumType.STRING)
  @Column
  private Status orderStatus;
  @Column
  private String messageInfo;

  public OrderStatus() {
  }

  public OrderStatus(String id, Order order, Status orderStatus, String messageInfo) {
    this.id = id;
    this.order = order;
    this.orderStatus = orderStatus;
    this.messageInfo = messageInfo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Status getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(Status orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getMessageInfo() {
    return messageInfo;
  }

  public void setMessageInfo(String messageInfo) {
    this.messageInfo = messageInfo;
  }

  public boolean isServed() {
    return this.orderStatus.equals(Status.SERVED);
  }
}
