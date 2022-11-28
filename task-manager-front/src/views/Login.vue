<template>
  <div class="row-center">
    <div class="col-md-5 col-sm-12">
      <div class="card card-container">
        <img class="card-logo" src="../assets/images/logo.png"/>
        <form name="form" @submit.prevent="handleLogin">
          <div class="form-group form-validate-input">
            <label for="username">
              {{$t('username')}}
            </label>
            <input
                v-model="user.username"
                v-validate="'required'"
                type="text"
                class="form-control"
                name="username"
                id="username"
            />
            <div v-if="errors.has('username')" class="alert alert-danger" role="alert">{{$t('username-is-required')}}</div>
          </div>
          <div class="form-group form-validate-input">
            <label for="password">
              {{$t('password')}}
            </label>
            <input
                v-model="user.password"
                v-validate="'required'"
                type="password"
                class="form-control"
                name="password"
                id="password"
            />
            <div v-if="errors.has('password')" class="alert alert-danger" role="alert">{{$t('password-is-required')}}</div>
          </div>
          <div class="form-group text-center">
            <button class="btn btn-link m-1" @click='resetForm' type="reset">
              {{$t('btn.reset')}}
            </button>
            <button class="btn btn-success" :disabled="loading">
              <span v-show="loading" class="spinner-border spinner-border-sm"></span>
              <span>
                {{$t('btn.login')}}
              </span>
            </button>
          </div>
          <div class="form-group">
            <div v-if="message" class="alert alert-danger" role="alert">{{ message }}</div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import NewTask from '../models/new-task';
import { i18n } from '../i18n';

export default {
  name: 'Login',
  data() {
    return {
      user: new NewTask('', '', ''),
      loading: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
    /*
        if (this.loggedIn) {
        this.$router.push('/profile');

      }*/
  },

  methods: {
    resetForm: function () {
      this.user.username = '';
      this.user.password = '';
    },
    handleLogin() {
      this.loading = true;
      this.$validator.validateAll().then(isValid => {
        if (!isValid) {
          this.loading = false;
          return;
        }
        if (this.user.username && this.user.password) {
          this.$store.dispatch('auth/login', this.user).then(
              () => {
                this.$router.push('/'+i18n.locale + '/profile');
              },
              error => {
                this.loading = false;
                this.$toasted.show(
                    (error.response && error.response.data && error.response.data.message) ||
                    error.message ||
                    error.toString()
                );
              }
          );
        }
      });
    }
  }
};
</script>

