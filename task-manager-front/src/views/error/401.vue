<template>
  <ErrorPage :code="401" :text="$t('accessDenied')"/>
</template>

<script>
import ErrorPage from "@/components/error/ErrorPage";

export default {
  name: "errorPage",
  components: {ErrorPage}
}
</script>
