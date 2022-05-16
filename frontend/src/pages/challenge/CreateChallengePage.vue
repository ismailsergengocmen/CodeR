<template>
    <div>
        <div class="fixed-top-center q-pa-md">
            <q-banner class="bg-grey text-white" dense>
                <span class="row justify-center">Create Question</span>
            </q-banner>
        </div>
        <div class="row bordered items-center justify-around q-pa-sm">
            <div>
                <span class="label bg-white text-black">Question Title</span>
                <q-input outlined v-model="questionTitle"/>
            </div>
            <q-toggle
                label="QuestionType"
                color="black"
                v-model="questionType"
                />
        </div>
        <div class="row bordered items-center justify-around q-pa-sm">
            <div>
                <span class="label bg-white text-black">Difficulty</span>
                <div class="q-gutter-sm column">
                    <q-radio right-label v-model="difficulty" val="easy" label="Easy" />
                    <q-radio right-label v-model="difficulty" val="medium" label="Medium" />
                    <q-radio right-label v-model="difficulty" val="hard" label="Hard" />
                    <q-radio right-label v-model="difficulty" val="extreme" label="Extreme" />
                </div>
            </div>    
            <div class="q-pa-md" style="max-width: 300px">
                <span class="label bg-white text-black">Question Content</span>
                <q-input v-model="questionContent" outlined type="textarea"/>
            </div>
        </div>
        <div class="row bordered items-center justify-around q-pa-sm">
            <div>
                <span class="label bg-white text-black">Categories</span>
                <div class="q-pa-md" style="max-width: 300px" >
                    <q-select
                    filled
                    v-model="categories"
                    multiple
                    :options="categoryOptions"
                    :loading="loading"
                    @virtual-scroll="onScroll"
                    />
                </div>
            </div>    
            <div v-if ="questionType == false">
                <span class="label bg-white text-black">Hints</span>
                <q-input v-model="hint1" label="Hint 1" />
                <q-input v-model="hint2" label="Hint 2" />
            </div>    
        </div>
        <span v-if ="questionType == false" class="label bg-grey text-white q-mx-md q-pa-xs row justify-center">Test-Cases</span>
        <q-separator class="q-ma-md" />
        <div class="q-pa-md">
            <span class="label bg-white text-black">TestCase Count</span>
            <div class="row full-width">
                <q-input
                    v-model.number="testCaseCount"
                    type="number"
                    filled
                    style="max-width: 200px"
                    min="1" 
                    max="10"
                    class="full-width q-pr-md"
                />
                <q-btn color="black" label="Add test cases" @click="generateTestCase" />
            </div>
        </div>
        <div class="row bordered items-center justify-around q-pa-sm" v-if ="questionType == false">
        <div class="row q-gutter-md" v-for="testCase in testCases" :key="testCase">
            <div class="q-mb-md">
                <span class="label bg-white text-black">Input</span>
                <q-input filled v-model="testCase.input"/>
            </div>
            <div class="q-mb-md">
                <span class="label bg-white text-black">Output</span>
                <q-input filled v-model="testCase.output" />
            </div> 
            <div>
                <span class="label bg-white text-black">Input Type</span>
                <q-input filled v-model="testCase.type" />
            </div> 
        </div> 
        </div>
        <div class="row justify-center q-mt-lg">
            <q-btn color="black" 
            label="Create" 
            @click = "createQuestion"/>
        </div>
    </div>
</template>

<script>
import { ref } from "vue"
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"

export default {
    name: "CreateChallengePage",
    props: {
        isComponent: {
            type: Boolean,
            default: false
        }
    },

    setup(props) {
        const $q = useQuasar();
        const router = useRouter();

        const questionType = ref(false);
        const questionTitle = ref('');
        const difficulty = ref('');
        const categories = ref([]);
        const questionContent = ref('');
        const testCaseCount = ref();
        const hint1 = ref('');
        const hint2 = ref('');
        const testCases = ref([]);
        const categoryOptions = [
        'Algorithms', 'Data Structures', 'Sorting', 'Programming Languages'
    ];

        const createChallenge = async (questionTitle, difficulty, categories, questionContent, testCases) => {
            console.log("createChallenge") 
            if(!questionTitle || !difficulty || !categories || !questionContent || !testCases || !hints){
                $q.notify({
                position:"top",
                color:"negative",
                message:"Fill all the necessary information"
                })
            }
            else{
                hints.value.push(hint1)
                hints.value.push(hint2)
                const questionData = {
                // user_id
                question_difficulty: difficulty,          
                question_title: questionTitle,
                question_content:questionContent,
                categories,
                hints,
                test_cases: testCases
                };
                api.post("/api/v1/question/challenge/create", questionData).then((response) => {
                if(!response.data){
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Something wrong"
                    })
                }
                else{
                    router.push('/home');
                }
                }).catch(()=> {
                $q.notify({
                    position:"top",
                    color:"negative",
                    message:"There was an error"
                })
                })
            }
        }

        const createNonCoding = async (questionTitle, difficulty, categories, questionContent) => {
            console.log("createNonCoding")
            console.log(questionTitle)
            console.log(difficulty)
            console.log(categories)
            console.log(questionContent)

            if(!questionTitle || !difficulty || !categories || !questionContent){
                $q.notify({
                position:"top",
                color:"negative",
                message:"Fill all the necessary information"
                })
            }
            else{
                const questionData = {
                // user_id
                    question_difficulty: difficulty,          
                    question_title: questionTitle,
                    question_content:questionContent,
                    categories,
                };
                api.post("/api/v1/question/noncoding/create", questionData).then((response) => {
                if(!response.data){
                    $q.notify({
                    position:"top",
                    color:"negative",
                    message:"Something wrong"
                    })
                }
                else{
                    router.push('/home');
                }
                }).catch(()=> {
                $q.notify({
                    position:"top",
                    color:"negative",
                    message:"There was an error"
                })
                })
            }
        }

        function createQuestion() {
            if(!props.isComponent){
                if (questionType.value) {
                    createNonCoding(questionTitle.value, difficulty.value, categories.value, questionContent.value)
                } else {
                    createChallenge(questionTitle.value, difficulty.value, categories.value, questionContent.value, testCases)
                }
            }
            else {
                const questionData = {
                    // user_id
                    question_difficulty: difficulty.value,          
                    question_title: questionTitle.value,
                    question_content: questionContent.value,
                    categories: categories.value,
                    hints: [hint1.value , hint2.value], 
                    test_cases: testCases.value
                };
                ctx.emit("addQuestion", questionData)
            }
        }

        function generateTestCase() {
            testCases.value = [];
            for (let i = 0; i < testCaseCount.value; i++) {
                testCases.value.push({input:ref(""), output:ref(""), type:ref("")})
            }
        }


        return {
            questionType,
            questionTitle,
            difficulty,
            categories,
            questionContent,
            testCaseCount,
            hint1,
            hint2,
            testCases,
            categoryOptions,
            createChallenge,
            createNonCoding,
            createQuestion,
            generateTestCase
        }
    },
}
</script>
