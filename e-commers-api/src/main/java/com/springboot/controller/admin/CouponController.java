package com.springboot.controller.admin;

import com.springboot.entity.Coupon;
import com.springboot.exceptions.ValidationException;
import com.springboot.service.admin.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping()
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try{
            Coupon createCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.ok(createCoupon);
        }catch (ValidationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Coupon>>  getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}
