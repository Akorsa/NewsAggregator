package ru.akorsa.test.ESservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.akorsa.test.ESrepository.ItemESRepository;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.repository.ItemRepository;

import java.util.List;

@Service
public class ESItemService {

    @Autowired
    private ItemESRepository itemRepository;

    
    public Item save(Item article) {
        return itemRepository.save(article);
    }
    
    public Item findOne(Integer id) {
        return itemRepository.findOne(id);
    }
    
    public Iterable<Item> findAll() {
        return itemRepository.findAll();
    }


    public List<Item> findByTitle(String name) {
        return itemRepository.findByTitle(name);
    }

    
    public List<Item> findByDescriptionUsingCustomQuery(String name) {
        return itemRepository.findByDescriptionUsingCustomQuery(name);
    }

    
    public long count() {
        return itemRepository.count();
    }

    
    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
