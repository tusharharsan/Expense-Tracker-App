package com.example.UserService.Repository;

import com.example.UserService.Entity.UserInfo;
import com.example.UserService.Entity.UserInfoDto;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends CrudRepository<UserInfo, String> {
    Optional<UserInfo> findByUserId(String userId);
}
