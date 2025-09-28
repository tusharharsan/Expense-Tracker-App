package org.example.Repository;

import org.example.Entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UsersEntity , Long> {
    public UsersEntity findByUsername(String username);
}
