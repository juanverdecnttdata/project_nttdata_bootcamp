package com.nttdata.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Entidad de secuencia, usado para generar los id de las colecciones
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "database_sequences")
public class DatabaseSequence implements Serializable {
    @Id
    private String _id;
    private long seq;
}
