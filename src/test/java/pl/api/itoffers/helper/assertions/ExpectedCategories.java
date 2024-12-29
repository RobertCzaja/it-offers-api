package pl.api.itoffers.helper.assertions;

import java.util.ArrayList;
import java.util.List;

public class ExpectedCategories {
  public List<List> categories = new ArrayList<>();

  public ExpectedCategories add(String name, Double percentage, Integer count) {
    categories.add(List.of(name, percentage, count));
    return this;
  }
}
