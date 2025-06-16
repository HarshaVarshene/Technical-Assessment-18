export interface Task {
  id?: number;
  title: string;
  description: string;
  updatedBy: string;
  updatedAt?: string;
  version?: number;
}