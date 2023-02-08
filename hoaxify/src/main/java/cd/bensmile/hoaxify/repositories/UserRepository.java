package cd.bensmile.hoaxify.repositories;

import cd.bensmile.hoaxify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
