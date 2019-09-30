package com.jjo.h2.controller;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jjo.h2.controller.validator.HDTOValidator;
import com.jjo.h2.mapper.HMapper;
import com.jjo.h2.model.H;
import com.jjo.h2.services.HService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(HController.class)
public class HControllerTest {

  private static final Long ID = 1L;

  private HController hController;

  @MockBean
  private HService hService;

  @MockBean
  private HDTOValidator hValid;

  @Mock
  private HMapper mapper;

  public HControllerTest() {
    this.hController = new HController(hService, hValid, mapper);
  }

  @Before
  public void setUp() {}

  @Test
  public void testFindById() {
    Mockito.when(hService.findById(ID)).thenReturn(Optional.of(buildH(ID)));

    hController.findById(ID);

    Mockito.verify(hService, Mockito.times(1)).findById(ID);
  }

  @Test
  public void test_findAll() throws Exception {}

  private H buildH(Long id) {
    return H.builder().id(id).build();
  }
}
