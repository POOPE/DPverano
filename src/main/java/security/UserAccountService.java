/*
 * UserService.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User findByActor(final Actor actor) {
		Assert.notNull(actor);

		User result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}

	public User createUser(final String authority) {
		final User user = new User();

		final Collection<Authority> auths = new HashSet<>();
		final Authority auth = new Authority();
		auth.setAuthority(authority);
		auths.add(auth);

		user.setAuthorities(auths);
		return user;
	}

	// Other business methods -------------------------------------------------

	public User addAuthority(final User user, final String authority) {
		final Authority auth = new Authority();
		auth.setAuthority(authority);
		user.getAuthorities().add(auth);
		return user;
	}

	public User findByUsername(final String username) {
		return this.userAccountRepository.findByUsername(username);
	}
}
