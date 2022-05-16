<template>
  <div>
    <q-form style="width: 410px" class="q-py-md">
      <q-input
        label="Email"
        v-model="mail"
        color="secondary"
        :rules="[(val) => !!val]"
      >
        <template v-slot:prepend>
          <q-icon name="mdi-account" />
        </template>
      </q-input>

      <q-input
        :type="show ? '' : 'password'"
        label="Password"
        v-model="password"
        color="secondary"
        :rules="[(val) => !!val]"
      >
        <template v-slot:prepend>
          <q-icon name="mdi-lock" />
        </template>

        <template v-slot:append>
          <q-icon
            v-if="password.length > 0"
            :name="show ? 'visibility' : 'visibility_off'"
            class="cursor-pointer"
            @click="show = !show"
          >
          </q-icon>
        </template>
      </q-input>

      <q-btn
        label="LOGIN"
        color="dark"
        unelevated
        no-caps
        align="center"
        style="width: 80%"
        @click.prevent="signIn(mail, password)"
      />
    </q-form>
  </div>
</template>

<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { useQuasar } from "quasar";
import { api } from "../../boot/axios";

export default {
  name: "LoginForm",

  setup(props, ctx) {
    const $q = useQuasar();
    const router = useRouter();
    const $store = useStore();

    const mail = ref("");
    const password = ref("");
    const show = ref(false);

    const signIn = async (email, password) => {
      if (!email && !password) {
        $q.notify({
          position: "top",
          color: "negative",
          message: "Fill all the credentials",
        });
      } else {
        const loginData = {
          email,
          password,
        };
        api
          .post("/api/v1/auth/login", loginData)
          .then((response) => {
            if (!response.data) {
              $q.notify({
                position: "top",
                color: "negative",
                message: "Wrong credentials",
              });
              localStorage.removeItem("currentUserID");
            } else {
              // Add user_id to localStorage
              localStorage.setItem("currentUserID", response.data);
              router.push("/home");
            }
          })
          .catch(() => {
            $q.notify({
              position: "top",
              color: "negative",
              message: "There was an error",
            });
          });
      }
    };

    return {
      mail,
      password,
      show,
      signIn,
    };
  },
};
</script>