package org.sample.currency.app.dao;

import org.sample.currency.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository for the {@link User} Document
 *
 * Created by Mohamed Mekkawy.
 */
public interface UserRepository extends MongoRepository<User, String> {

    @Override
    List<User> findAll();

    User findByUserName(String userName);

}
