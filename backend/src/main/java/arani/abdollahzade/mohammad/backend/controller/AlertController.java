package arani.abdollahzade.mohammad.backend.controller;


import arani.abdollahzade.mohammad.backend.entity.Alert;
import arani.abdollahzade.mohammad.backend.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/alerts")
public class AlertController {
    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/getAll")
    public List<Alert> getAlerts() {
        return alertService.getAlerts();
    }

}
