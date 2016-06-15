package ru.akorsa.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.akorsa.test.ESservice.ESItemService;
import ru.akorsa.test.entity.Item;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Controller
public class ItemController {

    @Autowired
    private ESItemService itemService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(value = "/search")
    public ModelAndView search(@RequestParam("searchString") String searchString) {

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("description", searchString)).build();
        List<Item> items = elasticsearchTemplate.queryForList(searchQuery, Item.class);

        return new ModelAndView("search", "items", items);
    }
}
