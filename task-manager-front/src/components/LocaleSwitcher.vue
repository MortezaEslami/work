<template>
  <div>
    <ul class="flags">
      <li class="lang-link" :selected="currentLanguage === locale && isActive"
          v-for="locale in supportedLocales" :key="locale" @click="switchLocale(locale)">
        <img :src="require(`@/assets/images/${locale}.png`)" :alt="locale">
      </li>
    </ul>
  </div>
</template>

<script>
import {Trans} from '@/plugins/Translation'

export default {
  name: 'LocaleSwitcher',
  data() {
    return {
      isActive: false,
      currentLanguage: '',
    }
  },
  mounted() {
    this.isActive = true;
    this.currentLanguage = Trans.currentLocale;
  },
  computed: {
    supportedLocales() {
      return Trans.supportedLocales
    },
  },
  methods: {
    switchLocale(locale) {
      if (this.$i18n.locale !== locale) {
        this.isActive = true;
        this.currentLanguage = locale;
        const to = this.$router.resolve({params: {locale}})
        return Trans.changeLocale(locale).then(() => {
          this.$router.push(to.location)
        })
      }
    }
  }
}
</script>

