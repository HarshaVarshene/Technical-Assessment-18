package com.Concurrency.Locking.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.Concurrency.Locking.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
