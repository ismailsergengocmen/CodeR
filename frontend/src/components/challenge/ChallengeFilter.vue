<template>
    <div class="q-gutter-md">
        <div class="q-gutter-md">
            <q-btn color="grey" label="Challenges" class="q-mr-lg"/>
            <q-btn color="grey" label="Solved Challenges" />
        </div>
        <div class="q-gutter-md row">
            <div v-for="difficulty in difficulties" :key="difficulty.name" >
                <q-btn :color="difficultyStyle(difficulty.value)" :label="difficulty.name" @click="selectDifficulty(difficulty.value)" />
            </div>
        </div>
        <div class="q-gutter-md row">
            <div v-for="category in categories" :key="category.name" >
                <q-btn color="grey" :label="category.name" @click="selectCategory(category.value)"/>
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
    </div>
</template>

<script>
import { ref, watch } from "vue"

export default {
    name: "ChallengeFilter",
    props: {},
    setup(props,ctx){
        const currentDifficulties = ref(["easy"]);
        const currentCategories = ref([]);
        const currentName = ref("");

        const difficulties = [
            // name frontendde görülen, value backendde kullanılan
            {name: "Easy", value: "easy"}, 
            {name: "Medium", value: "medium"}, 
            {name: "Hard", value: "hard"},
            {name: "Extreme", value: "extreme"}
        ];
        const categories = [
            {name: "Sorting", value: "sorting"},
            {name: "Data Structures", value: "data_structures"},
            {name: "Algorithms", value: "algorithms"},
            {name: "Programming Languages", value: "programming_languages"}
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
            categories,
            currentCategories,
            selectDifficulty,
            selectCategory,
            difficultyStyle,
            currentName
        }
    }
}
</script>

<style scoped>
</style>