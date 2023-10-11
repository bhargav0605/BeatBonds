package io.beatbonds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.beatbonds.model.Investment;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long>{

}
