package com.financialmonitoring.transactionservice.infra.repository.jpa;

import com.financialmonitoring.transactionservice.infra.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends MongoRepository<Event, Long> {

}
