package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ScriptAssert;

@Getter
@Setter
public class ShoppingCartDTO {

    private String msg;
    private ShoppingCartItemDTO[] items;
}
