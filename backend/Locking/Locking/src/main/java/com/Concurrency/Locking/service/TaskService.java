package com.Concurrency.Locking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.Concurrency.Locking.model.Task;
import com.Concurrency.Locking.model.TaskAudit;
import com.Concurrency.Locking.repository.TaskAuditRepository;
import com.Concurrency.Locking.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskAuditRepository taskAuditRepository;
    private final AuditService auditService;

    public TaskService(TaskRepository taskRepository, TaskAuditRepository taskAuditRepository,AuditService auditService) {
        this.taskRepository = taskRepository;
        this.taskAuditRepository = taskAuditRepository;
        this.auditService=auditService;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            task.setDescription("N/A");
        }

        task.setUpdatedBy("Harsha"); // Replace with actual user logic later
        task.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        auditService.logChange(saved);

        return saved;
    }
    public Task updateTask(Long id, Task updatedTask) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        if (existing.getVersion() != updatedTask.getVersion()) {
            throw new OptimisticLockingFailureException("Version conflict");
        }

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setUpdatedBy(updatedTask.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(existing);
        saveAudit(saved);
        return saved;
    }

    private void saveAudit(Task task) {
        TaskAudit audit = new TaskAudit(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getUpdatedBy(),
                task.getUpdatedAt()
        );
        taskAuditRepository.save(audit);
    }

    public List<TaskAudit> getAuditHistory(Long taskId) {
        return taskAuditRepository.findByTaskIdOrderByUpdatedAtDesc(taskId);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public Task saveTask(Task task) {
        Task saved = taskRepository.save(task);
        auditService.logChange(saved); // Create audit log
        return saved;
    }

}