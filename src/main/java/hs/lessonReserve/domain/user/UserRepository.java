package hs.lessonReserve.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<hs.lessonReserve.domain.User.User, Long> {

    hs.lessonReserve.domain.User.User findByEmail(String email);

}
