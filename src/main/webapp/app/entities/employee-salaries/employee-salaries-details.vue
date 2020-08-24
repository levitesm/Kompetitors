<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="employeeSalaries">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.employeeSalaries.detail.title')">EmployeeSalaries</span> {{employeeSalaries.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.employeeSalaries.seniority')">Seniority</span>
                    </dt>
                    <dd>
                        <span>{{employeeSalaries.seniority}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeeSalaries.salary')">Salary</span>
                    </dt>
                    <dd>
                        <span>{{employeeSalaries.salary}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeeSalaries.updateDate')">Update Date</span>
                    </dt>
                    <dd>
                        <span>{{employeeSalaries.updateDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.employeeSalaries.updatedBy')">Updated By</span>
                    </dt>
                    <dd>
                        <span>{{employeeSalaries.updatedBy}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="employeeSalaries.id" :to="{name: 'EmployeeSalariesEdit', params: {employeeSalariesId: employeeSalaries.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./employee-salaries-details.component.ts">
</script>
