package com.pickle.web.grades;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MockRepository extends JpaRepository<Mock, Long>, IMockRepository {
}
