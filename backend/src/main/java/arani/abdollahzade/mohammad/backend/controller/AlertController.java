package arani.abdollahzade.mohammad.backend.controller;


import arani.abdollahzade.mohammad.backend.entity.Candle;
import arani.abdollahzade.mohammad.backend.service.CandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/candles")
public class CandleController {
    private final CandleService candleService;

    @Autowired
    public CandleController(CandleService candleService) {
        this.candleService = candleService;
    }

    @GetMapping("/getAll")
    public List<Candle> getCandles() {
        return candleService.getCandles();
    }

}
