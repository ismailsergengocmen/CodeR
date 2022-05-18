<template>
  <div class="full-height">
    <div class="fixed-top-center q-pa-md">
      <q-banner class="bg-grey text-white" dense>
        <span class="row justify-center"> Interview Results </span>
      </q-banner>
    </div>
    <div>
      <span class="label bg-white text-black">Participants</span>
      <q-scroll-area visible style="height: 300px" class="col" ref="firstRef">
        <div
          v-for="participantResult in participantResults"
          :key="participantResult.user_id"
          class="q-pa-sm"
        >
          <div class="row justify-content">
            <div class="row">
              <span class="label bg-white text-black">
                {{ participantResult.user_name }}
              </span>
              <q-separator />
            </div>
            <div>
              <q-btn label="See" @click="firstDialog = true" />
              <q-dialog v-model="firstDialog">
                <q-card>
                  <q-card-section>
                    <div class="text-h6">{{ participantResult.user_name }}</div>
                  </q-card-section>

                  <q-card-section class="q-pt-none">
                    <div
                      class="col"
                      v-for="codingAnswer in participantResult.interviewCodingQuestionResults"
                      :key="codingAnswer.question_id_name"
                    >
                      <div class="row justify-content">
                        <span>{{ codingAnswer.question_id_name }}</span>
                        <q-btn
                          flat
                          label="See Answer"
                          @click="secondDialog = true"
                        ></q-btn>

                        <q-dialog v-model="secondDialog">
                          <q-card style="width: 600px; height: 600px">
                            <q-card-section>
                              <div class="text-h6">
                                {{ codingAnswer.question_name }}
                              </div>
                            </q-card-section>

                            <q-card-section class="q-pt-none">
                              <div class="row">
                                <div class="col">
                                  <span> Score </span>
                                  <div>{{ codingAnswer.score }}</div>
                                </div>
                                <div class="col">
                                  <span> Time </span>
                                  <div>{{ codingAnswer.time_taken }}</div>
                                </div>
                                <div class="col">
                                  <span> Status </span>
                                  <div>{{ codingAnswer.status }}</div>
                                </div>
                              </div>
                              <div class="col">
                                <span> Code </span>
                                <div>{{ codingAnswer.code }}</div>
                              </div>
                            </q-card-section>

                            <q-card-actions align="right" class="text-primary">
                              <q-btn flat label="Close" v-close-popup />
                            </q-card-actions>
                          </q-card>
                        </q-dialog>
                      </div>
                    </div>
                    <div
                      class="row"
                      v-for="nonCodingAnswer in participantResult.interviewNonCodingQuestionResults"
                      :key="nonCodingAnswer.question_id_name"
                    >
                      <div class="row justify-content">
                        <span>{{ nonCodingAnswer.question_id_name }}</span>
                        <q-btn
                          flat
                          label="See Answer"
                          @click="thirdDialog = true"
                        ></q-btn>

                        <q-dialog v-model="thirdDialog">
                          <q-card style="width: 600px; height: 600px">
                            <q-card-section>
                              <div class="text-h6">
                                {{ nonCodingAnswer.question_name }}
                              </div>
                            </q-card-section>

                            <q-card-section class="q-pt-none">
                              <div class="row">
                                <div class="col">
                                  <span> Status </span>
                                  <div>{{ nonCodingAnswer.status }}</div>
                                </div>
                              </div>
                              <div class="col">
                                <span> Code </span>
                                <div>{{ nonCodingAnswer.answer }}</div>
                              </div>
                            </q-card-section>

                            <q-card-actions align="right" class="text-primary">
                              <q-btn flat label="Close" v-close-popup />
                            </q-card-actions>
                          </q-card>
                        </q-dialog>
                      </div>
                    </div>
                  </q-card-section>

                  <q-card-actions align="right" class="text-primary">
                    <q-btn flat label="Close" v-close-popup />
                  </q-card-actions>
                </q-card>
              </q-dialog>
            </div>
          </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import { ref, onBeforeMount } from "@vue/runtime-core";
import { useRouter } from "vue-router";
import { useQuasar } from "quasar";
import { api } from "../../boot/axios";

export default {
  name: "CompanyInterviewResult",
  props: ["id"],

  setup(props) {
    const $q = useQuasar();
    const router = useRouter();

    const firstDialog = ref(false);
    const secondDialog = ref(false);
    const thirdDialog = ref(false);

    const participantResults = ref([]);

    onBeforeMount(() => {
      api.get(`api/v1/interview/results/${props.id}`).then((response) => {
        if (response.data) {
          participantResults.value = response.data;
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
      participantResults,
      firstDialog,
      secondDialog,
      thirdDialog,
    };
  },
};
</script>