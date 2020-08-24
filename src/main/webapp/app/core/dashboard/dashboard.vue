<template>
    <div>
        <div class="phone">
            <img src="../../../content/images/turn-phone.png" style="width: 100%; filter: invert()" alt="logo">
        </div>

        <div class="dashboard-body">
            <div class="dashboard-item">
                <div class="competitor-block">
                    <div class="logo-container">
                        <img :src="getLogo()" alt="logo" class="logo-image">
                    </div>
                    <div class="competitor-title">{{ referenceCompetitor ? referenceCompetitor.name : '' }}</div>
                </div>
                <div class="tabs-container">
                    <div class="menu">
                        <div class="tabs">
                        <span v-for="tab in tabs" class="tab-item" @click="setTab(tab[0])"
                              :class="{selected : tab[0] === selectedTab}" v-if="hasTabAccess(tab[0])">
                            {{ $t(tab[1]) }}
                        </span>
                        </div>
                        <div class="modes">
                            <div class="stat-year" v-if="statistics.finance && statistics.finance.length > 0 && selectedTab === 'finance'">
                                {{ statistics.finance[0].year + ' year'}}
                            </div>
                            <span :class="{selected: isCompare}" @click="selectCompare" class="tab-item">
                                {{ $t('dashboard.compare') }}
                            </span>
                            <span :class="{selected: isFight}" @click="selectFight" class="tab-item">
                                {{ $t('dashboard.fight') }}
                            </span>
                            <span :class="{selected: isMap}" @click="selectMap" class="tab-item">
                                Map
                            </span>
                        </div>
                    </div>
                </div>
                <hr>
                <div class="statistics-selector" v-if="selectedTab && isCompare">
                    <dashboard-statistic-item v-for="stat in referenceStatistics.statistics" :statisticItem="stat" :key="stat.name"
                                              :isActive="selectedStat ? selectedStat.name === stat.name : false"
                                              @selectStat="selectStat">
                    </dashboard-statistic-item>
                </div>
                <div class="fight-selector" v-if="selectedTab && isFight">
                    <dashboard-fight :currentStatistics="currentStatistics"></dashboard-fight>
                </div>
                <div class="fight-selector" v-if="selectedTab && isMap">
                    <dashboard-map :allStatistics="currentStatistics"></dashboard-map>
                </div>
                <div class="not-found" v-if="!selectedTab">Not found</div>
            </div>
            <dashboard-card
                v-if="selectedStat && isCompare && check_flag"
                :unit="referenceStatistics" :selectedStat="selectedStat" :dev="statDeviation(referenceStatistics)" :checkFlag="check_flag">
            </dashboard-card>
            <div v-if="selectedStat && isCompare" class="dashboard-item blocks">

                <div class="dashboard-item-block">
                    <div class="block-header">
                        <img class="arrow" src="../../../content/images/arrow-grow-black.png" alt="up">
                        {{ $t('dashboard.finance.inferior') }}
                        <span class="dashboard-comp-name">{{ referenceCompetitor.name }}</span>
                    </div>
                    <dashboard-card v-for="unit in inferiorStatistics" :key="unit.id"
                                    v-if="statNotEmpty(unit)"
                                    :unit="unit" :selectedStat="selectedStat" :dev="statDeviation(unit)" :checkFlag="check_flag">
                    </dashboard-card>
                </div> <div class="data_flag_section"><span class="data_flag" :class="check_flag?'selec':''" @click="checkFlag()">Check</span></div>
                <div class="dashboard-item-block">
                    <div class="block-header">
                        <img class="arrow" style="transform: scale(1, -1)" src="../../../content/images/arrow-grow-black.png" alt="up">
                        {{ $t('dashboard.finance.superior') }}
                        <span class="dashboard-comp-name">{{ referenceCompetitor.name }}</span>
                    </div>
                    <dashboard-card v-for="unit in superiorStatistics" :key="unit.id"
                                    v-if="statNotEmpty(unit)"
                                    :unit="unit" :selectedStat="selectedStat" :dev="statDeviation(unit)" :checkFlag="check_flag">
                    </dashboard-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./dashboard.component.ts">
</script>

<style scoped>
    .dashboard-body {
        margin: 95px 110px 0 110px;
        transition: all .1s;
    }

    .dashboard-item {
        background-color: #ffffff;
        margin-bottom: 2em;
    }

    .competitor-block {
        display: flex;
        flex-direction: row;
        padding: 2em 2em 1em 2em;
        vertical-align: top;
    }

    .logo-container {
        width: 100px;
        height: 100px;
        margin-right: 1em;
        vertical-align: top;
    }

    .logo-image {
        height: 100%;
        width: 100%;
        object-fit: contain;
    }

    .competitor-title {
        font-size: 34px;
        font-weight: 600;
        margin-top: 20px;
    }

    .tabs-container {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .tabs {

    }

    .stat-year {
        font-weight: 600;
        margin: 0 2rem 0 auto;
        display: inline-block;
    }

    .tab-item {
        cursor: pointer;
        color: #8c8c8c;
        padding: 0.8em 1.5em;
        border-radius: 2.5em;
        margin-right: 1em;
        border-style: solid;
        border-width: 2px;
        border-color: #ffffff;
        font-size: 14px;
        font-weight: 600;
    }

    .tab-item:hover {
        border-color: #dbe2e8;
    }

    .selected {
        color: #ffffff;
        background-color: #6b48ff;
        border-color: #6b48ff;
        cursor: default;
    }

    .statistics-selector {
        display: grid;
        grid-template-columns: repeat(5, 1fr);
        grid-row-gap: 10px;
        padding-bottom: 2em;
    }

    .not-found {
        margin-left: 3rem;
        padding-bottom: 1rem;
    }

    .blocks {
        display: flex;
        flex-direction: row;
        padding-bottom: 1em;
    }

    .dashboard-item-block {
        width: 50%;
    }

    .arrow {
        height: 15px;
        width: 15px;
        margin-right: 5px;
    }

    .block-header {
        color: #262626;
        font-size: 24px;
        font-weight: 600;
        line-height: 35px;
        padding: 0.5em 1.5em;
        white-space: nowrap;
        display: inline-block;
    }

    .phone {
        background-color: #6B48FF;
        display: none;
    }

    @media screen and (max-width: 1300px) {
        .dashboard-body {
            margin: 0;
        }
    }

    @media screen and (max-width: 1000px) {
        .statistics-selector {
            grid-template-columns: repeat(4, 1fr);
        }

        .dashboard-comp-name {
            display: none;
        }
    }

    @media screen and (max-width: 768px) {
        .statistics-selector {
            grid-template-columns: repeat(3, 1fr);
        }
    }

    @media screen and (max-width: 500px) {
        .dashboard-body {
            display: none !important;
        }

        .phone {
            display: block;
            padding: 3em;
        }
    }

    .menu {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        width: 100%;
        padding: 0 2em;
        margin-top: 1rem;
        margin-bottom: 1rem;
    }
    .data_flag_section{
        display: inline-block;
        text-align: right;
        padding-top: 1rem;
    }
.data_flag {
    border-style: solid;
    border-width: 3px;
    border-radius: 2rem;
    padding: 0px 1rem;
    color: #6B48FF;
    border-color: #6B48FF;
    cursor: pointer;
}
    .selec {
        color: white;
        background-color: #6B48FF;
    }
</style>
