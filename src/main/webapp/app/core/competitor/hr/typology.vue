<template>
    <div>
        <div v-if="loading">
            <custom-spinner></custom-spinner>
        </div>
        <div v-else>
            <typology-chart v-if="chartTypologies.length > 0" :typologies="chartTypologies"></typology-chart>
            <typology-table :typologies="typologies" @onUpdate="updateTypologies('updated', $event)"></typology-table>
        </div>
    </div>
</template>

<script lang="ts" src="./typology.component.ts">
</script>

<style scoped>

</style>
