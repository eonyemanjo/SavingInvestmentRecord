package org.example.savinginvestmentrecord.service;

import org.example.savinginvestmentrecord.entities.Customer;
import org.example.savinginvestmentrecord.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public void addCustomer(Customer customer) {
        Optional<Customer> existingCustomer = repository.findByCustomerNumber(customer.getCustomerNumber());

        if (existingCustomer.isPresent() && !existingCustomer.get().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("Customer number already exists");
        }

        repository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public void updateCustomer(Customer customer) {
        addCustomer(customer);  // This now uses the addCustomer logic
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }

    public double computeCompoundInterest(Customer customer) {
        double rate = customer.getSavingsType().equalsIgnoreCase("Savings De-luxe") ? 0.15 : 0.10;
        return customer.getInitialDeposit() * Math.pow(1 + rate, customer.getNumberOfYears());
    }
}
