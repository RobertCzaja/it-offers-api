package pl.api.itoffers.provider.nofluffjobs;

import org.jsoup.nodes.Document;

public class JsonFromHtmlExtractor {

  public String getRawJsonFromHtml(Document responseBody) {
    return responseBody.select("#serverApp-state").get(0).html();
  }
}
