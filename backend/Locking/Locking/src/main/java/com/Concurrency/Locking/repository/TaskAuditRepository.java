package com.Concurrency.Locking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Concurrency.Locking.model.TaskAudit;

public interface TaskAuditRepository extends JpaRepository<TaskAudit, Long> {
    List<TaskAudit> findByTaskIdOrderByUpdatedAtDesc(Long taskId);
}