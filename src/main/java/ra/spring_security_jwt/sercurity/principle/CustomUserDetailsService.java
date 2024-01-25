package ra.spring_security_jwt.sercurity.principle;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.spring_security_jwt.model.User;
import ra.spring_security_jwt.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1. Lấy User từ db theo userName đăng nhập
        User user = usersRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        //2. Lấy các quyền của user
        List<GrantedAuthority> authorities = user.getListRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        //3. Trả về đối tượng UserDetail
        return CustomUserDetails.builder().id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .authorities(authorities).build();
    }
}
