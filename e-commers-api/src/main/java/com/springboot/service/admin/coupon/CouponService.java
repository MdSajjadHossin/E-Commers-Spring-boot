package com.springboot.service.admin.coupon;

import com.springboot.entity.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
}

