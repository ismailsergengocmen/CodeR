<template>
  <div>
    <div class="full-height">
      <div class="fixed-top-center q-pa-md">
        <q-banner class="bg-grey text-white" dense>
          <span class="row justify-center"> {{ interviewName }} </span>
        </q-banner>
      </div>
      <div class="row bordered justify-around q-pa-sm">
        <div>
          <span class="label bg-white text-black">Start Date</span>
          <span class="row justify-center"> {{ interviewDate }} </span>
        </div>
      </div>


      <div class="q-pa-md">
        <div class="row justify-center">
            <q-field outlined style="width: 50%;" color="black">

        <span class="label bg-white text-black">Questions</span>
        <q-scroll-area visible style="height: 300px" class="col">
          <div
            v-for="question in interviewQuestions"
            :key="question.question_id"
            class="q-pa-sm"
          >
            <div class="row justify-between">
              <div class= "q-pa-sm">

                <span class="label bg-white text-black">
                  {{ question.question_title }}
                </span>

                <q-btn
                  label="Begin"
                  :to="`/~/challenge/${question.question_id}`"
                  class="q-pa-sm"/>

              </div>
            </div>
          </div>
        </q-scroll-area>
            </q-field>
        
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import { ref, onBeforeMount } from "@vue/runtime-core";
import { useRouter } from "vue-router";
import { useQuasar } from "quasar";
import { api } from "../../boot/axios";

export default {
  name: "JobSeekerInContestPage",
  props: ["id"],

  setup(props) {
    const $q = useQuasar();
    const router = useRouter();

    const interviewName = ref("");
    const interviewDate = ref("");
    const interviewQuestions = ref([]);

    onBeforeMount(() => {
      api.get(`api/v1/interview/${props.id}`).then((response) => {
        if (response.data) {
          interviewName.value = response.data.title;
          interviewDate.value = response.data.start_date;
          interviewQuestions.value = response.data.interview_questions;
        } else {
          $q.notify({
            position: "top",
            color: "negative",
            message: "Selected contest can not be found",
          });
        }
      });
    });

    return {
      interviewName,
      interviewDate,
      interviewQuestions,
    };
  },
};
</script>