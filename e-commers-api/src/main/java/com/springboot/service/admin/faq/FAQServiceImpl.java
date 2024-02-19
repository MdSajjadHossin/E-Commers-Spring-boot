package com.springboot.service.admin.faq;

import com.springboot.dto.FAQDto;
import com.springboot.entity.FAQ;
import com.springboot.entity.Product;
import com.springboot.repository.FAQRepo;
import com.springboot.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FAQRepo faqRepo;

    private final ProductRepo productRepo;

    public FAQDto postFAQ(Long productId, FAQDto faqDto){
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(optionalProduct.isPresent()){
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepo.save(faq).getFAQDto();

        }
        return null;
    }
}
