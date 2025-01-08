package pl.api.itoffers.provider.nofluffjobs.fetcher.details;

import org.jsoup.nodes.Document;

public class JsonFromOfferDetailsHtmlExtractor {

  public String getRawJsonFromHtml(Document responseBody) {
    return responseBody.select("script[type=application/ld+json]").html();
  }
}
