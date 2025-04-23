package com.financialmonitoring.transactionservice.core.repository;

import com.financialmonitoring.transactionservice.core.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {

}
