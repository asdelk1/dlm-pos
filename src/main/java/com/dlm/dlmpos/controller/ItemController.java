package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.ItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("")
    public ResponseEntity<List<ItemDTO>> listItems() {

        List<Item> items = this.itemService.getAll();
        List<ItemDTO> dtoList = items.stream().map(i -> this.mapper.map(i, ItemDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable long id) {

        Optional<Item> item = this.itemService.get(id);
        ItemDTO dto = null;
        if(item.isPresent()){
            dto = this.mapper.map(item, ItemDTO.class);
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("")
    public ResponseEntity<Object> createItem(@RequestBody @Valid ItemDTO itemDTO) {

        Item item = new Item();
        item.setItemId(itemDTO.getItemId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setUnitPrice(itemDTO.getUnitPrice());
        item.setActive(true);

        Item createdItem = this.itemService.save(item);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdItem.getItemId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


}
