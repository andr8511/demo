package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class StartController {

    @GetMapping("/")
    public String getNetworkInfo(HttpServletRequest request, Model model) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        List<PrintService> printers = List.of(printServices);
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            String ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            } else {
                ipAddress = ipAddress.split(",")[0].trim();
            }

            model.addAttribute("hostName", hostName);
            model.addAttribute("ipAddress", ipAddress);
            model.addAttribute("printers", printers);
        } catch (UnknownHostException e) {
            model.addAttribute("hostName", "Не удалось получить имя устройства");
            model.addAttribute("ipAddress", "Не удалось получить IP адрес");
        }
        return "index";
    }

}

