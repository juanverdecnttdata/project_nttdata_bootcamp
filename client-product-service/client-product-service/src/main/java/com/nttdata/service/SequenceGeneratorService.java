package com.nttdata.service;

import com.nttdata.entity.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Servicio de la entidad Sequence, sirve para generar el id automatico de las colecciones
 */
@Service
public class SequenceGeneratorService {
  @Autowired
  private ReactiveMongoOperations reactiveMongoOperations;

  /**
   * Metodo que genera y obtiene un codigo correlativo de la coleccion indicada
   *
   * @param sequenceName Nombre de la secuencia
   * @return retorna un numero secuencial correspondiente a la secuencia indicada
   */
  public long getSequenceNumber(String sequenceName) {
    try {
      Mono<DatabaseSequence> counter = reactiveMongoOperations.findAndModify(query(where("_id").is(sequenceName)),
          new Update().inc("seq", 1), options().returnNew(true).upsert(true),
          DatabaseSequence.class);
      return !Objects.isNull(counter) ? counter.toFuture().get().getSeq() : 1;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
