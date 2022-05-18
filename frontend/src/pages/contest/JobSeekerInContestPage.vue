<template>
<div>
    <div class="full-height">
        <div class="fixed-top-center q-pa-md">
            <q-banner class="bg-grey text-white" dense>
                <span class="row justify-center"> {{contestName}} </span>
            </q-banner>
        </div>

        <div class="row bordered justify-around q-pa-sm">
            <div>
                <span class="label bg-white text-black">Start Date</span>
                <span class="row justify-center"> {{contestDate}} </span>
            </div>
            <div>
                <span class="label bg-white text-black">Categories</span>
                <span class="row justify-center"> {{contestCategories}} </span>
            </div>
        </div>

        <div class="q-pa-md">
        <div class="row justify-center">
        <q-field outlined style="width: 50%;" color="black">
        <q-scroll-area
            visible
            style="height: 200px;" 
            style2= "weight: 200px;"
            class="col"
            ref="firstRef"
            >
            <span class="label bg-white text-black">Questions</span>
            
            <q-scroll-area
            visible
            style="height: 500px;" 
            style2= "weight: 200px;"
            class="col"
            ref="firstRef"
            >
                <div v-for="question in contestQuestions" :key="question.question_id" class="q-pa-sm">
                    <div class="row justify-content">

                    <q-form class="q-gutter-y-sm q-px-sm q-py-sm full-width" >  
                        <div class="q-pa-sm">
                            <div class="row justify-between">
                            <span class="label bg-white text-black"> {{ question.question_title }} </span>

                    
                            <q-btn label="Begin" :to="`/~/challenge/${question.question_id}`" class="q-pa-sm"/>  
                        </div>
                        </div>
                    </q-form>
                        
                    </div>
                </div> 
            </q-scroll-area>
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

    const contestName = ref("");
    const contestCategories = ref([]);
    const contestDate = ref("");
    const contestQuestions = ref([]);

    onBeforeMount(() => {
      api.get(`api/v1/contest/${props.id}`).then((response) => {
        if (response.data) {
          contestName.value = response.data.contest_name;
          contestCategories.value = response.data.category;
          contestDate.value = response.data.start_time;
          contestQuestions.value = response.data.contest_questions;
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
      contestName,
      contestCategories,
      contestDate,
      contestQuestions,
    };
  },
};
</script>