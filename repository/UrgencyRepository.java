package fr.m2i.tpSpring.repository;

import fr.m2i.tpSpring.model.Urgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrgencyRepository extends JpaRepository<Urgency, Integer> {

}