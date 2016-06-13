package ru.akorsa.test.service;

import org.junit.Before;
import org.junit.Test;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.exception.RssException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RssServiceTest {

    private RssService rssService;

    @Before
    public void setUp() throws Exception {
        rssService = new RssService();
    }

    @Test
    public void getItems() throws RssException {
        List<Item> items = rssService.getItems(new File("src/test/resources/lenta.xml"));
        assertEquals(7, items.size());
        Item firstItem = items.get(0);
        assertEquals("Неизвестный открыл стрельбу и захватил заложников в гей-баре во Флориде", firstItem.getTitle());
        assertEquals("12 06 2016 14:27:00", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
    }

}