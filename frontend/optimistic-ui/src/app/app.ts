import { Component } from '@angular/core';
import { TaskComponent } from './components/task.component'; // or './components/task' if you renamed it
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './app.html'
})
export class AppComponent {}
