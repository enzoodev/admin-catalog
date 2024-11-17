package com.admin.catalog.application.category.create;

import com.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public class CreateCategoryUseCaseTest {

    // 1. Test the happy path
    // 2. Test giving invalid category name
    // 3. Test creating an inactive
    // 4. Test simulating a gateway error

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenReturnCategoryId() {
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

        final var useCase = new CreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.getId());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                    return Objects.equals(expectedName, aCategory.getName())
                            && Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && Objects.nonNull(aCategory.getId())
                            && Objects.nonNull(aCategory.getCreatedAt())
                            && Objects.nonNull(aCategory.getUpdatedAt())
                            && Objects.isNull(aCategory.getDeletedAt());
        }));
    }
}
