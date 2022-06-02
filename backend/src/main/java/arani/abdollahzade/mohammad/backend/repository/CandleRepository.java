package arani.abdollahzade.mohammad.backend.repository;

import arani.abdollahzade.mohammad.backend.entity.Candle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandleRepository extends JpaRepository<Candle, Long> {

}
