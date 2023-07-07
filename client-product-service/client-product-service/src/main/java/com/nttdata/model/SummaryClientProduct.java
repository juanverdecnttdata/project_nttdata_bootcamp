package com.nttdata.model;

import com.nttdata.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entidad Client de la tabla client
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryClientProduct implements Serializable {

    private Long id_client;
    private List<Product> products;
    @Transient
    private Message message;
}