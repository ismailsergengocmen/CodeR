<template>
    <div>
        <div class= "q-pa-md">
            <q-banner dense label="Challenges" class="text-white bg-grey">
                <span class="row justify-center"> Leaderboard </span>
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
            <q-scroll-area
                visible
                style="height: 300px;"
                class="col"
                ref="firstRef"
                >
                <div v-for="contestant in leaderboard" :key="contestant.interview_id" class="q-pa-sm">
                    <div class="row justify-content">
                        <div>
                            <span class="label bg-white text-black"> {{ contestant.name }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ contestant.total_point}} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ contestant.total_finish_time_duration}} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ contestant.total_attempt_count}} </span>
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
    name: "ContestLeaderboardPage",
    props:["id"],

    setup() {
        const $q = useQuasar();
        const router = useRouter();

        const leaderboard = ref([])

        onBeforeMount(() => {
            api.get(`api/v1/contest/leaderboard/${props.id}`).then ((response) => {
                if(!response.data){
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Can't get the leaderboard"
                    })
                }
                else{
                    leaderboard.value = response.data
                }
            })
        })

        return {
            currentName,
        }
    },
}
</script>