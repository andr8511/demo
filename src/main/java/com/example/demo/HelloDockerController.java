package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.Arrays;

@RestController
@RequestMapping("/api/print")
public class HelloDockerController {

    @GetMapping("/services")
    public ResponseEntity<String> hello() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        if (printServices.length == 0) {
            return ResponseEntity.ok("No print services available.");
        }
        return ResponseEntity.ok(Arrays.toString(printServices));
    }
}
