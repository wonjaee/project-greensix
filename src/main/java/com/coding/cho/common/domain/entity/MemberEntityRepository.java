package com.coding.cho.common.domain.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long>{

	boolean existsByEmail(String email);

	Optional<MemberEntity> findByEmail(String email);

	Long findByName(String id);

	MemberEntity findAllByEmail(String email);

}
