package com.nttdata.entity;



import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad Person de la tabla person
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "person")
public class Person implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "person_sequence";

    @Id
    private Long id;
    private String first_name;
    private String last_name;
    private Integer age;
    private Integer gender;
    private Integer status;
    private Date start_date;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    @Transient
    private Message message;

}
