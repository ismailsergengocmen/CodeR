<template>
  <div> 
    <div class="column">
    </div>
    <div class="q-pa-md q-mx-xs row q-gutter-lg">
      <router-link :to="section.route" v-for="section in filteredSections" :key="section.name" class="my-card">
        <q-card class="bg-secondary text-white">
          <q-card-section align="center" style="height:120px">
            <div class="text-h6">{{ $t(section.name) }}</div>
            <q-icon size="lg" :name="section.icon" />
          </q-card-section>
        </q-card>
      </router-link>
    </div>
  </div>
</template>

<script>
import { computed, ref, watch } from 'vue'
import { useQuasar } from 'quasar'

export default {
  name: "HomePage",

  setup(props, ctx) {
    const $q = useQuasar();
    const riskless = ref(true);

    const isMobile = computed(() => {
      return $q.screen.width < 800;
    });

    const open = ref(!isMobile.value);

    watch(isMobile, () => {
      open.value = !isMobile.value;
    })

    const toggleDrawer = () => {
      open.value = !open.value
      ctx.emit('toggleDrawer');
    }

    const filteredSections = computed(() => {
      return sections.value.filter(item => {
        if (!item.disable) {
          return item
        }
      });
    })

    const sections = computed(() => {
      return [
      {
        name: "Challenges",
        icon: "mdi-book-open-variant",
        route: "/~/challenges",
      },
      {
        name: "Contests",
        icon: "mdi-needle",
        route: "/~/diagnovir"
      },
      {
        name: "Interviews",
        icon: "mdi-ambulance",
        route: "/~/health"
      }
    ]
    })

    return {
      riskless,
      filteredSections,
      toggleDrawer,
      isMobile
    }
  },
}
</script>

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 200px

a
  text-decoration: none
</style>
