package ra.spring_security_jwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @Column(name = "user_id",length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "user_name",columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String userName;
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(100)")
    private String fullName;
    @Column(columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String email;
    private boolean sex;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "user_status")
    private boolean status;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> listRoles = new HashSet<>();
}
