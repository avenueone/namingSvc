package org.avenue1.naming.repository;

import org.avenue1.naming.domain.Names;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Names entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NamesRepository extends MongoRepository<Names, String> {

}
