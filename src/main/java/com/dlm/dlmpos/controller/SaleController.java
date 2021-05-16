package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.ShoppingCartItemDTO;
import com.dlm.dlmpos.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("")
    public ResponseEntity addSale(@RequestBody ShoppingCartItemDTO[] itemsInCart){

        this.saleService.saveShoppingCart(itemsInCart);
        return ResponseEntity.ok("DONE");
    }
}
