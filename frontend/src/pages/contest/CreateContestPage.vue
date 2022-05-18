<template>
    <div class="full-height">
        <div class="fixed-top-center q-pa-md">
            <q-banner class="bg-grey text-white" dense>
                <span class="row justify-center">Create Contest</span>
            </q-banner>
        </div>
        <div class="row bordered justify-around q-pa-sm">
            <div>
                <span class="label bg-white text-black">Contest Name</span>
                <q-input outlined v-model="contestName"/>
            </div>
            <div>
                <span class="label bg-white text-black">Description</span>
                <q-input outlined v-model="description"/>
            </div>
        </div>
        <div class="row bordered q-pa-md justify-around">
            <div name= "Set time button and date/time objects">
                <q-input filled v-model="startTime">
                    <template v-slot:prepend>
                        <q-icon name="event" class="cursor-pointer">
                            <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                                <q-date v-model="startTime" mask="YYYY-MM-DDTHH:mm">
                                    <div class="row items-center justify-end">
                                        <q-btn v-close-popup label="Close" color="primary" flat />
                                    </div>
                                </q-date>
                            </q-popup-proxy>
                        </q-icon>
                    </template>
                    <template v-slot:append>
                        <q-icon name="access_time" class="cursor-pointer">
                            <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                                <q-time v-model="startTime" mask="YYYY-MM-DDTHH:mm" format24h>
                                    <div class="row items-center justify-end">
                                        <q-btn v-close-popup label="Close" color="primary" flat />
                                    </div>
                                </q-time>
                            </q-popup-proxy>
                        </q-icon>
                    </template>
                </q-input>
            </div>
            <div>
                <span class="label bg-white text-black">Duration</span>
                <q-input outlined v-model="duration"/>
            </div>
            <div>
                <span class="label bg-white text-black">Categories</span>
                <q-select
                    filled
                    v-model="categories"
                    multiple
                    :options="categoryOptions"
                />
            </div>
        </div>
        <div name="Create Question Button" class="row justify-center q-mt-xl">
            <q-btn @click="dialog = true" color="black" label="Create Question" />
        </div>
        <div>
            <q-scroll-area
                visible
                style="height: 300px;"
                class="col"
                ref="firstRef"
                >
                <div v-for="question in questions" :key="question.question_id" class="q-pa-sm">
                    <div class="row justify-content">
                        <div>
                            <span class="label bg-white text-black"> {{ question.question_title }} </span>
                        </div>
                        <div>
                            <q-separator vertical inset />
                        </div>
                        <div>
                            <span class="label bg-white text-black"> {{ question.question_difficulty }} </span>
                        </div>
                    </div>
                </div> 
            </q-scroll-area>
            <q-dialog
                v-model="dialog"
                full-width
                full-height
                >
                <q-card>
                    <CreateChallenge @addQuestion="addQuestions" :isComponent="true"/>
                    <q-card-actions align="right" class="bg-white black">
                        <q-btn flat label="Close" v-close-popup/>
                    </q-card-actions>
                </q-card>
            </q-dialog>
        </div>
        <div class="row justify-around">
        <q-btn @click="createContest" label="Create"/>
        </div>
    </div>
</template>

<script>
import { ref, ctx } from "vue"
import { useRouter } from "vue-router"
import { useQuasar } from "quasar"
import { api } from "../../boot/axios"
import CreateChallenge from "../challenge/CreateChallengePage.vue"

export default {
    name: "CreateContestPage",
    components: {
        CreateChallenge
    },
    setup(props, ctx) {
        const $q = useQuasar();
        const router = useRouter();

        // Parameters
        const categoryOptions = [
        'Algorithms', 'Data Structures', 'Sorting', 'Programming Languages'];

        // Inner - variables
        const dateButton = ref(false)
        const dialog = ref(false)
        const questions = ref([])
        const questionsID = ref([])

        // API
        const contestName = ref('');
        const startTime = ref(null);
        const finalTime = ref('');
        const duration = ref('');
        const description = ref('');
        const categories = ref([]);

        const createContest = async () => {
            if(!contestName.value || !startTime.value || !duration.value || !categories.value){
                $q.notify({
                position:"top",
                color:"negative",
                message:"Fill all the necessary information"
                })
            }
            else{
                finalTime.value = startTime.value
                await getQuestionIDs()
                const contestData = {
                    user_id: localStorage.getItem("currentUserID"),
                    contest_name: contestName.value,          
                    description: description.value,
                    start_time: finalTime.value,
                    duration: duration.value,
                    category: categories.value,
                    question_ids: questionsID.value,
                    sponsors: []
                };

                api.post("/api/v1/contest/create", contestData).then((response) => {
                    if(!response.data){
                        $q.notify({
                        position:"top",
                        color:"negative",
                        message:"Something wrong"
                        })
                    }
                    else{
                        router.push('/menu');
                    }
                }).catch(()=> {
                    $q.notify({
                        position:"top",
                        color:"negative",
                        message:"There was an error while creating contest"
                    })
                })
            }
        }

        const addQuestions = (questionData) => {
            questions.value.push(questionData)
        }

        const getQuestionIDs = async () => {
            questionsID.value = []
            for (let i = 0; i < questions.value.length; i++){
                api.post("/api/v1/question/challenge/create", questions.value[i]).then((response)=>{
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
        } 

        return {
            dialog,
            questions,
            contestName,
            description,
            startTime,
            finalTime,
            duration,
            categories,
            categoryOptions,
            createContest,
            addQuestions,
        }
    }
}
</script>