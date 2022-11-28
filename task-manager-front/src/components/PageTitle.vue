<template>
  <div class="alert mt-3" :class="bgColor">{{ title }}</div>
</template>

<script>
export default {
  name: "PageTitle",
  props: ['title', 'bgColor']
}
</script>
