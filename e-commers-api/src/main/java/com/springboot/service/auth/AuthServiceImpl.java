package com.springboot.service.auth;

import com.springboot.dto.SignupRequest;
import com.springboot.dto.UserDto;
import com.springboot.entity.Order;
import com.springboot.entity.User;
import com.springboot.enums.OrderStatus;
import com.springboot.enums.UserRole;
import com.springboot.repository.OrderRepo;
import com.springboot.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private OrderRepo orderRepo;

    public UserDto createUser(SignupRequest signupRequest){
        User user =  new User();

        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setName(signupRequest.getName());
        user.setRole(UserRole.CUSTOMER);
        User createuser = userRepo.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createuser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepo.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createuser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return userRepo.findByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepo.findByRole(UserRole.ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setEmail("admin@admin.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));

            userRepo.save(user);
        }
    }

}
