package com.cedar.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cedar.app.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	
	UserEntity findUserEntityByEmail(String email);

	UserEntity findUserByUserId(String userId);
	
	UserEntity findUserByLastName(String lastName);

	@Query(value = "select * from Users u where u.user_id=:userId", nativeQuery = true)
	UserEntity getUserByUserId(@Param("userId")String userId);
	
}
