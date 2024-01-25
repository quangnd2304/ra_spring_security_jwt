package ra.spring_security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignUpResponse;
import ra.spring_security_jwt.service.UserService;

@RestController
@RequestMapping("api/v1/public/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }
}
