package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.ItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    private final ModelMapper mapper;

    public ItemController(ItemService itemService, ModelMapper mapper) {
        this.itemService = itemService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemDTO>> listItems(){

        List<Item> items = this.itemService.getAll();
        List<ItemDTO> dtoList = items.stream().map(i -> this.mapper.map(i, ItemDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

}
