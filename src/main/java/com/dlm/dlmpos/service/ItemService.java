package com.dlm.dlmpos.service;

import com.dlm.dlmpos.dto.ItemDTO;
import com.dlm.dlmpos.entity.Item;
import com.dlm.dlmpos.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
