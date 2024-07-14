package com.cakeshoppingapp.customer;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.cakeshoppingapp.dtoes.AuthenticationDTO;



public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -8801422038429081822L;
	private final AuthenticationDTO authenticationDTO;

	public UserDetailsImpl(AuthenticationDTO authenticationDTO) {
		this.authenticationDTO = authenticationDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("public Collection<? extends GrantedAuthority> getAuthorities()");
		return Arrays.stream(StringUtils.tokenizeToStringArray(authenticationDTO.role(), " "))
				.map(str -> new SimpleGrantedAuthority("ROLE_" + str)).toList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		System.out.println("UserDetailsImpl getPassword");
		return authenticationDTO.password();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		System.out.println("UserDetailsImpl getUsername");
		return authenticationDTO.username();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	

}
