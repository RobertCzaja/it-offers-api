package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsonFromHtmlExtractor {

  public String getRawJsonFromHtml(Document responseBody) {
    Elements bodyChildren = responseBody.select("body").last().children();
    String lastScriptTagContent = bodyChildren.last().html();
    int firstBraceIndex = lastScriptTagContent.indexOf('{');
    return lastScriptTagContent
        .substring(firstBraceIndex)
        .replace("]\\n\"])", "")
        .replace("\\\"", "\"")
        .replace("\\\\\"", "\\\"");
  }
}
