<template>
    <div class="small">
        <line-chart :chart-data="dataCollection" :options="options"></line-chart>
    </div>
</template>

<script lang="ts" src="./employees-chart.component.ts">
</script>

<style scoped>
    .small {
        margin: 15px auto;
    }
</style>
