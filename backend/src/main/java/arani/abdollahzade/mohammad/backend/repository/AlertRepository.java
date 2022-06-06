package arani.abdollahzade.mohammad.backend.repository;

import arani.abdollahzade.mohammad.backend.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

}
