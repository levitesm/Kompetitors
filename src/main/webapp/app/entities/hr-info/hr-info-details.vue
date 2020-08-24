<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="hrInfo">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.hrInfo.detail.title')">HrInfo</span> {{hrInfo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.interviewsNumber')">Interviews Number</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.interviewsNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.recrutmentTime')">Recrutment Time</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.recrutmentTime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.reviewedCvPercent')">Reviewed Cv Percent</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.reviewedCvPercent}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.hrDetails')">Hr Details</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.hrDetails}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.vacanciesUrl')">Vacancies Url</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.vacanciesUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.hrSpecialistsNumber')">Hr Specialists Number</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.hrSpecialistsNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.glassdoorRate')">Glassdoor Rate</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.glassdoorRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.viadeoRate')">Viadeo Rate</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.viadeoRate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.glassdoorUrl')">Glassdoor Url</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.glassdoorUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.viadeoUrl')">Viadeo Url</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.viadeoUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.cooptationPremiumAmount')">Cooptation Premium Amount</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.cooptationPremiumAmount}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.juniorSalary')">Junior Salary</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.juniorSalary}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.averageSalary')">Average Salary</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.averageSalary}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.signingIncentives')">Signing Incentives</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.signingIncentives}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.averageContractDuration')">Average Contract Duration</span>
                    </dt>
                    <dd>
                        <span>{{hrInfo.averageContractDuration}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.hrInfo.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="hrInfo.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: hrInfo.competitor.id}}">{{hrInfo.competitor.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="hrInfo.id" :to="{name: 'HrInfoEdit', params: {hrInfoId: hrInfo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./hr-info-details.component.ts">
</script>
