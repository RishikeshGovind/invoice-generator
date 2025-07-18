package com.rishi.invoicegeneratorapi.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection="invoices")
public class Invoice {

    @Id
    private String id;

    private String clerkId;

    private Company company;

    private Shipping shipping;

    private InvoiceDetails invoiceDetails;

    private List<Item> items;

    private String notes;

    private String logo;

    private double tax;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant lastUpdatedAt;

    @JsonProperty("thumbnailUrl")
    private String thumbnail;

    private String template;

    private String title;

    @Data
    public static class Company{
        private String name;
        private String phone;
        private String address;
    }

    @Data
    public static class Billing{
        private String name;
        private String phone;
        private String address;
    }

    @Data
    public static class Shipping{
        private String name;
        private String phone;
        private String address;
    }

    @Data
    public static class InvoiceDetails{
        private String name;
        private String phone;
        private String dueDate;
    }

    @Data
    public static class Item{
        private String name;
        private int qty;
        private double amount;
        private String description;
    }

}
