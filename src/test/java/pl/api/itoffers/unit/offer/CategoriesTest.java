package pl.api.itoffers.unit.offer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.offer.domain.Category;

public class CategoriesTest {

  @Test
  void shouldTakeUniqueCategories() {

    var categories = new HashSet<Category>();
    categories.add(new Category("Ruby"));
    categories.add(new Category("java"));
    categories.add(new Category("Ruby"));

    assertThat(categories).hasSize(2);
  }
}
