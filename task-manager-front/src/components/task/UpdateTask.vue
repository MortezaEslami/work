<template>
  <div class="row-center">
    <div class="col-md-4 col-sm-12">
      <div class="card card-container">
        <div class="alert alert-warning">

          {{$t('you-are-editing-task')}}</div>
        <form name="form" @submit.prevent="handleUpdateTask">
          <div class="form-group form-validate-input">
            <label for="title">
              {{$t('title')}}
            </label>
            <input
                v-model="task.title"
                v-validate="'required|min:3|max:20'"
                type="text"
                class="form-control"
                name="title"
                id="title"
            />
            <div v-if="submitted && errors.has('title')" class="alert-danger">{{ errors.first('title') }}</div>
          </div>
          <div class="form-group form-validate-input">
            <label for="description">
              {{$t('description')}}
            </label>
            <input
                v-model="task.description"
                v-validate="'required|min:3|max:50'"
                type="text"
                class="form-control"
                name="description"
                id="description"
            />
            <div v-if="submitted && errors.has('description')" class="alert-danger">{{ errors.first('description') }}</div>
          </div>
          <div class="form-group form-validate-input">
            <label for="taskTime">
              {{$t('taskTime')}}
            </label>
            <input
                v-model="task.taskTime"
                type="text"
                class="form-control"
                name="taskTime"
                id="taskTime"
            />
            <div v-if="submitted && errors.has('taskTime')" class="alert-danger">{{ errors.first('taskTime') }}</div>
          </div>
          <div class="form-group text-center">
<!--            <button class="btn btn-link m-1" @click='resetForm' type="reset">
              {{$t('btn.reset')}}
            </button>-->
            <button class="btn btn-warning" :disabled="loading">
              <span v-show="loading" class="spinner-border spinner-border-sm"></span>
              <span>
                {{$t('btn.update')}}
              </span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import TaskService from "@/services/task-service";
import EventBus from "@/common/EventBus";

export default {
  name: "UpdateTask",
  props: ['taskId'],
  data() {
    return {
      submitted: false,
      successful: false,
      loading: false,
      task: {}
    };
  },
  computed: {},
  mounted() {
    this.getTaskById();
  },
  methods: {
    getTaskById() {
      TaskService.getTaskById(this.taskId).then(
          res => {
            this.task = res.data
          },
          error => {
            this.successful = false;
            this.message =
                (error.response && error.response.data && error.response.data.message) ||
                error.message ||
                error.toString();
            if (error.response && error.response.status === 403) {
              EventBus.dispatch("logout");
            }
          }
      );
    },
    handleUpdateTask() {
      this.message = '';
      this.submitted = true;
      this.loading = true;
      this.$validator.validate().then(isValid => {
        if (isValid) {
          TaskService.updateTask(this.task).then(
              res => {
                this.successful = true;
                this.$emit('updatedTask', this.task);
                this.$toasted.show(res.data.message);
              },
              error => {
                this.successful = false;
                this.loading = false;
                this.message =
                    (error.response && error.response.data && error.response.data.message) ||
                    error.message ||
                    error.toString();
              }
          );
        }
      });
    }
  }
};
</script>

}
