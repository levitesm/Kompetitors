<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="annualAccount">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.annualAccount.detail.title')">AnnualAccount</span> {{annualAccount.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccount.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{annualAccount.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccount.year')">Year</span>
                    </dt>
                    <dd>
                        <span>{{annualAccount.year}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccount.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{annualAccount.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.annualAccount.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{annualAccount.value}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="annualAccount.id" :to="{name: 'AnnualAccountEdit', params: {annualAccountId: annualAccount.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./annual-account-details.component.ts">
</script>
