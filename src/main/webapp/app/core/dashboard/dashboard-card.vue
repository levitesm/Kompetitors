<template>
    <div class="card-container">
        <div class="logo-container">
            <img :src="getLogo()" alt="logo" class="logo-image">
        </div>
        <div @click="showCompetitor(unit.competitorId)" class="competitor-name">{{ unit.competitorName }}</div>
        <div class="value-container">
            <div v-if="checkFlag">
                <div class="devv" :style="'background-color: ' + ((dev < 1.5)?'#008800':((dev < 3)?'#ffc107':'#C21F39'))" v-if="dev !== null">{{ dev.toFixed(1) }} </div>
                <div class="devv" style="background-color: indianred" v-else>NA </div>
            </div>
            <div class="val">
                <div class="value">{{ displayValue(currentStat.value) }}</div>
                <div class="value-unit">{{ displayUnit(currentStat.value) }}</div>
                <div class="value">{{ currentStat.unit }}</div>
            </div>

        </div>
    </div>
</template>

<script lang="ts" src="./dashboard-card.component.ts">
</script>

<style scoped>
    .card-container {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin: 0 2em;
    }
    .logo-container {
        width: 40px;
        height: 40px;
        margin: 10px;
        vertical-align: top;
    }
    .logo-image {
        height: 100%;
        width: 100%;
        object-fit: contain;
    }
    .competitor-name {
        height: min-content;
        color: #6b48ff;
        font-size: 20px;
        font-weight: 600;
        vertical-align: top;
        cursor: pointer;
        white-space: nowrap;
        overflow: hidden;
    }
    .value-container {
        display: flex;
        flex-direction: row;
        margin-left: auto;
        margin-right: 1em;
        padding-left: 10px;
    }
    .value {
        color: #262626;
        font-size: 20px;
        line-height: 20px;
        display: inline-block;
    }
    .value-unit {
        font-size: 20px;
        line-height: 20px;
        margin: 0 2px;
        color: #262626;
        display: inline-block;
    }
    .devv {
        margin-right: 1rem;
        padding: 0 1rem;
        font-size: 16px;
        font-weight: 600;
        border-radius: 2rem;
    }
    .val {
        width: 100px;
    }
</style>
