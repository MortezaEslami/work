<template>
  <div id="app">
    <nav class="navbar navbar-expand border-bottom">
      <img class="logo" src="./assets/images/logo.png"/>
      <ul class="navbar-nav mr-auto">
        <li v-if="currentUser" class="nav-item">
          <router-link :to="$i18nRoute({ name: 'TaskList' })" class="nav-link">
            <i class="ri-task-fill"></i>
            {{ $t('menu.tasks') }}
          </router-link>
        </li>
      </ul>
      <ul v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link :to="$i18nRoute({ name: 'Login' })" class="nav-link">
            <i class="ri-login-box-fill"></i>
            {{ $t('menu.login') }}
          </router-link>
        </li>
      </ul>

      <ul v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link :to="$i18nRoute({ name: 'Profile' })" class="nav-link">
            <i class="ri-user-2-fill"></i>
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logout">
            <i class="ri-logout-box-fill"></i>
            {{ $t('menu.logout') }}
          </a>
        </li>
      </ul>
      <LocaleSwitcher/>

    </nav>

    <div class="container">
      <router-view/>
    </div>
  </div>
</template>

<script>
import AuthService from './services/auth-service';
import LocaleSwitcher from "@/components/LocaleSwitcher";
import { i18n } from './i18n';

export default {
  components: {LocaleSwitcher},
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    EventBus.on("logout", () => {
      this.logout();
    });
  },
  methods: {
    logout() {
      AuthService.userLogout().finally(() => {
        this.$store.dispatch('auth/logout');
        this.$router.push('/'+i18n.locale +'/login');
      });
    }
  },
  beforeDestroy() {
    EventBus.remove("logout");
  }
};
</script>
