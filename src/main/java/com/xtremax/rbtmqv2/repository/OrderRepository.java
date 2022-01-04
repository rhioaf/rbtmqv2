package com.xtremax.rbtmqv2.repository;

import com.xtremax.rbtmqv2.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
