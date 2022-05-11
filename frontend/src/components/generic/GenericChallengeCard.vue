<template>
  <div class="row bordered items-center" style="height: 150px">
    
  </div>
</template>

<script>
export default {
  name: "GenericChallengeCard",
  props: {
    questionInfo:Object
  },
  setup(props, ctx) {
  },
}
</script>

<style scoped>
.bordered { 
  border: 1px solid rgb(192, 192, 192);
  width: 50%;
  border-radius: 5px;
}
</style>