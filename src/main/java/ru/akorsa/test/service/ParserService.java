package ru.akorsa.test.service;

import org.jsoup.nodes.Document;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.exception.RssException;

import java.io.IOException;
import java.util.List;

public interface ParserService {

    List<Item> getItems(String url) throws RssException;

    Document getDocument(String url) throws IOException;
}
