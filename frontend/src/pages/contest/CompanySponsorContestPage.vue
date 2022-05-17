<template>
    <div>
        <div class= "q-pa-md">
            <q-banner dense label="Challenges" class="text-white bg-grey">
                <span class="row justify-center"> Current Contests </span>
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
                <div v-for="contest in contests" :key="contest.contest_id" class="q-pa-sm">
                    <div class="row justify-content">
                        <div>
                            <span class="label bg-white text-black"> {{ contest.contest_name }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ contest.start_time }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <q-btn label="Sponsor" @click="sponsorContest(contest.contest_id)"/>  
                        </div>
                    </div>
                </div> 
            </q-scroll-area>
        </div>
    </div>
</template>

<script>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"

export default {
    name: "CompanySponsorContestPage",

    setup() {
        const $q = useQuasar();
        const router = useRouter();

        const currentName = ref("")
        const contests = ref([])
        const currentSponsors = ref([])
        const currentUser = ref([])

        const getContests = () => {
            const user_id = localStorage.getItem("currentUserID")
            api.get(`api/v1/contest/all`).then ((response) => {
                if(!response.data){
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Something wrong"
                    })
                }
                else{
                    contests.value = response.data
                }
            })
        }

        const sponsorContest = (contest_id) => {
            const user_id = localStorage.getItem("currentUserID")

            api.get(`api/v1/contest/${contest_id}`).then((response) => {
                if(response.data){
                    currentSponsors.value = response.data.sponsors
                }
                else{
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Can't get sponsors"
                    })
                }
            })

            api.get(`api/v1/contest/${user_id}`).then((response) => {
                if(response.data){
                    currentUser.value = response.data
                }
                else{
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Can't get user in sponsorContest"
                    })
                }
            })

            currentSponsors.value.push(currentUser.value)

            const contestData = {  
                contest_id,
                sponsors: currentSponsors.value,
            };

            api.push("api/v1/contest/update", contestData).then((response) => {
                if(response.data){
                    $q.notify({
                    position:"top",
                    color:"positive",
                    message:"Succesfully sponsored"
                    })
                }
                else{
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Can not be sponsored"
                    })
                }
            })
        }

        return {
            currentName,
            contest,
            getContests,
            sponsorContest
        }
    },
}
</script>