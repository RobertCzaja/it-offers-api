package pl.api.itoffers.provider.justjoinit.infrastructure;

import org.jsoup.Jsoup;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public final class JustJoinItHttpConnector implements JustJoinItConnector
{
    private static final String PATH = "/all-locations/";
    private final URL origin;

    public JustJoinItHttpConnector(URL origin) {
        this.origin = origin;
    }

    public String fetchStringifyJsonPayload(String technology) {

        try {
            return Jsoup.parse(fetchSourceHtml(technology))
                    .select("#__NEXT_DATA__")
                    .get(0)
                    .html();
        }catch (Exception e) {
            e.printStackTrace();
            throw new JustJoinItException("Error occurred fetching raw HTML", e);
        }
    }

    private String fetchSourceHtml(String technology) throws IOException {
        URLConnection connection =  new URL(origin.toString()+PATH+technology).openConnection();
        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.useDelimiter("\\Z");
        String htmlSource = scanner.next();
        scanner.close();
        return htmlSource;
    }
}
