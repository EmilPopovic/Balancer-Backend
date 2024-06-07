package me.emilpopovic.balancer.API;

import me.emilpopovic.balancer.EquationBalancer.EquationBalancingFailedError;
import me.emilpopovic.balancer.EquationBalancer.EquationFormatting.EquationDto;
import org.springframework.web.bind.annotation.*;
import me.emilpopovic.balancer.EquationBalancer.Balancer;

@RestController
@RequestMapping(path = "api/v1/balance")
public class BalancerController {

    @GetMapping(path = "simple")
    public String getBalancedString(
            @RequestParam() String equation
    ) throws EquationBalancingFailedError {
        return Balancer.getBalancedString(equation);
    }

    @GetMapping
    public EquationDto getBalancedEquation(
            @RequestParam() String equation
    ) throws EquationBalancingFailedError {
        return Balancer.getBalancedEquation(equation);
    }
}
