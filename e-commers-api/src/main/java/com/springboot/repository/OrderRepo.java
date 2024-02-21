package com.springboot.repository;

import com.springboot.dto.OrderDto;
import com.springboot.entity.Order;
import com.springboot.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    List<Order> findByUserIdAndOrderStatusIn(Long userId,List<OrderStatus> orderStatuses);

    Optional<Order> findByTrackingId(UUID trackingId);


}
