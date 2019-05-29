package com.jjo.h2.dto.security;

import java.sql.Blob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SingUpDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  @Email
  private String email;

  private Blob profilePic;
}
