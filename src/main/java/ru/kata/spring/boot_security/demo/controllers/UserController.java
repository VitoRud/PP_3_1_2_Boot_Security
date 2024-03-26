package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping(value = "/")
    public String printWelcome() {
        return "index";
    }

    @GetMapping(value = "/admin")
    public String printWelcome(ModelMap model) {
        List<User> users = us.userList();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping(value = "/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("userPrincipal", principal);
        return "user";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        }

        us.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam long id) {
        us.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String pageUpdateUser(@RequestParam int id, Model model) {
        model.addAttribute("user", us.getOneUser(id));
        return "update";
    }

    @PostMapping("/update")
    public String UpdateUser(@ModelAttribute("user") @Valid User userUpd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        us.updateUser(userUpd);
        return "redirect:/admin";
    }
}
