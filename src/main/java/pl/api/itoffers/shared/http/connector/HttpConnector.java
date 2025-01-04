package pl.api.itoffers.shared.http.connector;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class HttpConnector {
  public String fetchSourceHtml(URL url) throws IOException {
    Scanner scanner = null;
    try {
      URLConnection connection = url.openConnection();
      scanner = new Scanner(connection.getInputStream());
      scanner.useDelimiter("\\Z");
      return scanner.next();
    } finally {
      if (null != scanner) {
        scanner.close();
      }
    }
  }
}
