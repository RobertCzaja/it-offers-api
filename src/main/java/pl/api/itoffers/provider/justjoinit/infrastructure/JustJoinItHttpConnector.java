package pl.api.itoffers.provider.justjoinit.infrastructure;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

@Service
public final class JustJoinItHttpConnector implements JustJoinItConnector
{
    private static final String PATH = "/all-locations/";

    @Autowired
    private JustJoinItParameters parameters;

    public String fetchStringifyJsonPayload(String technology) {
        Document responseBody = null;

        try {
            responseBody = Jsoup.parse(fetchSourceHtml(technology));

            return responseBody
                .select("#__NEXT_DATA__")
                .get(0)
                .html();
        } catch (IndexOutOfBoundsException e) {
            throw new JustJoinItException(
                String.format("Empty \"%s\" response: \n\n%s\n", createUrl(technology), responseBody),
                e
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new JustJoinItException("Error occurred fetching raw HTML", e);
        }
    }

    private String createUrl(String technology) {
        return parameters.getOrigin().toString()+PATH+technology;
    }

    private String fetchSourceHtml(String technology) throws IOException {
        URLConnection connection =  new URL(createUrl(technology)).openConnection();
        Scanner scanner = new Scanner(connection.getInputStream());
        scanner.useDelimiter("\\Z");
        String htmlSource = scanner.next();
        scanner.close();
        return htmlSource;
    }
}
