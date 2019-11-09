package org.js.azdanov.restfulspring.io.repository;

import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);

  UserEntity findByUserId(String userId);
}
