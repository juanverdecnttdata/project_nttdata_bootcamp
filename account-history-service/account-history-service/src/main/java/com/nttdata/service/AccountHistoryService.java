package com.nttdata.service;


import com.nttdata.entity.AccountHistory;
import com.nttdata.entity.Message;
import com.nttdata.repository.AccountHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AccountHistoryService {

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

    /**
     * Metodo obtiene la informacion de la entidades AccountHistory
     * @return retorna un objeto de la entidad AccountHistory
     */
    public List<AccountHistory> getAll(){
        return accountHistoryRepository.findAll();
    }
    /**
     * Metodo que guarda la informacion de la entidad AccountHistory
     * @param accountHistory Objeto de la entidad AccountHistory
     * @return retorna el objeto insertado o actualizado
     */
    /*Metodo usado para la validacion de informacion y guardado de la entidad en la base de datos*/
    public AccountHistory save(AccountHistory accountHistory) {
        return accountHistoryRepository.save(accountHistory);
    }

}
