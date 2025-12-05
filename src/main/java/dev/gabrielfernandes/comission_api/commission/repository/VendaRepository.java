package dev.gabrielfernandes.comission_api.commission.repository;

import dev.gabrielfernandes.comission_api.commission.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByVendedor(String vendedor);
}
