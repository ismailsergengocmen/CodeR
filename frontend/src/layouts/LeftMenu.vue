<template>
<div>
    <div class="absolute-top column items-center" active-class="bg-teal-2">
        <router-link to="/home">
            <q-img 
                width="150px"
                src="../assets/hermione.jpg" 
                class="q-mt-md" 
            /> 
        </router-link> 
    </div>

    <div style="height: calc(100% - 100px); margin-top: 150px">
        
    <q-list class="q-py-none">
        <!--Create Interview/ Sponsor Contest/ Options/ Logout/ 
        Creat Question/ Create Contest/ Options/ Logout/ 
        Challenges/ Contests/ Interviews/ Options/ Logout -->
        <div class="column">
            <!--Company -->
            <router-link to="/~/createInterview" >
                 <q-item v-if="roleCheck('Company')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Create Interview" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to="/~/cInterviews" >
                 <q-item v-if="roleCheck('Company')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Interviews" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to="/~/cSponsorContest" > <!--BUNUN SAYFASI YOK DAHA-->
                <q-item v-if="roleCheck('Company')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Sponsor Contest" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>

            <!--Editor -->
            <router-link to="/~/createQuestion" >
                <q-item v-if="roleCheck('Editor')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Create Question" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to= "/~/createContest">
                <q-item v-if="roleCheck('Editor')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Create Contest" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <!--Job Seeker-->
            <router-link to= "/~/challenges">
                <q-item v-if="roleCheck('JobSeeker')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Challenges" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to= "/~/contest">
                <q-item v-if="roleCheck('JobSeeker')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Contests" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to= "/~/jsInterviewPage">
                <q-item v-if="roleCheck('JobSeeker')" clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Interviews" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
        </div>
         
        <!--General -->
        <div class="fixed-bottom q-mb-sm">
            <router-link to="/register">            
                <q-item clickable v-ripple style="min-height: 35px;">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Options" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
            <router-link to="/">
                <q-item clickable v-ripple style="min-height: 35px;" @click="logout">
                    <q-item-section class="q-ml-sm">
                        <q-btn color="grey" label="Logout" class="q-mr-lg"/>
                    </q-item-section>
                </q-item>
            </router-link>
        </div>
    </q-list>

    
    </div>
</div>
</template>

<script>
import { ref, onBeforeMount } from "@vue/runtime-core"
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../boot/axios"

export default {
  name: 'LeftMenu',

  setup(ctx) {
        const $q = useQuasar();
        const router = useRouter();
        const currentUser = ref(null)

       onBeforeMount(() => {
           const userID = localStorage.getItem("currentUserID");
           api.get(`api/v1/user/type/${userID}`).then((response) => {
            if (response.data) {
                currentUser.value = response.data;
            }})
       }) 

       const roleCheck = (role) => {
           return currentUser.value === role; 
       }
       
       const logout = () => {
        ctx.emit('logout');
      }
      return {
        logout,
        roleCheck,
        currentUser
      }
   },
}
</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>