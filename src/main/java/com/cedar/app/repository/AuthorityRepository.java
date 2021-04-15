package com.cedar.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cedar.app.entity.AuthorityEntity;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {

	List<AuthorityEntity> findByName(String name);
}
