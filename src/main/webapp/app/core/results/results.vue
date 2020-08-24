<template>
    <div class="results-container">
        <div class="results_body">
            <p class="results_number">{{filteredGroups.length}} {{ filteredGroups.length !== 1 ? $t('results.results.plural-title') : $t('results.results.singular-title') }}</p>
            <div class="space" v-if="filteredGroups.length===0"></div>
            <div class="filter-sort-block">
                <div class="filter-btn-block" ref="filterBtnBlock">
                    <div class="filter-btn" @click="filterModel.isOpen = !filterModel.isOpen">
                        {{ $t('results.filter') + $t('results.options.' + filterModel.selected) }}
                        <img v-if="filterModel.isOpen" class="filter-arrow" src="../../../content/images/arrow-up-violet.png" alt="opened">
                        <img v-else class="filter-arrow" src="../../../content/images/arrow-down-white.png" alt="closed">
                    </div>
                    <div class="filter-select" v-show="filterModel.isOpen">
                        <div class="filter-option" :class="{'filter-option-active': filterModel.selected === option}"
                             v-for="option in Object.keys(filterModel.options)"
                             @click="applyFilter(option)">{{ $t('results.options.' + option) }}
                        </div>
                    </div>
                </div>
                <div class="filter-btn-block" ref="sortBtnBlock">
                    <div class="filter-btn" @click="sortModel.isOpen = !sortModel.isOpen">
                        {{ $t('results.sort') + $t('results.options.' + sortModel.selected) }}
                        <img v-if="sortModel.isOpen" class="filter-arrow" src="../../../content/images/arrow-up-violet.png" alt="opened">
                        <img v-else class="filter-arrow" src="../../../content/images/arrow-down-white.png" alt="closed">
                    </div>
                    <div class="filter-select" v-show="sortModel.isOpen">
                        <div class="filter-option" :class="{'filter-option-active': sortModel.selected === option}"
                             v-for="option in Object.keys(sortModel.options)"
                             @click="applySort(option)">{{$t('results.options.' + option) }}
                        </div>
                    </div>
                </div>
            </div>
            <div class="group-list" v-if="filteredGroups.length > 0">
                <div class="group-list__element" v-for="(item, i) in filteredGroups">
                    <div class="group-list__container">
                        <div class="group-name">
                            <div class="group-name__logo">
                                <img :src="getLogo(item)" alt="logo" class="group-name__img" @click=showCompetitor(item.maxcompetitorid)>
                            </div>
                            <div class="group-name__title">
                                <span class="group-name__title-name" @click=showCompetitor(item.maxcompetitorid)>
                                    {{formatName(item.name)}}
                                </span>
                                <span>{{item.countryname}}</span>
                            </div>
                        </div>
                        <div class="group-data">
                            <div class="group-data__item">
                                <span class="group-data__item-value">{{item.competitorscount}}</span>
                                <br>
                                <span class="group-data__item-name group-data__item-name--link"
                                      @click="goToAgencies(item.maxcompetitorid)"
                                >{{(item.competitorscount === 1)?$t('company'):$t('companies') }}</span>
                            </div>

                            <div class="group-data__item">
                                <span class="group-data__item-value">{{item.agencies}}</span>
                                <br>
                                <span class="group-data__item-name group-data__item-name--link"
                                      @click="goToAgencies(item.maxcompetitorid)"
                                >{{(item.agencies === 1)?$t('results.results.agency'):$t('results.results.agencies') }}</span>
                            </div>
                            <div class="group-data__item">
                                <span class="group-data__item-value">{{item.employees}}</span>
                                <br>
                                <span class="group-data__item-name">{{ $t('results.results.employees') }}</span>
                            </div>
                            <div class="group-data__item">
                                <div class="group-data__values">
                                    <span class="group-data__item-value" :class="{'grey': getLastRevenueMillions(item) === 0}">
                                        {{ getLastRevenueMillions(item) }}
                                    </span>
                                    <span class="group-data__item-unit" :class="{'grey': getLastRevenueMillions(item) === 0}">
                                        {{ getLastRevenueUnit(item) }}
                                    </span>
                                </div>
                                <br>
                                <span class="group-data__item-name">{{ $t('results.results.revenue') }}</span>
                            </div>
                        </div>
                    </div>
                    <div class="region-info" v-if="$data.region!=='' && $data.region !== 'All Regions'">
                        {{item.regionagencies}} {{(item.regionagencies===1)?$t('results.results.agency'):$t('results.results.agencies')}} {{$t('results.results.and')}}
                        {{item.regionemployees}} {{$t('results.results.employees')}} {{$t('results.results.in')}} {{$data.region}}
                    </div>
                    <div class="group-list__hr"></div>
                    <div class="group-list__container">
                        <div class="group-list__item">
                            <div class="checkboxes">
                                <div class="checkboxes__item checkboxes__item--green" v-if="item.transparency || item.listed">{{ $t('competitor.chips.transparency') }}</div>
                                <div class="checkboxes__item checkboxes__item--red" v-else>{{ $t('competitor.chips.no-transparency') }}</div>
                                <div class="checkboxes__container">
                                    <div class="checkboxes__item checkboxes__item--purple" v-if="item.listed">
                                        {{ $t('competitor.chips.listed') }}
                                    </div>
                                    <div class="checkboxes__item checkboxes__item--grey" v-if="item.independent">
                                        {{ $t('competitor.chips.independent') }}
                                    </div>
                                    <div class="checkboxes__item checkboxes__item--blue" v-if="item.privatecapital">
                                        {{ $t('competitor.chips.private-equity') }}
                                    </div>
                                </div>
                            </div>
                            <div class="rates">
                                <div class="rates__item" title="Glassdoor rating">
                                    <div class="rates__text rates__text--green" :class="{blocked : !item.glassdoor}">
                                        {{ item.glassdoor ? item.glassdoor.toFixed(1) : 0 }}
                                    </div>
                                    <img src="../../../content/images/glassdoor-logo.svg" class="rates__img" alt="glass">
                                </div>
                                <div class="rates__item" title="Viadeo rating">
                                    <div class="rates__text rates__text--orange" :class="{blocked : !item.viadeo}">
                                        {{ item.viadeo ? item.viadeo.toFixed(1) : 0 }}
                                    </div>
                                    <img src="../../../content/images/viadeo-logo.svg" class="rates__img" alt="via">
                                </div>
                            </div>
                        </div>
                        <div class="group-list__item">
                            <div class="stats">
                                <div class="stats__text">{{ $t('results.results.total-competitive-rate') }}</div>
                                <div class="stats__stars">
                                    <span v-for="i in Array(totalRate(item.totalrate)).keys()" class="icon-note star_ico_on"></span>
                                    <span v-for="i in Array(5 - totalRate(item.totalrate)).keys()" class="icon-note star_ico_off"></span>
                                </div>
                            </div>
                            <div class="stats">
                                <div class="stats__text">{{ $t('results.results.tech-competitive-rate') }}</div>
                                <div class="stats__stars">
                                    <span v-for="i in Array(techRate(item.techrate)).keys()" class="icon-note star_ico_on"></span>
                                    <span v-for="i in Array(5 - techRate(item.techrate)).keys()" class="icon-note star_ico_off"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="results_map">
            <cmp-map :offices="offices"></cmp-map>
        </div>
    </div>
