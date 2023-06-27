package com.nttdata.service;

import com.nttdata.model.DatabaseSequence;
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

@Service
public class SequenceGeneratorService {
    @Autowired
    private ReactiveMongoOperations reactiveMongoOperations;

    public long getSequenceNumber(String sequenceName) {
        try {
        Mono<DatabaseSequence> counter = reactiveMongoOperations.findAndModify(query(where("_id").is(sequenceName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);

            return !Objects.isNull(counter) ? counter.toFuture().get().getSeq() : 1;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
