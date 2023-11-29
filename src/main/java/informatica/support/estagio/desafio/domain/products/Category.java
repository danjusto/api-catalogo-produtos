package informatica.support.estagio.desafio.domain.products;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    SMARTPHONE("smartphone"),
    LAPTOP("laptop");
    private final String name;
    public static final Map<String, Category> mapOfCategories = new HashMap<>();
    static {
        for (Category category : Category.values()) {
            mapOfCategories.put(category.getName(), category);
        }
    }
    Category(String category) {
        this.name = category;
    }
    public String getName() {
        return name;
    }
    public static Category getCategoryByName(String name) {
        return mapOfCategories.get(name);
    }
}
