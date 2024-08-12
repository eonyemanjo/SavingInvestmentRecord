package org.example.savinginvestmentrecord.web;

import org.example.savinginvestmentrecord.entities.Customer;
import org.example.savinginvestmentrecord.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private Model model;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setCustomerNumber("12345");
        customer.setName("John Doe");
        customer.setInitialDeposit(1000.0);
        customer.setNumberOfYears(5);
        customer.setSavingsType("Savings De-luxe");
    }

    @Test
    void viewHomePage() {
        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        String viewName = customerController.viewHomePage(model);

        assertEquals("index", viewName);
        verify(model).addAttribute(eq("listCustomers"), anyList());
    }

    @Test
    void showNewCustomerForm() {
        String viewName = customerController.showNewCustomerForm(model);

        assertEquals("new_customer", viewName);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void saveCustomer() {
        String viewName = customerController.saveCustomer(customer);

        assertEquals("redirect:/", viewName);
        verify(customerService).addCustomer(customer);
    }

    @Test
    void showFormForUpdate() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        String viewName = customerController.showFormForUpdate(1L, model);

        assertEquals("update_customer", viewName);
        verify(model).addAttribute(eq("customer"), any(Customer.class));
    }

    @Test
    void deleteCustomer() {
        String viewName = customerController.deleteCustomer(1L);

        assertEquals("redirect:/", viewName);
        verify(customerService).deleteCustomer(1L);
    }

    @Test
    void projectedInvestment() {
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(customerService.computeCompoundInterest(customer)).thenReturn(2000.0);

        String viewName = customerController.projectedInvestment(1L, model);

        assertEquals("projected_investment", viewName);
        verify(model).addAttribute(eq("projectedValue"), eq(2000.0));
    }
}
