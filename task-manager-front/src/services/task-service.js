import api from './api-config';

class TaskService {

    getAllTasks() {
        return api.get('/task-manager/api/v1/tasks');
    }

    deleteTask(id) {
        return api.delete('/task-manager/api/v1/tasks/' + id);
    }

    getTaskById(id) {
        return api.get('/task-manager/api/v1/tasks/' + id);
    }

    updateTask(task) {
        const data = {
            title: task.title,
            description: task.description,
            taskTime: task.taskTime
        }
        return api.put('/task-manager/api/v1/tasks/'+task.id, data);
    }


}

export default new TaskService();
