package com.jjo.h2.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jjo.h2.exception.Errors;
import com.jjo.h2.exception.HException;
import com.jjo.h2.model.H;
import com.jjo.h2.model.HHistory;
import com.jjo.h2.repositories.HRepository;
import com.jjo.h2.utils.Utils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HServiceImpl implements HService {

  private static final String URL = "url";
  
  private final @NonNull HRepository hRepo;

  private final @NonNull HHistoryService hHisService;

  private final @NonNull HTypeService htService;

  private final @NonNull TagsService tagsService;

  private H getH(Long id) {
    return hRepo.findById(id).orElse(null);
  }

  @Override
  public H saveH(H h) {
    List<H> existingH = findAll(h, Pageable.unpaged());

    if (!Utils.isNullOrEmpty(existingH)) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, URL);
    }

    return hRepo.save(h);
  }

  @Override
  public H updateH(Long id, H h) {
    H filter = new H();
    filter.setUrl(h.getUrl());
    List<H> existingH = findAll(h, Pageable.unpaged());

    if (!Utils.isNullOrEmpty(existingH) && existingH.stream().anyMatch(exh -> !id.equals(exh.getId()))) {
      throw new HException(Errors.FIELD_SHOULD_UNIQUE, URL);
    }

    return hRepo.save(h);
  }

  @Override
  public List<H> findAll(Pageable pageable) {
    return findAll(new H(), pageable);
  }

  @Override
  public List<H> findAll(H filter, Pageable pageable) {
    return hRepo.filter(filter, pageable);
  }

  @Override
  public void deleteH(Long id) {
    hRepo.delete(this.getH(id));
  }

  @Override
  public H increaseClick(Long id) {
    H h = hRepo.getOne(id);
    h.setClicks(h.getClicks() + 1);
    H resultingH = hRepo.save(h);

    HHistory hh = HHistory.builder().h(resultingH).date(LocalDateTime.now()).build();
    hHisService.save(hh);

    return resultingH;
  }

//  /**
//   * Copy the data from the dto to the entity
//   * 
//   * @param dto
//   * @param entity
//   * @return
//   */
//  private H copyData(HDTO dto, H entity) {
//    entity.setName(Utils.isNotNullOr(dto.getName(), entity.getName()));
//    entity.setUrl(Utils.isNotNullOr(dto.getUrl(), entity.getUrl()));
//    entity.setCover(Utils.isNotNullOr(dto.getCover(), entity.getCover()));
//    entity.setScore(Utils.isNotNullOr(dto.getScore(), entity.getScore()));
//
//    BiPredicate<HTypeDTO, HType> filterType = (d, e) -> !d.getId().equals(e.getId());
//    Function<HTypeDTO, HType> processType = htService::toEntity;
//
//    HType ht = Utils.isNotNullROr(dto.getType(), entity.getType(), filterType, processType);
//    entity.setType(ht);
//
//    BiPredicate<Set<TagsDTO>, Set<Tags>> filterTags = (d, e) -> true;
//    Function<Set<TagsDTO>, Set<Tags>> processTags =
//        t -> t.stream().map(tagsService::toEntity).collect(Collectors.toSet());
//
//    Set<Tags> tags = Utils.isNotNullROr(dto.getTags(), entity.getTags(), filterTags, processTags);
//    entity.setTags(tags);
//
//    return entity;
//  }
}
