<template>
  <div class="q-pa-md">
    <div class="row" style="height: 50px">
      <div class="col-4 q-pa-sm">
        <q-banner dense label="Challenges" class="text-white bg-grey">
          <span class="row justify-center">Question Title</span>
        </q-banner>
      </div>
      <div class="col-8 row justify-content">
        <q-select
          dense
          outlined
          v-model="pl"
          :options="plOptions"
          label="Programming Language"
          class="col-3"
        />
        <div class="q-gutter-x-md col-9">
          <q-btn outline label="Old Attempts" />
          <q-btn outline label="Forum" />
          <q-btn outline label="Leaderboard" />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="column col-4 bordered"></div>
      <div class="column col-8 bordered">
        <div class="q-pa-md" style="max-width: 300px">
          <q-input v-model="text" filled type="textarea" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  name: "ChallengeSpecificPage",
  props: ["course_id"],
  setup(props) {
    const pl = ref("C++");
    const plOptions = ["C++", "C", "Java", "Python3", "Javascript", "Fortran"];

    return {
      pl,
      plOptions,
    };
  },
};
</script>

<style scoped>
.bordered {
  border: 1px solid black;
  height: 100%;
}
</style>