package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartItemDTO {

    private ItemDTO item;
    private long qty;
}
