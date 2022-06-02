package arani.abdollahzade.mohammad.backend.service;

import arani.abdollahzade.mohammad.backend.entity.Candle;
import arani.abdollahzade.mohammad.backend.repository.CandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandleService {
    private final CandleRepository candleRepository;

    @Autowired
    public CandleService(CandleRepository candleRepository) {
        this.candleRepository = candleRepository;
    }

    public List<Candle> getCandles(){
        return candleRepository.findAll();
    }
}
