<template>
  <ErrorPage :code="404" :text="$t('pageNotFound')"/>
</template>

<script>
import ErrorPage from "@/components/error/ErrorPage";

export default {
  name: "errorPage",
  components: {ErrorPage}
}
</script>
