import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Task } from '../models/task';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TaskAudit } from '../models/task-audit';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

  getTask(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, task);
  }

  updateTask(task: Task): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${task.id}`, task).pipe(
      catchError(this.handleError)
    );
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getHistory(id: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/${id}/history`);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 409) {
      return throwError(() => new Error("Version conflict"));
    }
    return throwError(() => new Error("An error occurred"));
  }
   getAll(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

 save(task: Task): Observable<Task> {
  return this.http.post<Task>(this.apiUrl, task);
}
getAuditLogs(taskId: number): Observable<TaskAudit[]> {
  return this.http.get<TaskAudit[]>(`http://localhost:8080/api/tasks/${taskId}/history`);
}

}
