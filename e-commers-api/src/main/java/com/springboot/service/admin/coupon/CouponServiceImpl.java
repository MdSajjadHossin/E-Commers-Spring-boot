package com.springboot.service.admin.coupon;

import com.springboot.entity.Coupon;
import com.springboot.exceptions.ValidationException;
import com.springboot.repository.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepo couponRepo;

    public Coupon createCoupon(Coupon coupon){
        if(couponRepo.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exist");
        }
        return couponRepo.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepo.findAll();
    }
}
