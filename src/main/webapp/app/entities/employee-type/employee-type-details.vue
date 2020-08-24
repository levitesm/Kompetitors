<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="employeeType">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.employeeType.detail.title')">EmployeeType</span> {{employeeType.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.employeeType.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{employeeType.name}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="employeeType.id" :to="{name: 'EmployeeTypeEdit', params: {employeeTypeId: employeeType.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./employee-type-details.component.ts">
</script>
