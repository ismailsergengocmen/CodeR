<template>
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
        <div>
            <span class="label bg-white text-black">Interviews</span>
            <q-scroll-area
                visible
                style="height: 300px;"
                class="col"
                ref="firstRef"
                >
                <div v-for="interview in interviews" :key="interview.interview_id" class="q-pa-sm">
                    <div class="row justify-content">
                        <div>
                            <span class="label bg-white text-black"> {{ interview.title }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ interview.start_date }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <q-btn label="See" :to="`/${interview.interview_id}`"/>  
                        </div>
                    </div>
                </div> 
            </q-scroll-area>
        </div>
    </div>
</template>

<script>
import { ref, watch } from "vue"
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"

export default {
    name: "CompanyInterviewPage",

    setup() {
        const $q = useQuasar();
        const router = useRouter();

        const currentName = ref("");
        const interviews = ref([])

        const getInterviews = () => {
            const user_id = localStorage.getItem("currentUserID")
            api.get(`api/v1/?company_id=${user_id}`).then ((response) => {
                if(!response.data){
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Something wrong"
                    })
                }
                else{
                    interviews.value = response.data
                }
            })
        }

        return {
            currentName,
            getInterviews
        }
    },
}
</script>