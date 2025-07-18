package com.rishi.invoicegeneratorapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rishi.invoicegeneratorapi.entity.Invoice;
import com.rishi.invoicegeneratorapi.repository.InvoiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository; 

    public Invoice saveInvoice(Invoice invoice){
        //log.info("Saving invoice: {}", invoice);
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> fetchInvoices(String clerkId){
        return invoiceRepository.findByClerkId(clerkId);
    }

    public void removeInvoice(String invoiceId, String clerkId){
        Invoice existingInvoice = invoiceRepository.findByClerkIdAndId(clerkId, invoiceId)
               .orElseThrow(()-> new RuntimeException("Invoice not found: "+invoiceId));
        invoiceRepository.delete(existingInvoice);
    }
}
