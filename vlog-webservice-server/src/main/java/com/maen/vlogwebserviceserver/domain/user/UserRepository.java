package com.maen.vlogwebserviceserver.domain.user;

import com.maen.vlogwebserviceserver.domain.user.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findByEmail(String email);
}
