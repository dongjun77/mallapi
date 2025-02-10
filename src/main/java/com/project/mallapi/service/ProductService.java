package com.project.mallapi.service;

import com.project.mallapi.dto.PageRequestDTO;
import com.project.mallapi.dto.PageResponseDTO;
import com.project.mallapi.dto.ProductDTO;
import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> getList (PageRequestDTO pageRequestDTO);

}
