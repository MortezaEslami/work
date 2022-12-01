<template>
  <div>
    <div class="p-modal" v-if="isUpdateMode">
      <div class="row-center">
        <div class="col-md-4 col-sm-12">
          <button class="btn btn-dark btn-close-modal" @click="hideModal()">X</button>
        </div>
      </div>
      <UpdateTask @updatedTask="loadUpdatedRecord" :taskId="this.currentTaskId"/>
    </div>
    <div class="col-12" style="height: 30px">
      <div class="float-left col-6 col-sm-6" style="height: 40px">
        <button v-if="isAdminUser" class="btn btn-primary" @click="openNewTask">{{ $t('btn.create') }}</button>
      </div>
      <div class="float-right col-6 col-sm-6" style="height: 20px">
        <div class="col-2 float-left">
          <label for="taskId" style="font-weight: bold">
            {{ $t('tbl.id') }}
          </label>
        </div>
        <div class="col-8 float-left">
          <input
              type="text"
              class="form-control"
              name="taskId"
              id="taskId"
          />
        </div>
        <div class="col-2 float-right">
          <button class="btn btn-info" @click="search"> {{ $t('btn.search') }}</button>
        </div>

      </div>
    </div>
    <div class="col-12 mt-3">
      <table class="table">
        <thead>
        <tr>
          <th>
            {{ $t('tbl.id') }}
          </th>
          <th>
            {{ $t('tbl.title') }}
          </th>
          <th>
            {{ $t('tbl.description') }}
          </th>
          <th>
            {{ $t('tbl.taskTime') }}
          </th>
          <th>
            {{ $t('tbl.createdDate') }}
          </th>
          <th>
            {{ $t('tbl.createdBy') }}
          </th>
          <th v-if="isAdminUser">
            {{ $t('tbl.action') }}
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(task,index) in tasks" :key="index">
          <td>{{ task.id }}</td>
          <td>
            <span v-if="task.title === null"> - </span>
            <span v-if="task.title !== null"> {{ task.title }} </span>
          </td>
          <td>
            <span v-if="task.description === null"> - </span>
            <span v-if="task.description !== null"> {{ task.description }} </span>
          </td>
          <td>
            <span v-if="task.taskTime === null"> - </span>
            <span v-if="task.taskTime !== null"> {{ task.taskTime }} </span>
          </td>
          <td>
            <span v-if="task.createdDate === null"> - </span>
            <span v-if="task.createdDate !== null"> {{ (task.createdDate).replace('T', ' ').split('.')[0] }} </span>
          </td>
          <td>
            <span v-if="task.createdBy === null"> - </span>
            <span v-if="task.createdBy !== null"> {{ task.createdBy }} </span>
          </td>
          <td v-if="isAdminUser">
            <button class="btn btn-sm m-1 btn-outline-danger" @click="deleteTask(task, index)"
                    :disabled="loading && indexClicked === index">
              <span v-show="loading && indexClicked === index" class="spinner-border spinner-border-sm"></span>
              <span>
                {{ $t('btn.delete') }}
              </span>
            </button>
            <button class="btn btn-sm m-1 btn-outline-warning" @click="editTask(task)">
              <span>
                {{ $t('btn.edit') }}
              </span>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
      <ErrorPartial v-if="message" :text="message"/>
    </div>
  </div>
</template>

<script>
import TaskService from '../../services/task-service';
import UpdateTask from "@/components/task/UpdateTask";
import ErrorPartial from "@/components/error/ErrorPartial";
import EventBus from "@/common/EventBus";
import {i18n} from "@/i18n";

export default {
  name: 'Tasks',
  components: {ErrorPartial, UpdateTask},
  data() {
    return {
      tasks: {},
      message: '',
      loading: false,
      indexClicked: NaN,
      isUpdateMode: false,
      currentTaskId: 0
    };
  },
  mounted() {
    if (this.isAdminUser)
      this.getTasks();

  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    isAdminUser() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes('quality-manager');
      }
      return false;
    }
  },
  methods: {
    getTasks() {
      this.loading = false;
      TaskService.getAllTasks().then(
          response => {
            this.tasks = response.data;
          },
          error => {
            if (error.response === undefined)
              this.message =
                  (error.response && error.response.data && error.response.data.message) ||
                  error.message ||
                  error.toString();
          }
      );
    },
    search() {
      this.loading = false;
      TaskService.getTaskById(document.getElementById('taskId').value).then(
          response => {
            this.tasks = new Array(response.data);
          },
          error => {
            if (error.response === undefined)
              this.message =
                  (error.response && error.response.data && error.response.data.message) ||
                  error.message ||
                  error.toString();
          }
      );
    },
    deleteTask(_task, _index) {
      this.indexClicked = _index;
      this.loading = true;
      this.message = '';
      TaskService.deleteTask(_task.id).then(
          () => {
            this.getTasks();
          },
          error => {
            this.loading = false;
            this.$toasted.show(
                (error.response && error.response.data && error.response.data.message) ||
                error.message ||
                error.toString()
            );
            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
      );
    },
    openNewTask() {
      this.$router.push('/' + i18n.locale + "/create");
    },
    editTask(_task) {
      this.currentTaskId = _task.id;
      this.isUpdateMode = true;
    },
    loadUpdatedRecord(_updatedTask) {
      this.isUpdateMode = false;
      for (let i = 0; i < this.tasks.length; i++) {
        if (this.tasks[i].id === _updatedTask.id) {
          this.tasks[i].title = _updatedTask.title;
          this.tasks[i].description = _updatedTask.description;
          this.tasks[i].taskTime = _updatedTask.taskTime;
        }
      }
    },
    hideModal() {
      this.isUpdateMode = false;
    }
  }
};
</script>

