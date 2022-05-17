<template>
  <div class="q-pa-md">
    <div>{{ forumTitle }}</div>
    <div class="column">
      <div v-for="post in posts" :key="post.post_id" class="q-gutter-y-md">
        <div>{{ post.title }}</div>
        <div>{{ post.content }}</div>
      </div>

      <q-btn />
    </div>
  </div>
</template>

<script>
import { ref, onBeforeMount } from "vue";
import { api } from "../../boot/axios";
import { useQuasar } from "quasar";

export default {
  name: "ForumPage",
  props: ["question_id"],
  setup(props) {
    const forumTitle = ref("");
    const posts = ref([]);

    onBeforeMount(async () => {
      api.get(`api/v1/forum/${props.question_id}`).then((result) => {
        forumTitle.value = result.data.title;
        posts.value = result.data.posts;
      });
    });

    return {
      forumTitle,
      posts,
    };
  },
};
</script>