package com.rishi.invoicegeneratorapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rishi.invoicegeneratorapi.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByClerkId(String clerkId);
    
    boolean existsByClerkId(String clerkId);
}
