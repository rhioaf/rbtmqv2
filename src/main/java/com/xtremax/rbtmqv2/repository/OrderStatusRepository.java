package com.xtremax.rbtmqv2.repository;

import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.dto.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, String> {
  OrderStatus findByOrder(Order order);
}
