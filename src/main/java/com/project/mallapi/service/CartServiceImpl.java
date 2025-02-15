package com.project.mallapi.service;

import com.project.mallapi.domain.Cart;
import com.project.mallapi.domain.CartItem;
import com.project.mallapi.domain.Member;
import com.project.mallapi.domain.Product;
import com.project.mallapi.dto.CartItemDTO;
import com.project.mallapi.dto.CartItemListDTO;
import com.project.mallapi.repository.CartItemRepository;
import com.project.mallapi.repository.CartRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO) {

        String email = cartItemDTO.getEmail();
        Long pno = cartItemDTO.getPno();
        int qty = cartItemDTO.getQty();
        Long cino = cartItemDTO.getCino();

        // 기존에 담겨 있는 상품에 대한 처리
        if(cino != null){
            Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);

            CartItem cartItem = cartItemResult.orElseThrow();

            cartItem.changQty(qty);

            log.info("cino != null"+cartItem.getQty());

            cartItemRepository.save(cartItem);

            return getCartItems(email);
        }

        Cart cart = getCart(email);

        CartItem cartItem = null;

        cartItem = cartItemRepository.getItemOfPno(email, pno);

        if(cartItem == null) {
            Product product = Product.builder().pno(pno).build();
            cartItem = CartItem.builder().product(product).cart(cart).qty(qty).build();
        } else {
            cartItem.changQty(qty);
        }

        log.info("cino == null"+cartItem.getQty());
        cartItemRepository.save(cartItem);

        return getCartItems(email);
    }

    private Cart getCart(String email) {

        // 해당 email의 장바구니(Cart)가 있는지 확인 있으면 반환

        // 없으면 Cart 객체 생성하고 추가 반환

        Cart cart = null;

        Optional<Cart> result =cartRepository.getCartOfMember(email);

        if (result.isEmpty()) {

            log.info("Cart of the member is not exist!!");

            Member member = Member.builder().email(email).build();
            Cart tempCart = Cart.builder().owner(member).build();

            cart = cartRepository.save(tempCart);
        } else { // 장바구니는 있으나 해당 상품의 장바구니 아이템은 없는 경우
            cart = result.get();
        }

        return cart;
    }

    @Override
    public List<CartItemListDTO> getCartItems(String email) {
        return cartItemRepository.getItemsOfCartDTOByEmail(email);
    }

    @Override
    public List<CartItemListDTO> remove(Long cino) {

        Long cno = cartItemRepository.getCartFromItem(cino);

        cartItemRepository.deleteById(cino);

        return cartItemRepository.getItemsOfCartDTOByCart(cno);
    }
}
