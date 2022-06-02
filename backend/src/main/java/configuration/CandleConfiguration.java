package configuration;

import arani.abdollahzade.mohammad.backend.entity.Candle;
import arani.abdollahzade.mohammad.backend.repository.CandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class CandleConfiguration {

    private final CandleRepository candleRepository;

    @Autowired
    public CandleConfiguration(CandleRepository candleRepository) {
        this.candleRepository = candleRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(CandleRepository repository) {
        return args -> {
            Candle candle1 = Candle.build("open1", "high1", "low1", "close1");
            Candle candle2 = Candle.build("open1", "high1", "low1", "close1");
            repository.saveAll(Arrays.asList(candle1, candle2));
        };
    }
}
