package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.infrastructure.AbstractJustJoinItHttpConnector;

@Service
public final class JustJoinItHttpConnectorV2 extends AbstractJustJoinItHttpConnector {
    @Override
    protected String getRawJsonOffers(Document responseBody) {
        return new JsonFromHtmlExtractor().getRawJsonFromHtml(responseBody);
    }
}
