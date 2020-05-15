package com.knightcharacter.app.validation.validator;

import com.knightcharacter.app.gateway.privatedb.representation.enums.WeaponType;
import com.knightcharacter.app.validation.annotation.ValidWeaponType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WeaponTypeValidator implements ConstraintValidator<ValidWeaponType, CharSequence> {

    private List<String> acceptedValues;
    private String message;

    @Override
    public void initialize(ValidWeaponType annotation) {
        acceptedValues = Stream.of(WeaponType.values()).map(WeaponType::name).collect(Collectors.toList());
        message = String.format("Weapon type is not valid. It should be one of values : %s",
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
