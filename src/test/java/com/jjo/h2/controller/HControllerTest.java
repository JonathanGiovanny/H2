package com.jjo.h2.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.jjo.h2.controller.validator.HDTOValidator;
import com.jjo.h2.dto.HDTO;
import com.jjo.h2.dto.HTypeDTO;
import com.jjo.h2.services.HService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(HController.class)
public class HControllerTest {

  private static final String BASE_PATH = "/h";

  private static final Long ID = 1L;
  private static final Integer ID_I = 1;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private HService hService;
  
  @MockBean
  private HDTOValidator hValid;

  @Before
  public void setUp() {}
//
//  @GetMapping("/{id}")
//  public ResponseEntity<HDTO> getH(@PathVariable Long id) {
//    return ResponseEntity.ok(hService.getH(id));
//  }
//
//  @Test
//  public void test_getH() throws Exception {
//    HDTO dto = new HDTO();
//    dto.set
//
//      Employee alex = new Employee("alex");
//   
//      List<Employee> allEmployees = Arrays.asList(alex);
//   
//      given(service.getAllEmployees()).willReturn(allEmployees);
//   
//      mvc.perform(get("/api/employees")
//        .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$", hasSize(1)))
//        .andExpect(jsonPath("$[0].name", is(alex.getName())));
//  }

  @Test
  public void test_findAll() throws Exception {
    // Given
    HDTO dto = new HDTO();
    dto.setId(ID);
    dto.setName("H1");
    dto.setUrl("https://");
    dto.setCover("another url");

    HTypeDTO ht = new HTypeDTO();
    ht.setId(ID_I);
    ht.setName("HT1");
    dto.setType(ht);

    List<HDTO> expectedResult = Arrays.asList(dto);

    given(hService.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(expectedResult);

    // Then
    MvcResult result = mockMvc
    .perform(get(BASE_PATH).contentType(MediaType.APPLICATION_JSON)) //
    .andExpect(status().isOk()) //
    .andReturn();

    result.getResponse().getContentAsByteArray();
  }

//  @PostMapping("/search")
//  public ResponseEntity<List<HDTO>> searchH(@Valid @RequestBody HDTO h, Pageable pageable) {
//    return ResponseEntity.ok(hService.findAll(h, pageable));
//  }
//
//  @PostMapping
//  public ResponseEntity<Void> saveH(@Valid @RequestBody HDTO h) {
//    return ResponseEntity.created(URI.create(hService.saveH(h).getId().toString())).build();
//  }
//
//  @PutMapping("/{id}")
//  public ResponseEntity<HDTO> saveH(@PathVariable Long id, @Valid HDTO h) {
//    return ResponseEntity.ok(hService.updateH(id, h));
//  }
//
//  @DeleteMapping("/{id}")
//  public ResponseEntity<Void> deleteH(@PathVariable Long id) {
//    hService.deleteH(id);
//    return ResponseEntity.noContent().build();
//  }
//
//  @PutMapping("/{id}/clicks")
//  public ResponseEntity<HDTO> increaseClick(@PathVariable Long id) {
//    return ResponseEntity.ok(hService.increaseClick(id));
//  }
}
