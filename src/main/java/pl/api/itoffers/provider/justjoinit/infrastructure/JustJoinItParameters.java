package pl.api.itoffers.provider.justjoinit.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@ConfigurationProperties(prefix = "application.provider.just-join-it")
@Component
@Data
public class JustJoinItParameters {
     private final static String DETAILS_PATH = "/job-offer/";
     private final static String LIST_PATH = "/job-offers/all-locations/";     private String origin;

     public String getOffersUrl(String technology) {
         return this.getOrigin().toString()+LIST_PATH+technology;
     }

     public URL getOrigin() {
         try {
             return new URL(origin);
         } catch (MalformedURLException e) {
             throw new RuntimeException(e);
         }
     }

     public URL getOfferUrl(String slug) {
         try {
             return new URL(origin+DETAILS_PATH+slug);
         } catch (MalformedURLException e) {
             throw new RuntimeException(e);
         }
     }
}
