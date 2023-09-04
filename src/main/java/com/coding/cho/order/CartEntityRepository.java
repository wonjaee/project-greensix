package com.coding.cho.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.cho.common.domain.entity.MemberEntity;

public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {

	Optional<CartEntity> findByMember_email(String email);

	CartEntity findAllByMember_email(String email);

	CartEntity findByMember(MemberEntity member);

	void deleteByMember(MemberEntity member);

	

}
