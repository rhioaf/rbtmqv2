package com.xtremax.rbtmqv2.service.impl;

import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.error.NotFoundException;
import com.xtremax.rbtmqv2.repository.OrderRepository;
import com.xtremax.rbtmqv2.service.base.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository repository;
  @Override
  public Order findByOrderId(String id) throws NotFoundException {
    Optional<Order> order = repository.findById(id);
    if(order.isPresent()) {
      return order.get();
    } else {
      throw new NotFoundException("Not Found Bud!");
    }
  }

  @Override
  public void saveOrder(Order order) {
    repository.save(order);
  }
}
