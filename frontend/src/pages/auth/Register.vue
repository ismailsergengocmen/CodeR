<template>
  <div class="q-pt-lg row align-center justify-center">
    <register-form :isMobile="isMobile"/>
  </div>
</template>

<script>
import RegisterForm from "../../components/auth/RegisterForm.vue"
import { useQuasar } from 'quasar'

export default {
  name: 'Register',

  components: {
    RegisterForm
  },
  computed: {
    isMobile() {
      const $q = useQuasar();
      return $q.screen.width < 800;
    }
  },

  setup () {
    return {
    }
  }
}
</script>
