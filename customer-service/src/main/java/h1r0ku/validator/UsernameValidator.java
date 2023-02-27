package h1r0ku.validator;

import h1r0ku.repository.CustomerRepository;
import h1r0ku.validator.constraint.UsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    private final CustomerRepository customerRepository;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{7,29}$");

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (username == null) {
            return false;
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            return false;
        }

        if (customerRepository.findByUsername(username) != null) {
            return false;
        }

        return true;
    }
}
