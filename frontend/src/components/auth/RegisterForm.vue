<template>
<div class="full-width column items-center">
  <q-form :class="`${formStyling} greedy`" :style="isMobile ? 'width: 90%' : 'width: 60%'">
    <div class="column q-gutter-y-sm">
      <div class="row q-gutter-x-md">
        
        <q-input 
          label="FullName" 
          filled
          square
          v-model="name"
          class="col-8"
          color="secondary"
          :disable="loading"
          :rules="[ val => !!val]"
        />

        <q-select
          label='Role' 
          filled
          square
          v-model="role"
          class="col"
          color="secondary"
          :options="roleOptions"
          :disable="loading"
          :rules="[ val => !!val]"
        />
      </div>

      <div class="row q-gutter-x-md">
        <q-input 
          label="Email" 
          class="col"
          filled
          square
          type="email"
          v-model="mail"
          color="secondary"
          :disable="loading"
          lazy-rules
          :rules="[emailRuleValidity]"
        />
      </div>

      <div class="row q-gutter-x-md">
        <q-input 
          label="Phone" 
          class="col"
          filled
          square
          v-model="phone"
          color="secondary"
          :disable="loading"
          mask="(###) ### - ####"
          unmasked-value
          fill-mask
          lazy-rules
          :rules="[ val => !!val && val.length == 10]"
        />
      </div>
    
      <q-input
        label="Password" 
        filled
        square
        :type="show ? 'text' : 'password'"
        color="secondary"
        v-model="password"
        :disable="loading"
        lazy-rules
        :rules="[ val => val && val.length >= 8 || 'ShortPasswordError']"
      >
        <template v-slot:append>
          <q-icon
            :name="show ? 'visibility' : 'visibility_off'"
            class="cursor-pointer"
            @click="show = !show"
            >
          </q-icon>
        </template>
      </q-input>
    </div>

    <q-btn 
      label="Register" 
      color="dark" 
      unelevated 
      align="center"
      class="q-mt-sm"
      :style="isMobile ? 'width: 50%' : 'width: 30%'"
      @click.prevent="register(name, mail, password, role, phone)"
      :disable="loading"
    />
  </q-form>
</div>
</template>

<script>
import { ref, computed } from "vue"
import { useRouter } from 'vue-router'
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"


export default {
  name: "RegisterForm",
  props: {
    isMobile: Boolean
  },
  setup(props) {
    const router = useRouter();
    const $q = useQuasar();

    const name = ref(null);
    const role = ref(null);
    const mail = ref(null);
    const phone = ref(null);
    const password = ref(null);
    const show = ref(false);
    const loading = ref(false);
    
    const roleOptions = [
        'jobseeker', 'company', 'editor'
    ];

    const emailRuleValidity = (val) => {
      const emailPattern = /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/;
      return emailPattern.test(val) || 'InvalidMail';
    }

    const formStyling = computed(() => {
      return props.isMobile ? "q-gutter-md" : "q-pa-md q-gutter-md";
    })

    const register = async (name, mail, password, accountType, phone) => {
      console.log(name, mail, password, accountType, phone)
      if (inputValidity(name, mail, password, phone, accountType)) {
        const registerData = {
          type: accountType,
          email: mail,
          password,
          name,
          phone_no: phone
        };

        api.post("/api/v1/auth/register", registerData).then((response) => {
          if(response.data){
            $q.notify({
              position: 'top',
              color: 'positive',
              message: 'Register Successful'
            });
            router.push('/auth/login');
          }
          else{
            $q.notify({
              position: 'top',
              color: 'negative',
              message: 'Email has already used'
            })
          }
        }).catch(() => {
          $q.notify({
            position: 'top',
            color: 'negative',
            message: 'Register Not Successful'
          });
        });
      }
      else {
        $q.notify({
          position: 'top',
          color: 'negative',
          message:'EmptySlotsError' 
        });
      } 
    }

    const inputValidity = (name, mail, password, phone, role) => {
      if (!name || !mail || !password || !phone || !role) {
        return false;
      }
      else if (password.length < 8) {
        return false;
      }
      return true;
    }
    
    return {
      formStyling,
      name,
      mail,
      phone,
      password,
      show,
      role,
      roleOptions,
      register,
      loading,
      emailRuleValidity
    }
  },
}
</script>