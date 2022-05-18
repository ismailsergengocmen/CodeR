<template>
  <div class="q-gutter-md">
    <!-- <div class="q-gutter-md">
      <q-btn color="grey" label="Challenges" class="q-mr-lg" />
      <q-btn color="grey" label="Solved Challenges" />
    </div> -->
    <div class="q-gutter-md row">
      <div v-for="difficulty in difficulties" :key="difficulty.name">
        <q-btn
          :color="difficultyStyle(difficulty.value)"
          :label="difficulty.name"
          @click="selectDifficulty(difficulty.value)"
        />
      </div>
    </div>
    <div class="q-gutter-md q-ma-md row">
      <div v-for="category in categories" :key="category.name">
        <q-btn
          :color="categoryStyle(category.value)"
          :label="category.name"
          @click="selectCategory(category.value)"
        />
      </div>
    </div>
    <div>
      <q-btn label="Apply Filters" @click="applyFilter" class="q-ma-md" />
    </div>

    <div>
      <q-scroll-area visible style="height: 300px" class="col" ref="firstRef">
        <div
          v-for="challenge in challenges"
          :key="challenge.interview_id"
          class="q-pa-sm"
        >
          <div class="row justify-content">
            <div>
              <span class="label bg-white text-black">
                {{ challenge.question_title }}
              </span>
            </div>
            <div>
              <q-separator vertical inset />
            </div>
            <div>
              <q-separator vertical inset />
            </div>
            <div>
              <q-btn label="Begin" :to="`challenge/${challenge.question_id}`" />
            </div>
          </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script>
import { ref, onBeforeMount } from "vue";
import { api } from "../../boot/axios";

export default {
  name: "ChallengesPage",
  props: {},
  setup(props) {
    const currentDifficulties = ref([]);
    const currentCategories = ref([]);
    const challenges = ref([]);

    const difficulties = [
      // name frontendde görülen, value backendde kullanılan
      { name: "Easy", value: 1 },
      { name: "Medium", value: 2 },
      { name: "Hard", value: 3 },
      { name: "Extreme", value: 4 },
    ];

    const categories = [
      { name: "Sorting", value: "sorting" },
      { name: "Data Structures", value: "data_structures" },
      { name: "Algorithms", value: "algorithms" },
      { name: "Programming Languages", value: "programming_languages" },
    ];

    onBeforeMount(() => {
      api.get(`api/v1/question/all`).then((result) => {
        challenges.value = result.data;
      });
    });

    function selectDifficulty(difficulty) {
      if (!currentDifficulties.value.some((data) => data === difficulty)) {
        currentDifficulties.value.push(difficulty);
      } else {
        currentDifficulties.value = currentDifficulties.value.filter(
          (data) => data != difficulty
        );
      }
    }

    function selectCategory(category) {
      if (!currentCategories.value.some((data) => data == category)) {
        currentCategories.value.push(category);
      } else {
        currentCategories.value = currentCategories.value.filter(
          (data) => data != category
        );
      }
    }

    function difficultyStyle(difficulty) {
      if (currentDifficulties.value.some((data) => data === difficulty))
        return "black";
      return "grey";
    }

    function categoryStyle(category) {
      if (currentCategories.value.some((data) => data === category))
        return "black";
      return "grey";
    }

    const search = async () => {
      let customCategories = "";
      let customDifficulties = "";

      for (let i = 0; i < currentCategories.value.length; i++) {
        customCategories = customCategories + currentCategories.value[i] + ",";
      }

      for (let i = 0; i < currentDifficulties.value.length; i++) {
        customDifficulties =
          customDifficulties + currentDifficulties.value[i] + ",";
      }

      let endpoint = `/api/v1/question/all`;
      if (
        currentCategories.value.length > 0 &&
        currentDifficulties.value.length > 0
      ) {
        endpoint += `?category=${customCategories.slice(
          0,
          -1
        )}&difficulty=${customDifficulties.slice(0, -1)}`;
      } else if (currentCategories.value.length > 0) {
        endpoint += `?category=${customCategories.slice(0, -1)}`;
      } else if (currentDifficulties.value > 0) {
        endpoint += `?difficulty=${customDifficulties.slice(0, -1)}`;
      }

      api.get(endpoint).then((response) => {
        if (response.data) {
          challenges.value = response.data;
        }
      });
    };

    const applyFilter = async () => {
      await search();
    };

    return {
      currentDifficulties,
      difficulties,
      categories,
      currentCategories,
      selectDifficulty,
      selectCategory,
      difficultyStyle,
      categoryStyle,
      challenges,
      applyFilter,
    };
  },
};
</script>

<style scoped>
</style>