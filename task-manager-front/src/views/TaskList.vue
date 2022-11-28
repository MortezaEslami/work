<template>
  <div class="container">
    <PageTitle v-show="!isAdminUser" :title="$t('this-is-public-page')" :bgColor="'alert-info'"/>
    <PageTitle v-show="isAdminUser" :title="$t('you-have-full-access')" :bgColor="'alert-warning'"/>
    <Tasks/>
  </div>
</template>

<script>
import PageTitle from "@/components/PageTitle";
import Tasks from "@/components/task/Tasks";

export default {
  name: 'TaskList',
  components: {Tasks, PageTitle},
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
  }
};
</script>
