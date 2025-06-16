package com.Concurrency.Locking.service;

import com.Concurrency.Locking.model.Task;
import com.Concurrency.Locking.model.TaskAudit;
import com.Concurrency.Locking.repository.TaskAuditRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final TaskAuditRepository auditRepository;

    public AuditService(TaskAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void logChange(Task task) {
        TaskAudit audit = new TaskAudit();
        audit.setTaskId(task.getId());
        audit.setTitle(task.getTitle());
        audit.setDescription(task.getDescription());
        audit.setUpdatedBy(task.getUpdatedBy());
        audit.setUpdatedAt(task.getUpdatedAt());
        auditRepository.save(audit);
    }
}
