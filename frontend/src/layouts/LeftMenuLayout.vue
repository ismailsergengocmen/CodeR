<template>
    <q-layout view="hHh Lpr lff" class="shadow-2 rounded-borders">
        <q-drawer
            v-model="drawer"
            :width="200"
            bordered
            behavior="desktop"
        >
            <left-menu @logout="logout"/>
      </q-drawer>   
      <q-page-container>
        <router-view />
      </q-page-container>
    </q-layout>
</template>

<script>
import LeftMenu from './LeftMenu.vue'
import { ref } from 'vue'
export default {
    components:{
        LeftMenu,
    },
  name: 'LeftMenuLayout',

   setup(ctx) {
       const drawer = ref(true);

       return{
           drawer
       }
    const logout = () => {
        ctx.emit('logout');
      }
      return {
        logout
      }
   },
}
</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
</style>