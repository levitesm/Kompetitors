<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="employeesTypology">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.employeesTypology.detail.title')">EmployeesTypology</span> {{employeesTypology.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.employeesTypology.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{employeesTypology.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeesTypology.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{employeesTypology.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeesTypology.employeeType')">Employee Type</span>
                    </dt>
                    <dd>
                        <div v-if="employeesTypology.employeeTypeId">
                            <router-link :to="{name: 'EmployeeTypeView', params: {employeeTypeId: employeesTypology.employeeTypeId}}">{{employeesTypology.employeeType}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeesTypology.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="employeesTypology.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: employeesTypology.competitorId}}">{{employeesTypology.competitorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="employeesTypology.id" :to="{name: 'EmployeesTypologyEdit', params: {employeesTypologyId: employeesTypology.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./employees-typology-details.component.ts">
</script>
