package com.springboot.service.admin.adminOrder;

import com.springboot.dto.AnalyticsResponseDto;
import com.springboot.dto.OrderDto;
import com.springboot.entity.Order;
import com.springboot.enums.OrderStatus;
import com.springboot.repository.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{

    private final OrderRepo orderRepo;
    public List<OrderDto> getAllPlacedOrder(){

        List<Order> orderList = orderRepo.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            if(Objects.equals(status, "Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }

            return orderRepo.save(order).getOrderDto();
        }
        return null;
    }

    public AnalyticsResponseDto calculateAnalytics(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthsDate = currentDate.minusMonths(1);

        Long currentMonthOrder = getTotalOrdersForMonths(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthOrders = getTotalOrdersForMonths(previousMonthsDate.getMonthValue(), previousMonthsDate.getYear());

        Long currentMonthEarnings = getTotalEarningsForMonths(currentDate.getMonthValue(), currentDate.getYear());
        Long previousMonthEarning = getTotalEarningsForMonths(previousMonthsDate.getMonthValue(), previousMonthsDate.getYear());

        Long placed = orderRepo.countByOrderStatus(OrderStatus.Placed);
        Long shipped = orderRepo.countByOrderStatus(OrderStatus.Shipped);
        Long delivered = orderRepo.countByOrderStatus(OrderStatus.Delivered);

        return new AnalyticsResponseDto(placed, shipped, delivered, currentMonthOrder, previousMonthOrders, currentMonthEarnings, previousMonthEarning);
    }

    public Long getTotalOrdersForMonths(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calendar to the end of the specific months
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepo.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        return (long) orders.size();
    }

    public Long getTotalEarningsForMonths(int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startOfMonth = calendar.getTime();

        // Move the calendar to the end of the specific months
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        Date endOfMonth = calendar.getTime();

        List<Order> orders = orderRepo.findByDateBetweenAndOrderStatus(startOfMonth, endOfMonth, OrderStatus.Delivered);

        Long sum = 0L;
        for(Order order : orders){
            sum = sum + order.getAmount();
        }
        return sum;
    }
}
