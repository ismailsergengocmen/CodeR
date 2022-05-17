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
  
    <q-field outlined style="width: 150%;" color="secondary">
      <q-form class="q-gutter-y-md q-px-md q-py-md row" >
        <div class="col"><div>
          <b>pralala</b>
        </div></div>
  
        <div class="col">
            <div class="q-mr-lg">
        <q-field 
          outlined 
          stack-label
          label="slot.label"
          color="secondary"
          >
          <template v-slot:control>
            <div class="self-center"> lalala </div>
          </template>
        </q-field></div> </div>
  
        <div class="col"><q-btn 
          label="Join" 
          type="submit" 
          color="secondary" 
          @click="join" 
        /></div>
      </q-form>
    </q-field>
    </div>
    </div>
</div>

</template>

<script>
import { ref, watch, computed } from "vue"

export default {
    name: "ChallengeFilter",
    props: {
        slots: Array,
        title: String
    },
    setup(props,ctx){
        const currentDifficulties = ref(["easy"]);
        const currentCategories = ref([]);
        const currentName = ref("");

        const join = () => {
        
        }
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

        return{
            currentDifficulties,
            difficulties,
            currentCategories,
            selectDifficulty,
            selectCategory,
            difficultyStyle,
            currentName,
            filteredSlots,
            join
        }
    }
}
</script>

<style scoped>
</style>