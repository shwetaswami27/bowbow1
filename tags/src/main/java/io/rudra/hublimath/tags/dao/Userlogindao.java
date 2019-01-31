package io.rudra.hublimath.tags.dao;


import io.rudra.hublimath.tags.entities.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userlogindao extends JpaRepository<UserLogin,Long> {
    UserLogin findByEmailid(String emailid);
    UserLogin findByName(String username);
}
