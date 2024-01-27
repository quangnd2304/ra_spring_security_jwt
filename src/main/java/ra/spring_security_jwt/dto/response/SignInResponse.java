package ra.spring_security_jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInResponse {
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> listRoles;
    private final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;
}
