package com.xtremax.rbtmqv2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
  private String orderId;
  private double amountPayment;
}
