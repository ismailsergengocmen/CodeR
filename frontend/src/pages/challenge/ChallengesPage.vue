<template>
  <div>
    <challenge-filter/>
  </div>
</template>
<script>

import ChallengeFilter from "../../components/challenge/ChallengeFilter.vue"
export default {
  name: "ChallengesPage",
  components: {
    ChallengeFilter
  },
  setup() {
    return true
  },
}
</script>