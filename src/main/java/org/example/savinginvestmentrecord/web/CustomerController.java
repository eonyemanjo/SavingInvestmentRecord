package org.example.savinginvestmentrecord.web;

import org.example.savinginvestmentrecord.entities.Customer;
import org.example.savinginvestmentrecord.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listCustomers", service.getAllCustomers());
        return "index";
    }

    @GetMapping("/showNewCustomerForm")
    public String showNewCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new_customer";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        service.addCustomer(customer);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Customer customer = service.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "update_customer";
    }

    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable(value = "id") long id) {
        service.deleteCustomer(id);
        return "redirect:/";
    }

    @GetMapping("/projectedInvestment/{id}")
    public String projectedInvestment(@PathVariable(value = "id") long id, Model model) {
        Customer customer = service.getCustomerById(id);
        double projectedValue = service.computeCompoundInterest(customer);
        model.addAttribute("customer", customer);
        model.addAttribute("projectedValue", projectedValue);
        return "projected_investment";
    }
}
