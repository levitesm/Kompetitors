<template>
    <div>
        <div class="search-header hide-all">
            <div class=" fields-margin">
                <input id="what" class="search-input" :placeholder="$t('navbar.what')" v-model="what_field" @keyup.enter="search()" />
            </div>
            <div class="inp">
                <select id="country" class="search-input sel" v-model="selectedCountry" @keyup.enter="search()" @change="resetRegion()">
                    <option selected>France</option>
                    <option selected>USA</option>
                    <option selected>Australia</option>
                    <option selected>Russia</option>
                </select>
            </div>
            <div class="inp">
                <select id="region" class="search-input sel" v-model="selectedRegion" @keyup.enter="search()" >
                    <option selected>All Regions</option>
                    <option v-if="selectedCountry === 'France'" v-for="r in this.regionList" :value="r.region">{{r.region}}</option>
                </select>
            </div>
            <div class="inp">
                <input id="where" class="search-input" :placeholder="$t('navbar.where')" v-model="where_field" @keyup.enter="search()" />
            </div>
            <div class="sub_but">
                <input type="submit" class="submit-button2" :value="$t('navbar.search').toUpperCase()" @click="search()">
            </div>
            <div class="clear-all" @click="clear()">
                {{ $t('navbar.clear-all') }}
            </div>
            <div class="adv-search hide" @click="showAdvanced = !showAdvanced">
                <img class="adv-search-img" src="../../../content/images/adv-search.png" alt="adv-search">
                <div class="adv-search-label">{{ $t('navbar.advanced-search') }}</div>
            </div>
        </div>
        <div class="advanced-search-header hide" v-if="showAdvanced">
            <div class="advanced-search-hr"></div>
            <div @click="closeAdvanced()">
                <img class="close-img" src="../../../content/images/close.png" alt="close">
            </div>
            <div class="fields-margin row">
                <div class="adv-search-col">
                    <label class="advanced-search-label">{{ $t('navbar.number-employees') }}</label>
                    <div>
                        <input class="advanced-search-input" type="text" :placeholder="$t('navbar.from')" v-model="advSearchModel.empFrom" @keypress.enter="advancedSearch()">
                        <input class="advanced-search-input" type="text" :placeholder="$t('navbar.to')" v-model="advSearchModel.empTo" @keypress.enter="advancedSearch()">
                    </div>
                </div>
                <div class="adv-search-col">
                    <label class="advanced-search-label">{{ $t('navbar.transparency') }}</label>
                    <select class="search-input sel" v-model="advSearchModel.transparency">
                        <option v-bind:value="null">{{ $t('navbar.any') }}</option>
                        <option v-bind:value="true">{{ $t('navbar.transparent') }}</option>
                        <option v-bind:value="false">{{ $t('navbar.not-transparent') }}</option>
                    </select>
                </div>
                <div class="adv-search-col">
                    <label class="advanced-search-label">{{ $t('navbar.private-equity') }}</label>
                    <select class="search-input sel" v-model="advSearchModel.privateEquity">
                        <option v-bind:value="null">{{ $t('navbar.any') }}</option>
                        <option v-bind:value="true">{{ $t('navbar.has-private-equity') }}</option>
                        <option v-bind:value="false">{{ $t('navbar.no-private-equity') }}</option>
                    </select>
                </div>
                <div class="adv-search-col">
                    <label class="advanced-search-label">{{ $t('navbar.listed') }}</label>
                    <select class="search-input sel" v-model="advSearchModel.listed">
                        <option v-bind:value="null">{{ $t('navbar.any') }}</option>
                        <option v-bind:value="true">{{ $t('navbar.listed') }}</option>
                        <option v-bind:value="false">{{ $t('navbar.not-listed') }}</option>
                    </select>
                </div>
            </div>
            <div class="fields-margin">
                <input type="submit" class="results-btn" @click="advancedSearch()" :value="$t('navbar.show-results')" />
            </div>
        </div>
        <div class="icon-close close-ico" @click="close"></div>
    </div>
</template>

<script lang="ts" src="./jhi-search.component.ts">
</script>

<style scoped>
    .search-header {
        display: flex;
        flex-direction: row;
        background-color: white;
        padding: 22px 0;
        align-items: center;
    }
    .search-input {
        width: 100%;
        height: 48px;
        padding-left: 1em;
        background-color: #ffffff;
        border-style: solid;
        border-radius: 3px;
        border-color: #cccccc;
    }
    .inp {
        margin-left: -4px;
    }
    .submit-button2 {
        background-color: #6b48ff;
        color: #ffffff;
        border-style: none;
        border-radius: 0 3px 3px 0;
        border-color: #6b48ff;
        height: 48px;
        width: 165px;
        font-weight: bolder;
    }
    .sub_but {
        margin-left: -2px;
    }
    .clear-all {
        color: grey;
        margin-left: 10px;
        text-decoration: underline;
        cursor: pointer;
        white-space: nowrap;
    }
    .sel {
        border-width: 2px;
        appearance: none;
    }
    .adv-search {
        display: flex;
        flex-direction: row;
        align-items: baseline;
        white-space: nowrap;
        cursor: pointer;
    }
    .adv-search-img {
        width: 16px;
        height: 12px;
        margin: 0 10px 0 20px;
    }
    .advanced-search-header {
        background-color: white;
        display: flex;
        flex-direction: column;
    }
    .advanced-search-hr {
        border-top: 1px solid #DBE2E8;
    }
    .close-img {
        width: 16px;
        height: 16px;
        margin: 20px 120px 0 0;
        float: right;
    }
    .adv-search-label {
        color: #6B48FF;
        font-size: 14px;
        line-height: 22px;
    }
    .advanced-search-label {
        color: #262626;
        font-size: 14px;
        line-height: 22px;
    }
    .advanced-search-input {
        height: 48px;
        padding-left: 1em;
        background-color: #ffffff;
        border-style: solid;
        border-radius: 3px;
        border-color: #cccccc;
        width: 100px;
    }
    .fields-margin {
        margin-left: 110px;
    }
    .results-btn {
        height: 40px;
        width: 1140px;
        border-radius: 3px;
        border: none;
        background-color: #0D3F67;
        color: white;
        font-size: 14px;
        font-weight: 600;
        line-height: 22px;
        text-align: center;
        margin: 20px auto;
        outline: none;
    }
    .adv-search-col {
        margin-right: 20px;
        width: 220px;
    }
    @media screen and (max-width: 1360px) {
        .fields-margin{
            margin-left: 20px!important;
        }
        }
    @media screen and (max-width: 1199px) {
        .hide{
            display: none;
        }
    }
    @media screen and (max-width: 991px) {
        .search-header{
            display: block;
            width: 350px;
        }
        .close-ico {
            width: 350px;
        }
        .fields-margin {
            width: 300px;
        }
        .inp {
            margin-left: 20px;
            width: 300px;
        }
        .sub_but {
            margin: 20px;
            display: inline-block;
        }
        .clear-all {
            display: inline-block;
        }

    }
    @media screen and (max-width: 500px) {
        .search-header {
            width: 100%;
        }
        .close-ico {
            width: 100%;
        }
        .fields-margin {
            width: 90%;
        }
        .inp {
            width: 90%;
        }
        .clear-all {
            margin-left: 16%;
        }
        .close-ico {
            padding-right: 15px!important;
        }
    }

    .close-ico {
        cursor: pointer;
        color: #6B48FF;
        text-align: right;
        margin-top: -25px;
        padding-right: 5px;
    }
</style>
