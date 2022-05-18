<template>
<div>
    <div class="fixed-top-center q-pa-md">
            <q-banner class="bg-grey text-white" dense>
                <span class="row justify-center">New Contests</span>
            </q-banner>
    </div>
    <div class="q-pa-md">
        <div class="row justify-center">
            <router-link to="/~/mycontest" >
                <q-btn color="grey" label="Go To My Contests" class="q-mr-lg"/>
            </router-link>
        </div>
    </div>
    <div class="column items-center">
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
        </div>
    </div>

    <div class="q-pa-md">
        <div class="row justify-center">
    <!-- contests -->
            <q-field outlined style="width: 50%;" color="black">
                <q-scroll-area
                    visible
                    style="height: 500px;" 
                    style2= "weight: 200px;"
                    class="col"
                    ref="firstRef"
                    >
                    <div v-for="contest in contests" :key="contest.contest_id" class="q-pa-sm">
                        <q-form class="q-gutter-y-md q-px-md q-py-md full-width" >
                            <div class="col">
                                <div>
                                    <span class="row justify-center"> {{contest.contest_name}} </span> <!-- {{contestName}} -->
                                </div>
                            </div>
                    
                            <div class="col">
                                <div class="q-mr-lg">
                                    
                                    <q-field 
                                    outlined 
                                    stack-label
                                    label="Contest Categories"
                                    color="grey"
                                    >
                                        <template v-slot:control>
                                            <div class="self-center">
                                                <span class="row justify-center"> {{contest.category}} </span> 
                                            </div>
                                        </template>
                                    </q-field>

                                    <q-field 
                                    outlined 
                                    stack-label
                                    label="Sponsors"
                                    color="grey"
                                    >
                                        <template v-slot:control>
                                            <div class="self-center">
                                                <div class="row justify-center q-pa-sm" v-for="sponsor in contest.sponsors" :key="sponsor.user_id">
                                                    <span> {{sponsor.sponsor_name}}</span> 
                                                </div>
                                            </div>
                                        </template>
                                    </q-field> 
                                </div> 
                            </div>
                    
                            <div class="col">
                                <q-btn 
                                label="Join This Contest" @click="enterContest(contest.contest_id)"/>
                            </div>

                        </q-form>
                    </div>
                </q-scroll-area>
            </q-field>
  </div>
  </div>  
</div>

</template>

<script>
import { ref, watch, computed } from "vue"
import { onBeforeMount } from '@vue/runtime-core'
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"

export default {
    name: "ChallengeFilter",

    setup(props,ctx){
        const $q = useQuasar();
        const router = useRouter();
        const currentName = ref("");
        const contests = ref([]);
        const filteredSlots = computed(() => {
            return true;
        });

        watch(currentName, (newVal) => {
            ctx.emit("search", newVal.value)
        })

        //new part
        onBeforeMount(() => {
            const userID = localStorage.getItem("currentUserID");
            api.get(`api/v1/contest/all?user_id=${userID}&entered=false`).then((response) => {
                contests.value = response.data;
        })})

        const enterContest = (contestId) => {
            const userID = localStorage.getItem("currentUserID");
            const contestData = {
                contest_id: contestId,
                user_id: userID
            };
            api.post('api/v1/contest/enter', contestData).then((result) => {
                if (result.data) {
                    $q.notify({
                        position:"top",
                        color:"positive",
                        message:"Contest entered successfully"
                    })
                    router.push(`contest/${contestId}`);
                } else {
                    $q.notify({
                        position:"top",
                        color:"negative",
                        message:"There was a problem while entering the contest"
                    })
                }
            })
        }

        return{
            currentName,
            filteredSlots,
            contests,
            enterContest
        }
    }
}
</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>