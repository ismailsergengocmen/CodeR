<template>
    <div>
        <div class= "q-pa-md">
            <q-banner dense label="Challenges" class="text-white bg-grey">
                <span class="row justify-center"> Current Contests </span>
            </q-banner>
        </div>
        <div class="row justify-center">
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
        <div class="q-pa-md">
        <div class="row justify-center">
            <q-field outlined style="width: 50%;" color="black">
                <q-scroll-area
                    visible
                    style="height: 500px;" 
                    style2= "weight: 200px;"
                    class="col"
                    ref="firstRef"
                    >
                    <div v-for="contest in contests" :key="contest.contest_id" class="q-pa-sm">
                        <div class="row justify-center">
                            <q-form class="q-gutter-y-sm q-px-sm q-py-sm full-width" >
                                <q-field 
                                    outlined 
                                    stack-label
                                    color="grey"
                                    >
                                    <template v-slot:control>
                                        <div class= "q-pa-sm">

                                                <span class="q-pa-md"> {{ contest.contest_name }} </span>
                                                
                                                <span class="q-pa-md"> {{ contest.start_time }} </span>
                                                
                                                <q-btn class="q-pa-sm" label="Sponsor" @click="sponsorContest(contest.contest_id)"/>  
                                            
                                        </div>
                                </template>
                            </q-field>
                            </q-form>
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
import { ref, onBeforeMount } from "vue"
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

        //new part
        onBeforeMount(() => {
            api.get(`api/v1/contest/all`).then((response) => {
                contests.value = response.data;
        })})

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
            const newSponsor = {
                user_id,
                contest_id,
                money: 500

            }
            currentSponsors.value.push(newSponsor)
            
            const contestData = {  
                contest_id,
                sponsors: currentSponsors.value,
            };

            api.put("api/v1/contest/update", contestData).then((response) => {
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
            contests,
            sponsorContest
        }
    },
}
</script>