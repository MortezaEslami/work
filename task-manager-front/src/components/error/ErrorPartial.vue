<template>
  <div class="text-center">
    <div class="text-center text-danger">
      <i class="ri-bug-fill ri-3x"></i>
      <p>{{ text }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ErrorPartial",
  props: ['text']
}
</script>
