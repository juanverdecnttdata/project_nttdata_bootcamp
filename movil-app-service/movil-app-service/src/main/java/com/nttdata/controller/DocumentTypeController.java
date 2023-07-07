package com.nttdata.controller;

import com.nttdata.entity.DocumentType;
import com.nttdata.entity.Message;
import com.nttdata.entity.User;
import com.nttdata.service.DocumentTypeService;
import com.nttdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/documentType")
public class DocumentTypeController {
  @Autowired
  private DocumentTypeService documentTypeService;

  @GetMapping("/getDocumentTypeById/{id}")
  public DocumentType getClientProductById(@PathVariable("id") Long id){
    return documentTypeService.getTypeDocumentById(id);
  }

  @PostMapping("/insertDataREDIS")
  public Message insertDataREDIS(){
    DocumentType documentType = new DocumentType(1L, "DNI");
    documentTypeService.save(documentType);
    return new Message("001", "data created");
  }



}
