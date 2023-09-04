package com.coding.cho.common.secutiry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.common.domain.entity.MemberEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private final MemberEntityRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//System.out.println("email: "+email);
		MemberEntity entity=repository.findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException("존재하지 않거나 탈퇴한 회원입니다."));
		
		Set<SimpleGrantedAuthority> authorities=entity.getRoleSet().stream()
				.map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
				.collect(Collectors.toSet());
		return new User(email, entity.getPass(), authorities);
	}

}
