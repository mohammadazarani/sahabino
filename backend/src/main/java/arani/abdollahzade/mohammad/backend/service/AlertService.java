package arani.abdollahzade.mohammad.backend.service;

import arani.abdollahzade.mohammad.backend.entity.Alert;
import arani.abdollahzade.mohammad.backend.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getAlerts(){
        return alertRepository.findAll();
    }
}
