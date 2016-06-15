package ru.akorsa.test.service;

import org.elasticsearch.common.unit.Fuzziness;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.akorsa.test.ESservice.ESItemService;
import ru.akorsa.test.entity.Blog;
import ru.akorsa.test.entity.Item;

import java.util.List;

import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.AND;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/testContext.xml"})
public class ElasticSearchQueryTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ESItemService itemService;


    private final Blog habr = new Blog("habr");
    private final Blog infoq = new Blog("infoq");

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Item.class);
        elasticsearchTemplate.createIndex(Item.class);
        elasticsearchTemplate.putMapping(Item.class);

        Item item = new Item();
        item.setDescription("Spring Data Elasticsearch");
        item.setBlog(habr);
        itemService.save(item);

        item = new Item();
        item.setDescription("Search engines");
        item.setBlog(infoq);
        itemService.save(item);

        item = new Item();
        item.setDescription("Second item About Elasticsearch");
        item.setBlog(habr);
        itemService.save(item);

        item = new Item();
        item.setDescription("Elasticsearch Tutorial");
        item.setBlog(infoq);
        itemService.save(item);
    }

    @Test
    public void givenFullTitle_whenRunMatchQuery_thenDocIsFound() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("description", "Search engines").operator(AND)).build();
        final List<Item> articles = elasticsearchTemplate.queryForList(searchQuery, Item.class);
        assertEquals(1, articles.size());
    }

    @Test
    public void givenOneTermFromTitle_whenRunMatchQuery_thenDocIsFound() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("description", "Engines Solutions")).build();
        final List<Item> articles = elasticsearchTemplate.queryForList(searchQuery, Item.class);
        assertEquals(1, articles.size());
        assertEquals("Search engines", articles.get(0).getDescription());
    }

    @Test
    public void givenPartTitle_whenRunMatchQuery_thenDocIsFound() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("description", "elasticsearch data")).build();
        final List<Item> articles = elasticsearchTemplate.queryForList(searchQuery, Item.class);
        assertEquals(3, articles.size());
    }

    @Test
    public void givenNotExactPhrase_whenUseSlop_thenQueryMatches() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("description", "spring elasticsearch").slop(1)).build();
        final List<Item> articles = elasticsearchTemplate.queryForList(searchQuery, Item.class);
        assertEquals(1, articles.size());
    }

    @Test
    public void givenPhraseWithType_whenUseFuzziness_thenQueryMatches() {
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("description", "spring date elasticserch").operator(AND).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

        final List<Item> articles = elasticsearchTemplate.queryForList(searchQuery, Item.class);
        assertEquals(1, articles.size());
    }
}
