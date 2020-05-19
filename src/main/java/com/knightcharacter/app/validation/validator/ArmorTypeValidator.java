package com.knightcharacter.app.validation.validator;

import com.knightcharacter.app.gateway.privatedb.representation.enums.ArmorType;
import com.knightcharacter.app.validation.annotation.ValidArmorType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArmorTypeValidator implements ConstraintValidator<ValidArmorType, CharSequence> {

    private List<String> acceptedValues;
    private String message;

    @Override
    public void initialize(ValidArmorType annotation) {
        acceptedValues = Stream.of(ArmorType.values()).map(ArmorType::name).collect(Collectors.toList());
        message = String.format("Armor type is not valid. It should be one of values : %s",
            String.join("|", acceptedValues));
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value != null && acceptedValues.contains(value.toString())) {
            return true;
        }
        setInvalidRarityMessage(context, message);
        return false;
    }

    private void setInvalidRarityMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
