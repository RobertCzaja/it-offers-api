package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsonFromHtmlExtractor {

    /**
     * TODO to refactor
     */
    public String getRawJsonFromHtml(String html) {
        Document responseBody = Jsoup.parse(html);
        Elements bodyChildren = responseBody.select("body").last().children();
        String lastScriptRawContent = bodyChildren.last().html();
        int firstBraceIndex = lastScriptRawContent.indexOf('{');
        String cutBody = lastScriptRawContent.substring(firstBraceIndex);
        String rawJsonPayload = cutBody.replace("]\\n\"])", "");
        String cleanedJson = rawJsonPayload.replace("\\\"","\"");
        return cleanedJson.replace("\\\\\"", "\\\"");
    }
}
