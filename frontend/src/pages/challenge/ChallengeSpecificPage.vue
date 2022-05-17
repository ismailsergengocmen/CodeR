<template>
  <div class="q-pa-md">
    <div class="row" style="height: 50px">
      <div class="col-4 q-pa-sm">
        <q-banner dense label="Challenges" class="text-white bg-grey">
          <span class="row justify-center">{{ question_title }}</span>
        </q-banner>
      </div>
      <div class="col-8 row align-center q-gutter-x-md q-pa-sm">
        <q-select
          dense
          outlined
          v-model="pl"
          :options="plOptions"
          label="Programming Language"
          class="col-4"
        />
        <div class="q-gutter-x-md col row">
          <q-btn
            outline
            label="Old Attempts"
            class="col-auto"
            @click="showOldAttempts = true"
          />
          <q-btn outline label="Forum" class="col-auto" />
          <q-btn outline label="Leaderboard" class="col-auto" />
        </div>
        <div class="q-gutter-y-md col-auto row">
          <q-rating
            v-model="questionPoint"
            size="1.5em"
            color="grey"
            class="justify-center"
            color-selected="black"
          />
        </div>
      </div>
    </div>
    <div class="row">
      <q-scroll-area style="height: 550px" class="col-4">
        <div class="column q-pa-md">
          {{ question_content }}
        </div>
      </q-scroll-area>

      <q-separator vertical />

      <div v-if="!submiting" class="column col q-pa-md">
        <q-input
          v-model="code"
          filled
          type="textarea"
          @keydown.tab.prevent="code = code + ' \t'"
        />
        <div class="row justify-end q-gutter-x-md q-mt-md">
          <q-btn class="on-right" label="Run Code" />
          <q-btn
            class="bg-black text-white"
            label="Submit"
            @click="submitCode"
          />
        </div>
      </div>
      <div v-else class="q-ma-md col">
        <q-banner
          inline-actions
          :class="
            attemptPoint === 100
              ? 'bg-green text-white q-mb-md'
              : 'bg-red text-white q-mb-md'
          "
        >
          Attempt Point: {{ attemptPoint }}
          <template v-slot:action>
            <q-btn
              flat
              color="white"
              icon="mdi-close"
              @click="submiting = false"
            />
          </template>
        </q-banner>

        <q-separator class="q-my-md" />

        <q-scroll-area style="height: 500px">
          <div
            class="q-mb-md"
            style="height: 50px"
            v-for="(attempt, index) in attemptResults"
            :key="attempt"
          >
            <q-banner
              :class="`${attemptStyle(attempt.result)} text-white full-width`"
            >
              Test Case {{ index + 1 }}: {{ attempt.result }}
            </q-banner>
          </div>
        </q-scroll-area>
      </div>
    </div>
    <q-dialog v-model="showOldAttempts" full-width full-height>
      <q-card>
        <q-scroll-area style="height: 540px">
          <q-card-section>
            <div v-for="(attempt, index) in oldAttempts" :key="attempt.id">
              <q-expansion-item
                expand-separator
                :icon="pastAttemptIcon(attempt.codingAttempt.point)"
                :header-class="pastAttemptStyle(attempt.codingAttempt.point)"
                :label="`Attempt ${index + 1}`"
                :caption="styledDateTime(attempt.codingAttempt.attempt_end)"
                expand-icon-class="text-white"
              >
                <q-card>
                  <q-card-section>
                    <div>
                      Programming Language:
                      {{ attempt.codingAttempt.programming_language }}
                    </div>
                    <div>{{ attempt.codingAttempt.code }}</div>
                    <q-separator class="q-my-sm" />
                    <div class="q-gutter-y-md">
                      <div
                        style="height: 50px"
                        v-for="(attempt, index) in attempt.testCaseAttempt"
                        :key="attempt"
                      >
                        <q-banner
                          :class="`${attemptStyle(
                            attempt.result
                          )} text-white full-width`"
                        >
                          Test Case {{ index + 1 }}: {{ attempt.result }}
                        </q-banner>
                      </div>
                    </div>
                  </q-card-section>
                </q-card>
              </q-expansion-item>
              <q-separator />
            </div>
          </q-card-section>
        </q-scroll-area>
        <q-card-actions align="right">
          <q-btn flat label="Close" color="primary" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref, onBeforeMount, watch } from "vue";
import { api } from "../../boot/axios";

export default {
  name: "ChallengeSpecificPage",
  props: ["question_id"],
  setup(props) {
    const pl = ref("C++");
    const plOptions = ["C++", "C", "Java", "Python3", "Javascript", "Fortran"];
    const question_title = ref("");
    const question_content = ref("");
    const code = ref("");
    const submiting = ref(false);
    const attemptResults = ref([]);
    const attemptPoint = ref(null);
    const showOldAttempts = ref(false);
    const oldAttempts = ref([]);
    const questionPoint = ref(0);

    onBeforeMount(async () => {
      const userID = localStorage.getItem("currentUserID");

      api
        .get(`/api/v1/question/challenge/${props.question_id}`)
        .then((result) => {
          if (result.data === null) {
            router.push("/menu"); // TODO -- bunu questions page route'una çevir
          } else {
            question_title.value = result.data.question_title;
            question_content.value = result.data.question_content;
          }
        });

      api
        .get(
          `/api/v1/attempt/past/challenge/${props.question_id}?user_id=${userID}`
        )
        .then((result) => {
          oldAttempts.value = result.data;
        });

      const userData = {
        user_id: userID,
        question_id: props.question_id,
      };
      api.post("/api/v1/question/getlike", userData).then((result) => {
        if (result.data) {
          questionPoint.value = result.data;
        } else {
          questionPoint.value = 0;
        }
      });
    });

    const submitCode = async () => {
      submiting.value = true;

      const userID = localStorage.getItem("currentUserID");
      const attemptData = {
        user_id: userID,
        question_id: props.question_id,
        programming_language: pl.value,
        code: code.value,
      };
      api
        .post("/api/v1/attempt/submit/challenge", attemptData)
        .then((result) => {
          attemptResults.value = result.data.testCaseAttempts;
          attemptPoint.value = result.data.point;
        });
    };

    const attemptStyle = (attemptResult) => {
      return attemptResult === "pass" ? "bg-green" : "bg-red";
    };

    const pastAttemptIcon = (point) => {
      return point === 100 ? "mdi-check" : "mdi-close";
    };

    const pastAttemptStyle = (point) => {
      return point === 100 ? "bg-green text-white" : "bg-negative text-white";
    };

    const styledDateTime = (dateTimeTimestamp) => {
      const a = dateTimeTimestamp.split("T");
      return a[1] + "  --  " + a[0];
    };

    watch(questionPoint, async () => {
      const userID = localStorage.getItem("currentUserID");
      const likeData = {
        question_id: props.question_id,
        user_id: userID,
        point: questionPoint.value,
      };
      await api.post("api/v1/question/like", likeData);
    });

    return {
      pl,
      plOptions,
      code,
      submiting,
      attemptResults,
      attemptPoint,
      submitCode,
      attemptStyle,
      question_title,
      question_content,
      showOldAttempts,
      oldAttempts,
      pastAttemptIcon,
      pastAttemptStyle,
      styledDateTime,
      questionPoint,
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