package com.nttdata.model;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nttdata.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
/**
 * Entidad Client de la tabla client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Client {
    @Id
    private Long id;
    private Long id_person;
    private Integer status;
    private Long id_client_type;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    @Transient
    private Message message;

}