package ra.spring_security_jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String id;
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private boolean sex;
    private Date birthDate;
    private boolean status;
}
