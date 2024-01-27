package ra.spring_security_jwt.service;

import ra.spring_security_jwt.dto.request.SignInRequest;
import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignInResponse;
import ra.spring_security_jwt.dto.response.SignUpResponse;
import ra.spring_security_jwt.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);

    SignInResponse login(SignInRequest signInRequest);

    List<UserResponse> findAll();

}
