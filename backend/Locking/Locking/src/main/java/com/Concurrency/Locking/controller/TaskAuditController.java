package com.Concurrency.Locking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Concurrency.Locking.model.TaskAudit;
import com.Concurrency.Locking.repository.TaskAuditRepository;

@RestController
@RequestMapping("/api/task-audits")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskAuditController {

    @Autowired
    private TaskAuditRepository taskAuditRepository;

    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskAudit>> getAuditLogs(@PathVariable Long taskId) {
        List<TaskAudit> audits = taskAuditRepository.findByTaskIdOrderByUpdatedAtDesc(taskId);
        return ResponseEntity.ok(audits);
    }
    
}
