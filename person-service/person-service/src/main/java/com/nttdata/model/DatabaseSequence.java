package com.nttdata.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "database_sequences")
public class DatabaseSequence {
    @Id
    private String _id;

    private long seq;
}
