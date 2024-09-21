package com.example.demo.controller;


import com.example.demo.constant.Message;
import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.jwt.JwtUser;
import com.example.demo.jwt.TokenProvider;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@Api(tags = Message.AUTHENTICATE_CONTROLLER)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;

    @Resource(name = Message.USERSERVICE)
    private IUserService userService;

    @CrossOrigin("*")
    @ApiOperation(value = Message.GENERATE_NEW_TOKEN)
    @PostMapping
    public ResponseEntity<BaseResponse> createAuthToken(
            @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

            JwtUser jwtUser = userService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(jwtUser);

            BaseResponse<Object> response = BaseResponse.builder()
                    .successMsg(Message.TOKEN_GENERATED)
                    .token(token)
                    .build();
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new BadRequestException(Message.INVALID_USERNAME_PASSWORD);
        } catch (DisabledException e) {
            throw new BadRequestException(Message.USER_BLOCKED);
        }

    }

}
