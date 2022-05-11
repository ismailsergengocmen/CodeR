<template>
    <div>
        <div>
            <div>
                <q-btn color="grey" label="Challenges" class="q-mr-lg"/>
                <q-btn color="grey" label="Solved Challenges" />
            </div>
            <div>
                <q-btn-group>
                    <q-btn color="grey" label="Easy" />
                    <q-btn color="grey" label="Medium" />
                    <q-btn color="grey" label="Hard" />
                    <q-btn color="grey" label="Extreme" />
                </q-btn-group>
            </div>
            <div>
                <q-btn-group>
                    <q-btn color="grey" label="Sorting" />
                    <q-btn color="grey" label="Data Structures" />
                    <q-btn color="grey" label="Algorithms" />
                    <q-btn color="grey" label="Programming Languages" />
                </q-btn-group>
            </div>
            <div>
                <q-input
                    v-model="search"
                    debounce="1000"
                    outlined
                    dense
                    :placeholder="$t('Search')"
                    color="secondary"
                >
                    <template v-slot:append>
                        <q-icon name="search" />
                    </template>
                </q-input>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: "Challenge Filter",
  props: {
      
  }
}
</script>

<style scoped>
.bordered { 
  border: 1px solid rgb(192, 192, 192);
  width: 50%;
  border-radius: 5px;
}
</style>