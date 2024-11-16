package com.admin.catalog.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNull(currentCategory.getDeletedAt());
    }
}
