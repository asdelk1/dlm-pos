package com.dlm.dlmpos.service;

import com.dlm.dlmpos.dto.ShoppingCartItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.entity.Sale;
import com.dlm.dlmpos.entity.SaleDetail;
import com.dlm.dlmpos.repository.SaleDetailRepository;
import com.dlm.dlmpos.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final ItemService itemService;
    private final SaleDetailRepository repository;
    private final SaleRepository saleRepository;

    public SaleService(ItemService itemService, SaleDetailRepository repository, SaleRepository saleRepository) {
        this.itemService = itemService;
        this.repository = repository;
        this.saleRepository = saleRepository;
    }

    public void saveShoppingCart(ShoppingCartItemDTO[] cart) {

        Sale sale = new Sale();
        sale.setTimestamp(LocalDateTime.now());
        sale = this.saleRepository.save(sale);

        BigDecimal totalBill = BigDecimal.ZERO;

        List<SaleDetail> saleDetailList = new ArrayList<>();
        for (ShoppingCartItemDTO itemInCart : cart) {
            Optional<Item> item = this.itemService.get(itemInCart.getItemId());
            if (item.isPresent()) {
                long qty = itemInCart.getQty();
                BigDecimal unitPrice = item.get().getUnitPrice();
                BigDecimal total = unitPrice.multiply(new BigDecimal(qty));

                SaleDetail saleDetail = new SaleDetail();
                saleDetail.setItem(item.get());
                saleDetail.setUnitPrice(unitPrice);
                saleDetail.setTotal(total);
                saleDetail.setSale(sale);
                saleDetailList.add(saleDetail);

                totalBill = totalBill.add(total);
            }
        }
        sale.setTotal(totalBill);
        this.saleRepository.save(sale);
        this.repository.saveAll(saleDetailList);
    }
}
