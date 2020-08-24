<template>
    <div>
        <div class="wrapper">
            <div class="header">
                <competitive-rate :title="$t('technology-tab.information.comp-rate')" :rate="rate"></competitive-rate>
                <div class="vertical-line"></div>
                <div>
                    <span class="numbers_titles">{{ $t('technology-tab.information.tech-spec') }}</span>
                    <br>
                    <span class="numbers_values">{{ techInfo ? techInfo.techSpecialistsNumber : 0 }}</span>
                </div>
                <div class="vertical-line"></div>
                <head-of
                    :title="$t('technology-tab.information.cto')" :name="headDisplayName"
                    :linkedinUrl="headCTO && headCTO.linkedPage ? headCTO.linkedPage : ''">
                </head-of>
                <div class="icon-edit suggest_link" :class="{'no-access': !hasAccess(TECH_EDIT)}" @click="showEditCTO()">
                    {{ $t('competitor.bottom-main-section.suggest-changes') }}
                </div>
            </div>

            <div class="sections">
                <technology-section :title="$t('technology-tab.information.techCompetancies')"
                                    collectionName="techCompetancies"
                                    :dictionaryService="listCompetanciesService"
                                    :techService="techCompetanciesService"
                                    @addTechnology="openAddModal"
                ></technology-section>
                <technology-section :title="$t('technology-tab.information.techPractices')"
                                    collectionName="techPractices"
                                    :dictionaryService="listPracticesService"
                                    :techService="techPracticesService"
                                    @addTechnology="openAddModal"
                ></technology-section>
                <technology-section :title="$t('technology-tab.information.techTools')"
                                    collectionName="techTools"
                                    :dictionaryService="listToolsService"
                                    :techService="techToolsService"
                                    @addTechnology="openAddModal"
                ></technology-section>
                <technology-section :title="$t('technology-tab.information.techServices')"
                                    collectionName="techServices"
                                    :dictionaryService="listServicesService"
                                    :techService="techServicesService"
                                    @addTechnology="openAddModal"
                ></technology-section>
                <technology-section :title="$t('technology-tab.information.techPartners')"
                                    collectionName="techPartners"
                                    :dictionaryService="listTechPartnersService"
                                    :techService="techPartnersService"
                                    @addTechnology="openAddModal"
                ></technology-section>
                <technology-section :title="$t('technology-tab.information.techProjects')"
                                    collectionName="techProjects"
                                    :dictionaryService="listProjectTypesService"
                                    :techService="techProjectsService"
                                    @addTechnology="openAddModal"
                ></technology-section>
            </div>
        </div>
        <technology-add-modal :entity="entity" :dictionary="dictionaries[entity]"></technology-add-modal>
        <technology-edit-cto-modal :headCTO="headCTO" :techInfo="techInfo" @updateTechInfo="updateTechInfo"></technology-edit-cto-modal>
    </div>
</template>

<script lang="ts" src="./technology-general.component.ts"></script>

<style scoped>
    .vertical-line {
        height: 50px;
        border: 1px solid #D6DADF;
        margin: 30px 5% 0 50px;
    }
    .wrapper {
        /*margin: 0 0 20px 75px;*/
    }
    .header {
        display: flex;
        flex-direction: row;
        align-items: flex-start;
    }
    .sections {
        margin-top: 40px;
        display: flex;
        flex-wrap: wrap;
    }
    .suggest_link {
        color: #6b48ff;
        font-size: 14px;
        cursor: pointer;
        margin: auto 25px 0 auto;
        width: min-content;
        white-space: nowrap;
    }
</style>
