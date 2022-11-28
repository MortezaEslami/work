<template>
  <div class="row-center">
    <div class="col-md-5 col-sm-12">
      <div class="card card-container">
        <img class="card-logo" src="../assets/images/logo.png"/>
        <form name="form" @submit.prevent="handleCreate">
          <div v-if="!successful">
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
                  v-validate="'required|max:50'"
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
                  v-validate="'required'"
                  type="number"
                  class="form-control"
                  name="taskTime"
                  id="taskTime"
              />
              <div v-if="submitted && errors.has('taskTime')" class="alert-danger">{{ errors.first('taskTime') }}</div>
            </div>
            <div class="form-group text-center">
              <button class="btn btn-success">
                {{$t('btn.save')}}
              </button>
            </div>
          </div>
        </form>
        <div v-if="message" class="alert" :class="successful ? 'alert-success' : 'alert-danger'">{{ message }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import NewTask from '../models/new-task';
import {i18n} from "@/i18n";

export default {
  name: 'Create',
  data() {
    return {
      task: new NewTask('', '', ''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  methods: {
    handleCreate() {
      this.message = '';
      this.submitted = true;
      this.$validator.validate().then(isValid => {
        if (isValid) {
          this.$store.dispatch('auth/create', this.task).then(
              () => {
                this.$router.push('/' + i18n.locale +"/taskList");
              },
              error => {
                this.message =
                    (error.response && error.response.data && error.response.data.message) ||
                    error.message ||
                    error.toString();
                this.successful = false;
              }
          );
        }
      });
    }
  }
};
</script>
