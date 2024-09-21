package com.example.demo.constant;

public interface Message {

    String TOKEN_GENERATED = "New authorization token has been generated.";
    String INVALID_USERNAME_PASSWORD = "Username or password is invalid";
    String USER_BLOCKED = "This user is blocked";
    String USERSERVICE = "userService";
    String GENERATE_NEW_TOKEN = "It is to generate new authorization token";
    String AUTHENTICATE_CONTROLLER = "Authentication Controller Provider";
    String STUDENT_CONTROLLER = "Student Controller Provider";
    String PING = "Ping Successfully";
    String ADD_STUDENT = "API to add student details";
    String GET_ALL_STUDENT = "API to get all student details";
    String GET_STUDENT = "API to get student details by Id";
    String UPDATE_STUDENT = "API to update student details";
    String DELETE_STUDENT = "API to delete student details by Id";
    String GET_ALL_STUDENT_PDF = "Api to get pdf of students details";
    String FIRST_NAME_NOT_VALID = "First name is not valid";
    String LAST_NAME_NOT_VALID = "Last name is not valid";
    String FIRSTNAME_CAN_NOT_BE_BLANK = "First name can not be blank";
    String LASTNAME_CAN_NOT_BE_BLANK = "Last name can not be blank";
    String EMAIL_ALREADY_EXISTS = "Email already exists";
    String EMAIL_MUST_BE_PROVIDED = "Email must be provided";
    String PASSWORD_IS_REQUARD = "Password is required";
    String PASSWORD_LENGTH_NOT_CORRECT = "Password length must be equal/greater than 6-chars";
    String SHOULD_BE_VALID_EMAIL = "Should be a valid email";
    String PHONE_NO_NOT_VALID = "Phone number is not valid, its length must be 10-digits without using country code i.e '+91'";

//    String FILE_NOT_FOUND = "No such file found";

}
