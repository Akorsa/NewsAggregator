package ru.akorsa.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll(new PageRequest(0, 20, Sort.Direction.DESC, "publishedDate")).getContent();
    }
}
