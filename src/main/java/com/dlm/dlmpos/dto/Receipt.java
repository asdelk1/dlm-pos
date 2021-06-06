package com.dlm.dlmpos.dto;

import com.dlm.dlmpos.entity.SaleDetail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Receipt {

    List<SaleDetail> items;

    public Receipt(List<SaleDetail> items) {
        this.items = items;
    }
}
