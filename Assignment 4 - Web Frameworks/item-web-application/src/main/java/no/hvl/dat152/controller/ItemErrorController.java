package no.hvl.dat152.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ItemErrorController implements ErrorController {

    @RequestMapping(path = "/error")
    public String handleError(RedirectAttributes redirectAttrs){

        String msg = "An error occurred. Please try again.";
        redirectAttrs.addFlashAttribute("errormsg", msg);
        return "redirect:viewitems";
    }
}
