package ra.spring_security_jwt.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.spring_security_jwt.dto.request.SignUpRequest;
import ra.spring_security_jwt.dto.response.SignUpResponse;
import ra.spring_security_jwt.model.ERoles;
import ra.spring_security_jwt.model.Role;
import ra.spring_security_jwt.model.User;
import ra.spring_security_jwt.repository.RoleRepository;
import ra.spring_security_jwt.repository.UsersRepository;
import ra.spring_security_jwt.service.UserService;

import java.util.HashSet;
import java.util.Set;

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
        User userCreated = usersRepository.save(user);
        return modelMapper.map(userCreated, SignUpResponse.class);
    }
}
