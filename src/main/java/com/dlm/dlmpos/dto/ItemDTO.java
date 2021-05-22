package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDTO {

    private Long id;
    private String itemId;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private boolean active;
}
