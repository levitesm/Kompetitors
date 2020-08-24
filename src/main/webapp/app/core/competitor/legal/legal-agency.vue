<template>
    <div class="block">
        <div v-text="getRegion()" class="city"></div>
        <div v-text="address()"></div>
        <span>{{post()}}</span><span class="city">{{office.cityAsText}}</span>
    </div>
</template>

<script lang="ts" src="./legal-agency.component.ts">
</script>

<style scoped>
    .block {
        width: 28%;
        margin-left: 45px;
        margin-bottom: 70px;
        padding-left: 10px;
        border-left-style: solid;
        border-width: 1px;
        border-color: #cccccc;
    }
    .city {
        font-weight: bold;
    }
</style>
