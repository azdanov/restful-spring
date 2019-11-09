package org.js.azdanov.restfulspring.io.repository;

import java.util.List;
import java.util.UUID;
import org.js.azdanov.restfulspring.io.entity.AddressEntity;
import org.js.azdanov.restfulspring.io.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {

  List<AddressEntity> findAllByUserDetails(UserEntity userId);

  AddressEntity findByAddressId(UUID addressId);
}
