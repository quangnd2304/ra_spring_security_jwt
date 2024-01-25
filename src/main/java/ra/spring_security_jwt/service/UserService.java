package ra.spring_security_jwt.service;

import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignUpResponse;

public interface UserService {
    SignUpResponse register(SignUpRequest signUpRequest);
}
