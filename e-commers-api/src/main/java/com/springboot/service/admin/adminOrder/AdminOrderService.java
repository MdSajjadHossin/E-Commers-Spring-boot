package com.springboot.service.admin.adminOrder;

import com.springboot.dto.AnalyticsResponseDto;
import com.springboot.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    public List<OrderDto> getAllPlacedOrder();

    public OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponseDto calculateAnalytics();
}
