package ir.sahabino.rules_evaluator.repository;
import ir.sahabino.rules_evaluator.entity.Candle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandleRepository extends JpaRepository<Candle, Long>  {
}
