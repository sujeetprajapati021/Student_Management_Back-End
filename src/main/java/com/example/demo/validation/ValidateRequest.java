package com.example.demo.validation;

import com.example.demo.constant.Message;
import com.example.demo.dto.AddUserRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BadRequestException;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@AllArgsConstructor
@Component
public class ValidateRequest {

    @Autowired
    private UserRepository userDao;

    String regex = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";
    String regSpecialChars = "([a-zA-Z\\s+']{1,80}\\s*)+";
    String regSpecialOnNums = "(^[0-9]{10}$)";
    Pattern pattern = Pattern.compile(regex);
    Pattern patternSpecialChars = Pattern.compile(regSpecialChars);
    Pattern patternSpacialNums = Pattern.compile(regSpecialOnNums);

    public void validateRequest(AddUserRequest request) {

        Matcher matcher = pattern.matcher(request.getEmailId());
        Matcher matcherFirstName = patternSpecialChars.matcher(request.getFirstName());
        Matcher matcherLastName = patternSpecialChars.matcher(request.getLastName());

        Optional<UserEntity> user = userDao.findByEmailId(request.getEmailId());

        if (!matcherFirstName.matches())
            throw new BadRequestException(Message.FIRST_NAME_NOT_VALID);

        if (!matcherLastName.matches())
            throw new BadRequestException(Message.LAST_NAME_NOT_VALID);

        if (request.getFirstName() == null || request.getFirstName() == "")
            throw new BadRequestException(Message.FIRSTNAME_CAN_NOT_BE_BLANK);

        if (request.getLastName() == null || request.getLastName() == "")
            throw new BadRequestException(Message.LASTNAME_CAN_NOT_BE_BLANK);

        if (user.isPresent())
            throw new BadRequestException(Message.EMAIL_ALREADY_EXISTS);

        if (request.getEmailId() == null || request.getEmailId() == "")
            throw new BadRequestException(Message.EMAIL_MUST_BE_PROVIDED);

        if (!matcher.matches())
            throw new BadRequestException(Message.SHOULD_BE_VALID_EMAIL);

        if (request.getPassword() == null || request.getPassword() == "")
            throw new BadRequestException(Message.PASSWORD_IS_REQUARD);

        if (request.getPassword().length() < 6)
            throw new BadRequestException(Message.PASSWORD_LENGTH_NOT_CORRECT);

        if (!isValidPhoneNumber(request.getMobileNumber()))
            throw new BadRequestException(Message.PHONE_NO_NOT_VALID);

    }

    private Boolean isValidPhoneNumber(String phoneNumber) {
        String strPattern = "^[0-9]{10}$";
        return phoneNumber.matches(strPattern);
    }
}
