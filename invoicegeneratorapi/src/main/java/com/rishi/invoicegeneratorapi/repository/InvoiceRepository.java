package com.rishi.invoicegeneratorapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rishi.invoicegeneratorapi.entity.Invoice;
import java.util.List;
import java.util.Optional;


public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    List<Invoice> findByClerkId(String clerkId);

    Optional<Invoice> findByClerkIdAndId(String clerkId, String id);
}
