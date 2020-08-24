<template>
    <div>
        <h3>{{ $t('hr-tab.typologies.chart') }}</h3>
        <div class="chart">
            <doughnut-chart class="chart__container" :chart-data="dataCollection" :options="options"></doughnut-chart>
            <div class="chart__legend">
                <div v-for="entry in legend" class="chart__legend-entry">
                    <div :style="{'background-color': entry.color}" class="chart__legend-color" ></div>
                    <div>{{ localisationFromName(entry.name) }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./typology-chart.component.ts">
</script>

<style scoped lang="scss">
    .chart {
        display: flex;
        flex-direction: row;
        
        &__container {
            margin: 1rem 0;
            position: relative;
            height: 15rem;
            width: 15rem;
        }
        
        &__legend {
            padding: 2rem;
            flex: 1;
            display: flex;
            flex-direction: column;
            
            &-entry {
                display: flex;
                margin-bottom: 1rem;
                align-items: center;
            }
            
            &-color {
                width: 1rem;
                height: 1rem;
                margin-right: 1rem;
            }
        }
    }
</style>
