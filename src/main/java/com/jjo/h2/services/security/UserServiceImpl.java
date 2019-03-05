package com.jjo.h2.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.model.security.User;
import com.jjo.h2.repositories.security.UserRepository;
import com.jjo.h2.utils.HException;
import com.jjo.h2.utils.MapperUtil;

@Service
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passEncoder;

	@Autowired
	private MapperUtil mapperUtil;

	@Override
	public UserDTO getUser(Long id) throws HException {
		return userRepo.findById(id).map(u -> toDTO(u)).orElse(null);
	}

	@Override
	public void registerUser(UserDTO user) throws HException {
		user.setPassword(passEncoder.encode(user.getPassword()));
		userRepo.save(toEntity(user));
	}

	/**
	 * Call the mapper and transform the object
	 * 
	 * @param dto
	 * @return
	 */
	private User toEntity(UserDTO dto) {
		return mapperUtil.getMapper(User.class, UserDTO.class).getDestination(dto);
	}

	/**
	 * Call the mapper and transform the object
	 * 
	 * @param entity
	 * @return
	 */
	private UserDTO toDTO(User entity) {
		return mapperUtil.getMapper(UserDTO.class, User.class).getDestination(entity);
	}
}
