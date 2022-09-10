package com.devonfw.java.training.asynchronous.mvc;

import com.devonfw.java.training.asynchronous.entity.Pi;
import com.devonfw.java.training.asynchronous.service.PiMultiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("mvc")
public class PiController {

    @Autowired
    private PiMultiService piService;

    @GetMapping("pi")
    public String pi(
            @RequestParam(name = "timeToComputeInSeconds", required = false, defaultValue = "0") int timeToComputeInSeconds,
            @RequestParam(name = "numberOfProbes", required = false, defaultValue = "0") int numberOfProbes,
            Model model) {
        List<Pi> pis = piService.computeMultiPis(timeToComputeInSeconds, numberOfProbes);
        model.addAttribute("pis", pis);
        model.addAttribute("timeToComputeInSeconds", timeToComputeInSeconds);
        model.addAttribute("numberOfProbes", numberOfProbes);
        return "pi";
    }
}
