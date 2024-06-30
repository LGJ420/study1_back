package org.zerock.mallapi.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.*;
import org.zerock.mallapi.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartService cartService;

    @PreAuthorize("#itemDTO.email == authentication.name")
    @PostMapping("/change")
    public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO itemDTO){

        log.info(itemDTO);

        if(itemDTO.getQty() <= 0){

            return cartService.remove(itemDTO.getCino());
        }

        return cartService.addOrModify(itemDTO);
    }
}
