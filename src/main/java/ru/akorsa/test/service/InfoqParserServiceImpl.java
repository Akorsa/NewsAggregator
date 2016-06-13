package ru.akorsa.test.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.akorsa.test.entity.Item;
import ru.akorsa.test.exception.RssException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service("infoq")
public class InfoqParserServiceImpl implements ParserService {

    @Override
    public List<Item> getItems(String url) throws RssException {
        List<Item> items = new ArrayList<>();

        try {
            Document doc = getDocument(url);
            if (doc == null) return null;

            Elements elements = doc.select(".news_type_block");
            if (elements.size() == 0) return null;

            for (Element element : elements) {

                Item item = new Item();

                // title
                Element titleElement = element.select("a[title]").first();
                String title = titleElement.text();
                item.setTitle(title);

                // description
                Element descriptionElement = element.select("p").first();
                String description = "";
                if (descriptionElement != null) {
                    description = descriptionElement.text();
                }
                item.setDescription(description);

                // publishedDate
                String publishedDate = element.select("span").first().text();
                String temp = publishedDate.substring(publishedDate.lastIndexOf("on") + 4, publishedDate.lastIndexOf("2016") + 4);
                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date = formatter.parse(temp);

                item.setPublishedDate(date);

                // link
                String link = "https://www.infoq.com" + element.select("a[href]").first().attr("href");
                item.setLink(link);

                items.add(item);
            }
        } catch (Exception e) {
            throw new RssException(e);
        }
        return items;
    }

    @Override
    public Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("none")
                .get();

        return document;
    }
}
