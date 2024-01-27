package ra.spring_security_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.spring_security_jwt.dto.request.SignInRequest;
import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignInResponse;
import ra.spring_security_jwt.dto.response.SignUpResponse;
import ra.spring_security_jwt.dto.response.UserResponse;
import ra.spring_security_jwt.model.User;
import ra.spring_security_jwt.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/public/user")
    public ResponseEntity<SignUpResponse> register(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse signUpResponse = userService.register(signUpRequest);
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }
    @PostMapping("/public/user/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest){
        return new ResponseEntity<>(userService.login(signInRequest),HttpStatus.OK);
    }
    @GetMapping("/admin/user")
    public ResponseEntity<List<UserResponse>> findAll(){
        return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
    }

}
