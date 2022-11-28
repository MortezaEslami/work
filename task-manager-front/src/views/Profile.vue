<template>
  <div class="card">
    <h3>
      <strong>{{ currentUser.username }}</strong> {{$t('profile')}}
    </h3>
    <div class="col-12">
      <strong>{{$t('email')}}:</strong>
      {{ currentUser.email }}
    </div>
    <div class="col-12 authorities">
      <strong>{{$t('authorities')}}:</strong>
      <ul>
        <li v-for="(role,index) in currentUser.roles" :key="index">{{ role }}</li>
      </ul>
    </div>
    <div class="alert alert-warning">
      <strong class="d-block">{{$t('token')}}:</strong>
      <span class="font-xs"> {{ currentUser.accessToken }}</span>
    </div>
    <div class="alert alert-info">
      <strong class="d-block">{{$t('refreshToken')}}:</strong>
      <span class="font-xs"> {{ currentUser.refreshToken }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>
