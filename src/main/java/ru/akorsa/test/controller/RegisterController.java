package ru.akorsa.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.akorsa.test.entity.User;
import ru.akorsa.test.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User constructUser() {
        return new User();
    }

    @RequestMapping
    public String showRegister() {
        return "user-register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-register";
        }
        userService.save(user);
        return "redirect:/register.html?success=true";
    }

    @RequestMapping("/available")
    @ResponseBody
    public String available(@RequestParam String username) {
        Boolean available = userService.findOne(username) == null;
        return available.toString();
    }

}
