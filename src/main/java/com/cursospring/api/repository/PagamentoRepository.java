package com.cursospring.api.repository;

import com.cursospring.api.domain.Pagamento;
import com.cursospring.api.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
