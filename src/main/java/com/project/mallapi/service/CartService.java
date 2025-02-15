package com.project.mallapi.service;

import com.project.mallapi.dto.CartItemDTO;
import com.project.mallapi.dto.CartItemListDTO;
import jakarta.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartService {

    List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);

    List<CartItemListDTO> getCartItems(String email);

    List<CartItemListDTO> remove(Long cino);

}
