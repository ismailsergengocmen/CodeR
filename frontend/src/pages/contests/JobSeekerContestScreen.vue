<template>
<div>
    <div class="q-pa-md">
        <router-link to="/register" >
        <div class="row justify-center">
            <q-btn color="grey" label="New Contest" class="q-mr-lg"/>
            <q-btn color="grey" label="My Contest"/>
        </div>
        </router-link>
    </div>
    <div class="column items-center" style="height: 200px">
        <div class="row justify-center" style="height: 300px">
            <div class="q-gutter-md">
                <div class="q-gutter-md row">
                    <div v-for="difficulty in difficulties" 
                    :key="difficulty.name" >
                        <q-btn :color="difficultyStyle(difficulty.value)" 
                        :label="difficulty.name" @click="selectDifficulty(difficulty.value)"/>
                    </div>
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
        </div>
    </div>
    
    <!-- contests -->
    <q-field outlined style="width: 200%;" color="black">
        <q-scroll-area
            visible
            style="height: 500px;" 
            style2= "weight: 500px;"
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
                                        <div v-for="sponsor in sponsorss" :key="sponsor.sponsor_id" class="q-pa-sm">
                                            <span class="row justify-center"> {{sponsor.sponsor_name}}</span> 
                                        </div>
                                    </div>
                                </template>
                            </q-field> 
                        </div> 
                    </div>
            
                    <div class="col">
                        <q-btn 
                        label="Join This Contest" :to="`contest/${contest.contest_id}`"/>
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

    setup(ctx){
        const currentDifficulties = ref(["easy"]);
        const currentCategories = ref([]);
        const currentName = ref("");

        const contests = ref([]);
        const sponsorss = ref([]);

        const filteredSlots = computed(() => {
        return true;
        });

        const difficulties = [
            {name: "Easy", value: "easy"}, 
            {name: "Medium", value: "medium"}, 
            {name: "Hard", value: "hard"},
            {name: "Extreme", value: "extreme"}
        ];

        function selectDifficulty(difficulty){
            if(!currentDifficulties.value.some(data => data == difficulty))
                currentDifficulties.value.push(difficulty);
            else
                currentDifficulties.value = currentDifficulties.value.filter(data => data != difficulty)
            ctx.emit("selectDifficulty", currentDifficulties)
        }

        function selectCategory(category){
            if(!currentCategories.value.some(data => data == category))
                currentCategories.value.push(category);
            else
                currentCategories.value = currentCategories.value.filter(data => data != category)
            ctx.emit("selectCategory", currentCategories) 
            // Can only send one parameter at a time, if not send with an object
        }

        function difficultyStyle(difficulty){
            if(currentDifficulties.value.some(data => data === difficulty))
                return "black"
            return "grey"    
        }

        watch(currentName, (newVal) => {
            ctx.emit("search", newVal.value)
        })

        //new part
        onBeforeMount(() => {
            api.get(`api/v1/contest/all`).then((response) => {
                contests.value = response.data
                sponsorss.value = response.data.sponsors
            })
        })

        return{
            currentDifficulties,
            difficulties,
            currentCategories,
            selectDifficulty,
            selectCategory,
            difficultyStyle,
            currentName,
            filteredSlots,
            contests,
            sponsorss
        }
    }
}
</script>

<style scoped>
</style>