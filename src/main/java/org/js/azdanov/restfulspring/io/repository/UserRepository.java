package org.js.azdanov.restfulspring.io.repository;

import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);
}
