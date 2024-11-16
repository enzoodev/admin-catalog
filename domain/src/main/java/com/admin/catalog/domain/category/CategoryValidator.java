package com.admin.catalog.domain.category;

import com.admin.catalog.domain.validation.Error;
import com.admin.catalog.domain.validation.ValidationHandler;
import com.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private static final int MAX_NAME_LENGTH = 255;
    private static final int MIN_NAME_LENGTH = 3;

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler){
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.validationHandler().append((new Error("'name' should not be null")));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append((new Error("'name' should not be empty")));
            return;
        }

        final int length = name.trim().length();
        if (length < MIN_NAME_LENGTH || length > MAX_NAME_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
