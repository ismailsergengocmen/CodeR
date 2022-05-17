<template>
  <div class="q-pa-xl column items-center">
    <q-card class="column items-center q-pa-xl">
      <q-img 
        src="../../assets/logo_coder.png"
        fit="fill"
        width="320px"
        class="q-mb-md"
      />
      <login-form />
      <q-btn 
          label="Register" 
          color="secondary"
          class = "q-mb-md full-width q-mx-md"
          unelevated
          to="/auth/register"
        />
    </q-card>
  </div>
</template>

<script>
import { ref } from "vue"

import LoginForm from "../../components/auth/LoginForm.vue"

export default {
  name: 'Login',

  components: {
    LoginForm
  },

  setup () {
    return {
    }
  }
}
</script>
