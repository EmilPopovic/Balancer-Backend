package me.emilpopovic.balancer.API;

import me.emilpopovic.balancer.EquationBalancer.EquationBalancingFailedError;
import org.springframework.web.bind.annotation.*;
import me.emilpopovic.balancer.EquationBalancer.Balancer;

import java.util.Map;

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
    public Map<String, Map<String, Integer>> getBalancedEquation(
            @RequestParam() String equation
    ) throws EquationBalancingFailedError {
        return Balancer.getBalancedMap(equation);
    }

}
