import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../services/task.service';
import { HttpErrorResponse } from '@angular/common/http';

import { Task } from '../models/task';
import { TaskAudit } from '../models/task-audit';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css'],
  providers: [TaskService]
})
export class TaskComponent implements OnInit {
  tasks: Task[] = [];
  task: Task = this.emptyTask();
  auditLogs: TaskAudit[] = [];

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getAll().subscribe(data => {
      this.tasks = data;
    });
  }

  save(): void {
    this.taskService.save(this.task).subscribe({
      next: (savedTask) => {
        this.task = savedTask;
        this.loadTasks(); 
        this.loadAuditLogs(savedTask.id!); 
      },
      error: (error) => {
        console.error('Save failed:', error);
        alert('Save failed: ' + error.message);
      }
    });
  }

  edit(task: Task): void {
    this.task = { ...task };

    if (task.id !== undefined) {
      this.loadAuditLogs(task.id); 
    } else {
      this.auditLogs = [];
    }
  }

  loadAuditLogs(taskId: number): void {
    this.taskService.getAuditLogs(taskId).subscribe({
      next: (logs) => {
        this.auditLogs = logs;
      },
      error: (err) => {
        console.error('Failed to load audit logs', err);
        this.auditLogs = [];
      }
    });
  }

  clear(): void {
    this.task = this.emptyTask();
    this.auditLogs = [];
  }

  private emptyTask(): Task {
    return {
      id: 0,
      title: '',
      description: '',
      updatedBy: '',
      updatedAt: '',
      version: 0
    };
  }
}
