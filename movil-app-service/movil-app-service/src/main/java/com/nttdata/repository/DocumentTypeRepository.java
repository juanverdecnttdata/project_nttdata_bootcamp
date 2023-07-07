package com.nttdata.repository;

import com.nttdata.entity.DocumentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class repository of the User.
 */
@Repository
public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {


}
