package com.admin.catalog.domain.category;

import com.admin.catalog.domain.exceptions.DomainException;
import com.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
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

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var currentException = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, currentException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage , currentException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var currentException = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, currentException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage , currentException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "te ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var currentException = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, currentException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage , currentException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, but also the leap into electronic typesetting.";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var currentException = Assertions.assertThrows(DomainException.class, () -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, currentException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage , currentException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "name";
        final var expectedDescription = "  ";
        final var expectedIsActive = true;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedIsActive = false;

        final var currentCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(currentCategory);
        Assertions.assertNotNull(currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertNotNull(currentCategory.getUpdatedAt());
        Assertions.assertNotNull(currentCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallInactivateCategoryAnd_thenReturnCategoryInactivated() {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var currentCategory = aCategory.inactivate();

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(), currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(currentCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallActiveCategoryAnd_thenReturnCategoryActivated() {
        final var expectedName = "name";
        final var expectedDescription = "description";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var currentCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(aCategory.getId(), currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(currentCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "new name";
        final var expectedDescription = "new description";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("name", "description", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();

        final var currentCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> currentCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), currentCategory.getId());
        Assertions.assertEquals(expectedName, currentCategory.getName());
        Assertions.assertEquals(expectedDescription, currentCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, currentCategory.isActive());
        Assertions.assertNotNull(currentCategory.getCreatedAt());
        Assertions.assertTrue(currentCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(currentCategory.getDeletedAt());
    }
}
