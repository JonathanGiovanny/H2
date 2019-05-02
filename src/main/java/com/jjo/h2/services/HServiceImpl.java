package com.jjo.h2.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.model.HType;
import com.jjo.h2.repositories.HHistoryRepository;
import com.jjo.h2.repositories.HRepository;
import com.jjo.h2.utils.MapperUtil;
import com.jjo.h2.utils.Utils;

@Service
public class HServiceImpl implements HService {

  @Autowired
  private HRepository hRepo;

  @Autowired
  private HHistoryRepository hHisRepo;

  @Autowired
  private HTypeService htService;

  @Autowired
  private MapperUtil mapperUtil;

  public HDTO getH(Long id) {
    return hRepo.findById(id).map(h -> toDTO(h)).orElse(null);
  }

  @Override
  public HDTO saveH(HDTO h) {
    return toDTO(hRepo.save(toEntity(h)));
  }

  @Override
  public HDTO updateH(Long id, HDTO h) {
    H entity = hRepo.getOne(id);
    copyData(h, entity);
    return Optional.ofNullable(hRepo.save(toEntity(h))).map(this::toDTO).orElse(null);
  }

  @Override
  public List<HDTO> findAll(Pageable pageable) {
    return findAll(new HDTO(), pageable);
  }

  @Override
  public List<HDTO> findAll(HDTO filter, Pageable pageable) {
    return hRepo.findAll(Example.of(toEntity(filter)), pageable).getContent().stream().map(this::toDTO)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteH(Long id) {
    hRepo.delete(toEntity(this.getH(id)));
  }

  @Override
  public HDTO increaseClick(Long id) {
    H h = hRepo.getOne(id);
    h.setClicks(h.getClicks() + 1);
    hRepo.save(h);

    HHistory hh = HHistory.builder().h(h).date(LocalDateTime.now()).build();
    hHisRepo.save(hh);

    return getH(id);
  }

  /**
   * Copy the data from the dto to the entity
   * 
   * @param dto
   * @param entity
   * @return
   */
  private H copyData(HDTO dto, H entity) {
    entity.setName(Utils.isNotNullOr(dto.getName(), entity.getName()));
    entity.setUrl(Utils.isNotNullOr(dto.getUrl(), entity.getUrl()));
    entity.setCover(Utils.isNotNullOr(dto.getCover(), entity.getCover()));
    entity.setScore(Utils.isNotNullOr(dto.getScore(), entity.getScore()));

    HType ht = Optional.ofNullable(dto.getType()).filter(h -> h.getId().equals(entity.getType().getId())).map(htService::toEntity)
        .orElse(entity.getType());
    entity.setType(ht);

    if (entity.getTags().size() == dto.getTags().size() && entity.getTags().containsAll(dto.getTags())) {
      entity.setTags(dto.getTags());
    }

    return entity;
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param dto
   * @return
   */
  private H toEntity(HDTO dto) {
    return mapperUtil.getMapper(H.class, HDTO.class).getDestination(dto);
  }

  /**
   * Call the mapper and transform the object
   * 
   * @param entity
   * @return
   */
  private HDTO toDTO(H entity) {
    return mapperUtil.getMapper(HDTO.class, H.class).getDestination(entity);
  }
}
