package com.nttdata.service;

import com.nttdata.entity.DocumentType;
import com.nttdata.repository.DocumentTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService {

  private DocumentTypeRepository documentTypeRepository;
  final Logger logger = LoggerFactory.getLogger(DocumentType.class);

  @Cacheable(value = "userCache")
  public DocumentType getTypeDocumentById(Long id) {
    logger.info("Getting typeDocument with ID {}.", id);
    return documentTypeRepository.findById(id)
        .orElseThrow(RuntimeException::new);
  }

  public DocumentType getTypeDocById(Long id) {
    Optional<DocumentType> optional = documentTypeRepository.findById(id);
    return optional.get();
  }

  public DocumentType save(DocumentType documentType){
    return documentTypeRepository.save(documentType);
  }

  public List<DocumentType> getAll() {
    return (List<DocumentType>) documentTypeRepository.findAll().iterator();
  }
}