</template>
<script lang="ts" src="./results.component.ts">
</script>
<style scoped lang="scss">
    @import '../../../content/scss/responsive';

    .results-container {
        display: flex;
        justify-content: flex-end;

        @media only screen and (max-width: $bp-largest) {
            justify-content: center;
        }
    }

    .results_body {
        padding-top: 2em;
        transition: all .2s;

        @media only screen and (max-width: $bp-large) {
            margin-left: 0;
        }
    }

    .results_map {
        width: 445px;
        height: 445px;
        margin-top: 1rem;
        margin-left: 1rem;
        transition: all .2s;

        @media only screen and (max-width: $bp-largest) {
            display: none;
        }
    }

    .map-container {
        height: 100%;
        width: 100%;
    }

    .map-container iframe {
        height: 100%;
        width: 100%;
    }

    .results_number {
        font-size: 20px;
        font-weight: 600;
        margin-left: 1.5rem;
    }

    .star_ico_off {
        margin-left: 2px;
        color: #dbe2e8;
        font-size: 25px;
    }

    .star_ico_on {
        margin-left: 2px;
        color: #1ee3cf;
        font-size: 25px;
    }

    .group-list {
        display: flex;
        flex-direction: column;
        width: 775px;

        @media only screen and (max-width: $bp-medium) {
            width: min-content;
        }

        &__element {
            padding: 1rem 1rem 1rem 1rem;
            background-color: #ffffff;
            margin-bottom: 3rem;
            display: flex;
            flex-direction: column;
        }

        &__container {
            display: flex;
            justify-content: right;

            @media only screen and (max-width: $bp-medium) {
                flex-direction: column;
            }
        }

        &__item {
            display: flex;
            width: 100%;
            justify-content: right;

            @media only screen and (max-width: $bp-medium) {
                justify-content: center;
            }

            @media only screen and (max-width: $bp-small) {
                margin-top: 1rem;
            }
        }

        &__hr {
            width: 100%;
            border-top: 1px solid rgba(0, 0, 0, .1);
            margin-bottom: 1rem;

            @media only screen and (max-width: $bp-small) {
                margin-top: 1rem;
            }
        }
    }

    .group-name {
        display: flex;
        margin: 1rem auto 1rem 1rem;

        &__logo {
            height: 60px;
            width: 70px;
            padding-left: 1em;
            cursor: pointer;
        }

        &__img {
            height: 100%;
            width: 100%;
            object-fit: contain;
        }

        &__title {
            display: flex;
            flex-direction: column;
            margin-left: 1.5rem;
        }

        &__title-name {
            color: #6b48ff;
            font-size: 20px;
            font-weight: 600;
            cursor: pointer;
        }
    }

    .group-data {
        display: flex;
        justify-content: space-evenly;

        &__item {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 1rem 0.7rem;

            @media only screen and (max-width: $bp-small) {
                margin: 1rem 0.3rem;
            }
        }

        &__values {
            display: flex;
            align-items: baseline;
        }

        &__item-value {
            font-size: 24px;
        }

        &__item-name {
            color: #000;
            font-size: 14px;

            &--link {
                color: #6b48ff;
                cursor: pointer;

                &:hover {
                    text-decoration: underline;
                }
            }
        }

        &__item-unit {
            font-size: 20px;
            font-weight: 200;
            white-space: nowrap;
        }

    }

    .checkboxes {
        display: flex;
        flex-direction: column;
        margin-left: 1rem;
        margin-right: auto;

        &__container {
            display: flex;
        }

        &__item {
            width: min-content;
            color: #ffffff;
            border-width: 2px;
            border-style: dashed;
            padding: 0.5em 1em;
            border-radius: 2.5em;
            font-size: 10px;
            font-weight: 600;
            white-space: nowrap;
            margin-bottom: 1rem;
            margin-right: .4rem;

            &--green {
                background-color: #1ee3cf;
                border-color: #1ee3cf;
            }

            &--red {
                background-color: #e60027;
                border-color: #e60027;
            }

            &--purple {
                background-color: #6b48ff;
                border-color: #6b48ff;
            }

            &--grey {
                background-color: #666;
                border-color: #666;
            }

            &--blue {
                background-color: #0d3f67;
                border-color: #0d3f67;
            }
        }
    }

    .rates {
        display: flex;
        flex-direction: column;
        justify-content: center;

        @media only screen and (max-width: $bp-small) {
            margin-right: .5rem;
        }

        &__item {
            margin-bottom: .3rem;
            display: flex;
        }

        &__text {
            font-weight: 700;
            line-height: 18px;

            &--orange {
                color: #f49d22;
            }

            &--green {
                color: #0caa41;
            }
        }

        &__img {
            width: 18px;
            height: 18px;
        }
    }

    .stats {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-around;
        margin: 0 1rem;

        &__text {
            font-size: 12px;
            white-space: nowrap;
        }

        &__stars {
            display: flex;
        }
    }

    .mini_m {
        font-size: 20px !important;
        font-weight: 200;
    }

    .grey {
        color: #cccccc !important;
        cursor: default;
    }

    .filter-sort-block {
        display: flex;
        flex-direction: row;
    }

    .filter-btn-block {
        margin-bottom: .8rem;
        margin-left: 1.5rem;
        position: relative;
        width: min-content;
    }

    .filter-btn {
        display: flex;
        flex-direction: row;
        align-items: center;
        border: 2px solid #DBE2E8;
        padding: 5px 20px 5px 20px;
        border-radius: 3px;
        color: #666666;
        background-color: rgb(250, 250, 250);
        white-space: nowrap;
    }

    .filter-btn:hover {
        border: 2px solid #6B48FF !important;
        cursor: pointer;
    }

    .filter-arrow {
        height: 12px;
        width: 18px;
        margin-left: 10px;
    }

    .filter-select {
        background-color: white;
        position: absolute;
        top: 43px;
        box-shadow: rgba(0, 0, 0, 0.1) 0 8px 16px 0, rgba(0, 0, 0, 0.08) 0 1px 4px 0;
        width: 100%;
    }

    .filter-option {
        text-align: center;
        margin: 5px 0 5px 0;
        color: rgb(117, 117, 117);
        font-size: 14px;
    }

    .filter-option-active {
        color: black;
    }

    .filter-option:hover {
        background-color: rgb(250, 250, 250);
        cursor: pointer;
        color: black;
    }

    .region-info {
        margin-right: 1rem;
        text-align: right;
        color: #666666;

        @media only screen and (max-width: $bp-medium) {
            text-align: center;
        }
    }

    .space {
        width: 770px;
    }
</style>
