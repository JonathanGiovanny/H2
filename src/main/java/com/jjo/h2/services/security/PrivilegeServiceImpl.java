package com.jjo.h2.services.security;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.security.PrivilegeDTO;
import com.jjo.h2.model.security.Privilege;
import com.jjo.h2.repositories.security.PrivilegeRepository;
import com.jjo.h2.utils.MapperUtil;
import com.jjo.h2.utils.Utils;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

  @Autowired
  private PrivilegeRepository privilegeRepo;

  @Autowired
  private MapperUtil mapperUtil;

  @Override
  public PrivilegeDTO getPrivilege(Long id) {
    return toDTO(privilegeRepo.findById(id).orElseThrow());
  }

  @Override
  public List<PrivilegeDTO> findAll(Pageable pageable) {
    return privilegeRepo.findAll(pageable).getContent().stream().map(this::toDTO).collect(Collectors.toList());
  }

  @Override
  public PrivilegeDTO savePrivilege(PrivilegeDTO privilege) {
    return toDTO(privilegeRepo.save(toEntity(privilege)));
  }

  @Override
  public PrivilegeDTO updatePrivilege(Long id, PrivilegeDTO privilegeDto) {
    Privilege privilege = privilegeRepo.findById(id).orElseThrow();
    privilege.setName(Utils.isNotNullOr(privilegeDto.getName(), privilege.getName()));
    privilege.setDescription(Utils.isNotNullOr(privilegeDto.getDescription(), privilege.getDescription()));
    return toDTO(privilegeRepo.save(privilege));
  }

  @Override
  public void deletePrivilege(Long id) {
    privilegeRepo.deleteById(id);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  private Privilege toEntity(PrivilegeDTO dto) {
    return mapperUtil.getMapper(Privilege.class, PrivilegeDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private PrivilegeDTO toDTO(Privilege entity) {
    return mapperUtil.getMapper(PrivilegeDTO.class, Privilege.class).getDestination(entity);
  }
}
