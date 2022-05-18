<template>
  <div>
    <div class="q-pa-md">
      <q-banner dense label="Challenges" class="text-white bg-grey">
        <span class="row justify-center"> Company Interviews </span>
      </q-banner>
    </div>
    <div>
        <div class= "q-pa-md">
            <q-banner dense label="Challenges" class="text-white bg-grey">
                <span class="row justify-center"> Company Interviews </span>
            </q-banner>
        </div>
        <div>
            <q-input
                v-model="currentName"
                debounce="1500"
                outlined
                dense
                placeholder='Search'
                color="secondary"
            >
                <template v-slot:prepend>
                    <q-icon name="search" />
                </template>
            </q-input>
        </div>

        <div class="q-pa-lg">
        <div class="row justify-center">
            
    <q-field outlined style="width: 50%;" color="black">
            <q-scroll-area
                visible
                style="height: 300px;"
                class="col"
                ref="firstRef"
                >
                <div v-for="interview in interviews" :key="interview.interview_id" class="q-pa-md">
                    <div class="row justify-between">
                        
                            <span class="q-pa-sm"> {{ interview.title }} </span>
                        
                            <span class="q-pa-sm"> {{ interview.start_date }} </span>
                        
                            <q-btn label="See" :to="`/~/cInterviewResults/${interview.interview_id}`"/>  
                        
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
import { ref, watch, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import { useQuasar } from "quasar";
import { api } from "../../boot/axios";

export default {
  name: "CompanyInterviewPage",

  setup() {
    const $q = useQuasar();
    const router = useRouter();

    const currentName = ref("");
    const interviews = ref([]);

    onBeforeMount(() => {
      const user_id = localStorage.getItem("currentUserID");
      api.get(`api/v1/interview/all?company_id=${user_id}`).then((response) => {
        if (!response.data) {
          $q.notify({
            position: "top",
            color: "negative",
            message: "Something wrong",
          });
        } else {
          interviews.value = response.data;
        }
      });
    });

    watch(currentName, (newName) => {
      search(newName);
    });

    const search = async (newName) => {
      const user_id = localStorage.getItem("currentUserID");
      api
        .get(
          `api/v1/interview/all?company_id=${user_id}&interview_name=${newName}`
        )
        .then((response) => {
          if (!response.data) {
            $q.notify({
              position: "top",
              color: "negative",
              message: "Something wrong",
            });
          } else {
            interviews.value = response.data;
          }
        });
    };

    return {
      currentName,
      interviews,
      search,
    };
  },
};
</script>