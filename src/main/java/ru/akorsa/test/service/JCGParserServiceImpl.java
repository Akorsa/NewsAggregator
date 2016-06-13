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

@Service("jcg")
public class JCGParserServiceImpl implements ParserService {

    @Override
    public List<Item> getItems(String url) throws RssException {
        List<Item> items = new ArrayList<>();

        try {
            Document doc = getDocument(url);
            if (doc == null) return null;

            Elements elements = doc.select("article");
            if (elements.size() == 0) return null;

            for (Element element : elements) {

                Item item = new Item();

                // title
                String title = element.select("a[href]").first().text();
                item.setTitle(title);

                // description
                Element descriptionElement = element.select(".entry").first();
                String description = "";
                if (descriptionElement != null) {
                    description = descriptionElement.text();
                }
                item.setDescription(description);

                // publishedDate
                String publishedDate = element.select(".tie-date").first().text();
                String dateStr = "";
                if (publishedDate.indexOf("th") != -1 || publishedDate.indexOf("st") != -1 || publishedDate.indexOf("nd") != -1) {
                    dateStr = publishedDate.replace("th", "");
                    dateStr = dateStr.replace("st", "");
                    dateStr = dateStr.replace("nd", "");
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                Date date = formatter.parse(dateStr);

                item.setPublishedDate(date);

                // link
                String link = element.select("a[href]").first().attr("href");
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
