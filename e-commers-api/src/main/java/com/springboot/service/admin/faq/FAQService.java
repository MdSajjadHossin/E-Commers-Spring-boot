package com.springboot.service.admin.faq;

import com.springboot.dto.FAQDto;

public interface FAQService {
    public FAQDto postFAQ(Long productId, FAQDto faqDto);
}
