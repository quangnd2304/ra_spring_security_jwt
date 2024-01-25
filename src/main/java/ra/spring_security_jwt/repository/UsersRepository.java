package ra.spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.spring_security_jwt.model.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,String> {
    Optional<User> findByUserName(String userName);
}
