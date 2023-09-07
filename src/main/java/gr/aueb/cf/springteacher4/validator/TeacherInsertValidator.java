package gr.aueb.cf.springteacher4.validator;

import gr.aueb.cf.springteacher4.dto.TeacherInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TeacherInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TeacherInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeacherInsertDTO teacherInsertDto = (TeacherInsertDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (teacherInsertDto.getFirstname().length() < 3 || teacherInsertDto.getFirstname().length() > 512) {
            errors.rejectValue("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (teacherInsertDto.getLastname().length() < 3 || teacherInsertDto.getLastname().length() > 512) {
            errors.rejectValue("lastname", "size");
        }
    }
}
