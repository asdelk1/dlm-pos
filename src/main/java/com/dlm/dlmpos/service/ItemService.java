package com.dlm.dlmpos.service;

import com.dlm.dlmpos.dto.ItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.repository.ItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAll(){
        return this.itemRepository.findAll();
    }

    public Optional<Item> get(Long id){
        return this.itemRepository.findById(id);
    }

    public Item save(Item item){

        item.setType(item.getType().toUpperCase(Locale.ROOT));
        return this.itemRepository.save(item);
    }

    public Item getEntity(ItemDTO itemDTO){

        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setItemId(itemDTO.getItemId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setUnitPrice(itemDTO.getUnitPrice());

        return item;
    }

    public Set<String> getItemTypes(){
        return this.itemRepository.findAll().stream()
                .filter(item -> item.getType() != null && !StringUtils.isBlank(item.getType()))
                .map(i -> i.getType().toUpperCase(Locale.ROOT)).collect(Collectors.toSet());
    }


}
