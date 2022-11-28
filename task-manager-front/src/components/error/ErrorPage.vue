<template>
  <div class="text-center error-page">
    <img src="../../assets/images/logo.png"/>
    <h1>
      {{ code }}
    </h1>
    <p>{{ text }}</p>
  </div>
</template>

<script>
export default {
  name: "ErrorPage",
  props: ['code', 'text']
}
</script>
