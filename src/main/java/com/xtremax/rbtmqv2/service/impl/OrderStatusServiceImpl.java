package com.xtremax.rbtmqv2.service.impl;

import com.xtremax.rbtmqv2.dto.Order;
import com.xtremax.rbtmqv2.dto.OrderStatus;
import com.xtremax.rbtmqv2.error.NotFoundException;
import com.xtremax.rbtmqv2.repository.OrderStatusRepository;
import com.xtremax.rbtmqv2.service.base.OrderStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

  private final OrderStatusRepository repository;
  @Override
  public OrderStatus findById(String id) throws NotFoundException {
    Optional<OrderStatus> orderStatus = repository.findById(id);
    if(orderStatus.isPresent()) {
      return orderStatus.get();
    } else {
      throw new NotFoundException("Not found buddy!");
    }
  }

  @Override
  public OrderStatus findByOrder(Order order) {
    return repository.findByOrder(order);
  }

  @Override
  public void saveOrderStatus(OrderStatus orderStatus) {
    repository.save(orderStatus);
  }
}
