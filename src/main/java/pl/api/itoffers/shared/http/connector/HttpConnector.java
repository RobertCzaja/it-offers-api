package pl.api.itoffers.shared.http.connector;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class HttpConnector {
  public String fetchSourceHtml(URL url) throws IOException {
    URLConnection connection = url.openConnection();
    try (Scanner scanner = new Scanner(connection.getInputStream())) {
      scanner.useDelimiter("\\Z");
      return scanner.next();
    }
  }
}
