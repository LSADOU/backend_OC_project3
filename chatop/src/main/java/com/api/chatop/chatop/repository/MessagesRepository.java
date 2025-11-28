package com.api.chatop.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.chatop.chatop.model.Messages;

@Repository
public interface MessagesRepository extends CrudRepository<Messages, Long>{

}
