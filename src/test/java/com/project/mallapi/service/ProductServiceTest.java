package com.project.mallapi.service;

import static org.junit.jupiter.api.Assertions.*;

import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.ProductDTO;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductDTO> responseDTO = productService.getList(pageRequestDTO);

        log.info(responseDTO.getDtoList());
    }

}