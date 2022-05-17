<template>
  <div class="q-pa-md">
    <div>{{ forumTitle }}</div>
    <div class="column">
      <q-scroll-area style="height: 300px">
        <div v-for="post in posts" :key="post.post_id" class="q-gutter-y-md">
          <div style="border: 1px solid black">{{ post.title }}</div>
          <div style="border: 1px solid black">{{ post.content }}</div>
        </div>
      </q-scroll-area>
      <q-input outlined v-model="postTitle" label="Title" />
      <q-input v-model="post" filled type="textarea" label="Content" />
      <q-btn label="Post" @click="sendPost" />
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
    const $q = useQuasar();
    const forumTitle = ref("");
    const posts = ref([]);
    const post = ref("");
    const postTitle = ref("");
    const forumId = ref(null);
    const userId = ref(null);

    onBeforeMount(async () => {
      api.get(`api/v1/forum/${props.question_id}`).then((result) => {
        forumTitle.value = result.data.title;
        posts.value = result.data.posts;
      });

      api.get(`api/v1/forum/getid/${props.question_id}`).then((result) => {
        forumId.value = result.data;
      });
      userId.value = localStorage.getItem("currentUserID");
    });

    const sendPost = async () => {
      const postData = {
        forum_id: forumId.value,
        user_id: userId.value,
        title: postTitle.value,
        content: post.value,
      };
      api.post("api/v1/post/create", postData).then((result) => {
        if (result.data) {
          $q.notify({
            position: "top",
            color: "positive",
            message: "Posted",
          });

          post.value = "";
          postTitle.value = "";

          api.get(`api/v1/forum/${props.question_id}`).then((result) => {
            forumTitle.value = result.data.title;
            posts.value = result.data.posts;
          });
        } else {
          $q.notify({
            position: "top",
            color: "negative",
            message: "There was a problem, try again",
          });
        }
      });
    };

    return {
      forumTitle,
      posts,
      post,
      postTitle,
      sendPost,
    };
  },
};
</script>