package com.jjo.h2.services.security;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjo.h2.config.DatasourceNeo4j;
import com.jjo.h2.dto.security.RoleDTO;
import com.jjo.h2.model.security.AccessData;
import com.jjo.h2.model.security.Role;
import com.jjo.h2.repositories.security.RoleRepository;
import com.jjo.h2.utils.MapperUtil;

@Service
@Transactional(value = DatasourceNeo4j.TRANSACTION_MANAGER)
public class RolesServiceImpl implements RolesService {

  @Autowired
  private RoleRepository roleRepo;

  @Autowired
  private MapperUtil mapperUtil;

  @Override
  public List<RoleDTO> getRoles() {
    return StreamSupport.stream(roleRepo.findAll().spliterator(), false).map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public Long createRole(RoleDTO role) {
    return roleRepo.save(toEntity(role)).getId();
  }

  @Override
  public RoleDTO updateRole(RoleDTO role) {
    Role entity = roleRepo.findById(role.getId()).get();
    entity.setName(role.getName());
    return toDTO(roleRepo.save(entity));
  }
  
  @Override
  public RoleDTO updateRolePrivileges(RoleDTO role) {
    Role entity = roleRepo.findById(role.getId()).get();
    Role incomingData = toEntity(role);
    entity.setPrivileges(incomingData.getPrivileges());
    return toDTO(roleRepo.save(entity));
  }

  @Override
  public void deleteRole(Long id) {
    roleRepo.deleteById(id);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  private Role toEntity(RoleDTO dto) {
    return mapperUtil.getMapper(Role.class, RoleDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private RoleDTO toDTO(Role entity) {
    return mapperUtil.getMapper(RoleDTO.class, Role.class).getDestination(entity);
  }
}
