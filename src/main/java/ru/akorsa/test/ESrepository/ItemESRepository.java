package ru.akorsa.test.ESrepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.entity.Item;

import java.util.List;


public interface ItemESRepository extends ElasticsearchRepository<Item, Integer> {

    List<Item> findByTitle(String name);

    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"description\" : \"?0\"}}}}")
    List<Item> findByDescriptionUsingCustomQuery(String message);
}
