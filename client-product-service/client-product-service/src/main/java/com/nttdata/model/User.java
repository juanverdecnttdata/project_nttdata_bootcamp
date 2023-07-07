package com.nttdata.model;

import com.nttdata.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
  @Transient
  public static final String SEQUENCE_NAME = "user_sequence";
  @Id
  private Long id;
  private String identificationNumber;
  private String identificationType;
  private String cellNumber;
  private String imeiNumber;
  private String email;
  @Transient
  private Message message;
}
