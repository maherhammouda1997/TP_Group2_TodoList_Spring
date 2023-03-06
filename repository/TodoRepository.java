package fr.m2i.tpSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.m2i.tpSpring.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}

