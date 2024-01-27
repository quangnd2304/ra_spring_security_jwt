package ra.spring_security_jwt.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.spring_security_jwt.dto.request.SignInRequest;
import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignInResponse;
import ra.spring_security_jwt.dto.response.SignUpResponse;
import ra.spring_security_jwt.dto.response.UserResponse;
import ra.spring_security_jwt.model.ERoles;
import ra.spring_security_jwt.model.Role;
import ra.spring_security_jwt.model.User;
import ra.spring_security_jwt.repository.RoleRepository;
import ra.spring_security_jwt.repository.UsersRepository;
import ra.spring_security_jwt.sercurity.jwt.JwtProvider;
import ra.spring_security_jwt.sercurity.principle.CustomUserDetails;
import ra.spring_security_jwt.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        Set<Role> listRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role -> {
            switch (role) {
                case "admin":
                    listRoles.add(roleRepository.findByName(ERoles.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Không tìm thấy role")));
                    break;
                case "moderator":
                    listRoles.add(roleRepository.findByName(ERoles.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("Không tìm thấy role")));
                    break;
                case "user":
                default:
                    listRoles.add(roleRepository.findByName(ERoles.ROLE_USER).orElseThrow(() -> new RuntimeException("Không tìm thấy role")));
            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRoles(listRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        User userCreated = usersRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);
        List<String> listRoleUser = new ArrayList<>();
        userCreated.getListRoles().stream().forEach(role -> {
            listRoleUser.add(role.getName().name());
        });
        signUpResponse.setListRoles(listRoleUser);
        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()));
        } catch (Exception ex) {
            throw new RuntimeException("Username or Password incorrect");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        String refreshToken = jwtProvider.generateRefreshToken(userDetails);
        return new SignInResponse(userDetails.getUsername(), userDetails.getPassword()
                , userDetails.getAuthorities(), accessToken, refreshToken);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> listUser = usersRepository.findAll();
        return listUser.stream().map(user -> modelMapper.map(user,UserResponse.class)).collect(Collectors.toList());
    }
}
