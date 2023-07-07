package com.nttdata.service;


import com.nttdata.entity.AccountHistory;
import com.nttdata.repository.AccountHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
public class AccountHistoryService {

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    /**
     * Metodo obtiene la informacion de la entidades AccountHistory
     * @return retorna un objeto de la entidad AccountHistory
     */
    public Flux<AccountHistory> getAll(){
        return accountHistoryRepository.findAll();
    }
    /**
     * Metodo que guarda la informacion de la entidad AccountHistory
     * @param accountHistory Objeto de la entidad AccountHistory
     * @return retorna el objeto insertado o actualizado
     */
    /*Metodo usado para la validacion de informacion y guardado de la entidad en la base de datos*/
    public Mono<AccountHistory> save(AccountHistory accountHistory) {
        AccountHistory newAccountHistory = new AccountHistory();
        accountHistory.setId(sequenceGeneratorService.getSequenceNumber(AccountHistory.SEQUENCE_NAME));
        try {
            newAccountHistory = accountHistoryRepository.save(accountHistory).toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(newAccountHistory);
    }

}
