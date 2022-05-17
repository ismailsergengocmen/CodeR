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

        <div>
            <q-scroll-area
                visible
                style="height: 300px;"
                class="col"
                ref="firstRef"
                >
                <div v-for="challenge in challenges" :key="challenge.interview_id" class="q-pa-sm">
                    <div class="row justify-content">
                        <div>
                            <span class="label bg-white text-black"> {{ challenge.name }} </span>
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
                            <q-btn label="Begin" :to="`${question.question_id}`"/>
                        </div>
                    </div>
                </div> 
            </q-scroll-area>
        </div>
    </div>
</template>

<script>
import { ref, watch } from "vue"

export default {
    name: "ChallengesPage",
    props: {},
    setup(props){
        const currentDifficulties = ref(["easy"]);
        const currentCategories = ref([]);
        const currentName = ref("");

        const questions = ref([]);

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
        }

        function selectCategory(category){
            if(!currentCategories.value.some(data => data == category))
                currentCategories.value.push(category);
            else
                currentCategories.value = currentCategories.value.filter(data => data != category)
        }

        function difficultyStyle(difficulty){
            if(currentDifficulties.value.some(data => data === difficulty))
                return "black"
            return "grey"    
        }

        watch([currentDifficulties,currentCategories], ([newDifficulties,newCategories]) => {
            search(newDifficulties,newCategories)
        })

        const search = (newDifficulties, newCategories) => {

            let customCategories = ""
            let customDifficulties = ""

            for(let i = 0; i < categories.length; i++){
                const val = categories[i].value
                if(i == categories.length - 1)
                    customCategories = customCategories + val
                else    
                    customCategories = customCategories + val + ","
            }

            for(let i = 0; i < difficulties.length; i++){
                const val = difficulties[i].value
                if(i == difficulties.length - 1)
                    customDifficulties = customDifficulties + val
                else    
                    customDifficulties = customDifficulties + val + ","
            }

            api.get(`/api/v1/question/challenge/create?category=${customCategories}&difficulty=${customDifficulties}`).then((response)=>{
                if(response.data)
                    questionsID.value.push(response.data)
                else{
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"There was an error creating the question. Please try it again"
                    })
                }    
            })
        }

        return{
            currentDifficulties,
            difficulties,
            categories,
            currentCategories,
            selectDifficulty,
            selectCategory,
            difficultyStyle,
            currentName,
            questions
        }
    }
}
</script>

<style scoped>
</style>