package com.rishi.invoicegeneratorapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.rishi.invoicegeneratorapi.entity.Invoice;
import com.rishi.invoicegeneratorapi.service.EmailService;
import com.rishi.invoicegeneratorapi.service.InvoiceService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@RestController 
@RequiredArgsConstructor
@RequestMapping("/api/invoices")
@Slf4j
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final EmailService emailService;

    @PostMapping("")
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice){
        return ResponseEntity.ok(invoiceService.saveInvoice(invoice));
    }

    @GetMapping("")
    public ResponseEntity<List<Invoice>> fetchInvoices(Authentication authentication) {
        return ResponseEntity.ok(invoiceService.fetchInvoices(authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeInvoice(@PathVariable String id, Authentication authentication){
        if(authentication.getName()!=null){
            invoiceService.removeInvoice(id, authentication.getName());
            return ResponseEntity.noContent().build();
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to acces this resource");
    }

    @PostMapping("/sendInvoice")  
    public ResponseEntity<?> sendInvoice( @RequestPart("file") MultipartFile file, 
                                          @RequestParam("email") String customerEmail){
        
        try{
            emailService.sendInvoiceEmail(customerEmail, file);
            return ResponseEntity.ok("Invoice sent successfully");
        } catch(Exception e){
            log.error("Failed to send invoice", e); // optional 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send invoice");
        }

                                         
    }
    


}
