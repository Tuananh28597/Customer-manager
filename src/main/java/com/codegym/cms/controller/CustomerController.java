package com.codegym.cms.controller;


import com.codegym.cms.model.Customer;
import com.codegym.cms.model.CustomerForm;
import com.codegym.cms.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Value("${file-upload}")
    private String fileUpload;


    @GetMapping
    public ModelAndView showAll(@RequestParam(name="q",required = false) String name,
                                @PageableDefault(size=5) Pageable pageable){
        Page<Customer> customers ;
        ModelAndView modelAndView = new ModelAndView("/customer/list");
       if (name==null){
           customers = customerService.findAll(pageable);
       }else{
           customers = customerService.findByNameContaining(name, pageable);
       }
       modelAndView.addObject("customers", customers);

       return  modelAndView;

    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customerForm", new CustomerForm());
        return modelAndView;
    }


    @PostMapping("/create-customer")
    public String saveCustomer(@Validated @ModelAttribute(name = "customerForm") CustomerForm customerForm,Model model,
                                     BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            model.addAttribute("customerForm", customerForm);
            return "/customer/create";
        }
        MultipartFile multipartFile = customerForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(customerForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer(customerForm.getId(), customerForm.getName(), customerForm.getAge(),fileName);
        customerService.save(customer);
        model.addAttribute("message", "Created");
        return "redirect:/customer";


    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer",customer);
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }


    @PostMapping("/edit-customer")
    public ModelAndView editCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message","Updated");
        return modelAndView;

    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer",customer);
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("customers") Customer customer){
        customerService.remove(customer.getId());
        return "redirect:/customer";
    }










}
