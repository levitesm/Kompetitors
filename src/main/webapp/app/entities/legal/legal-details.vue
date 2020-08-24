<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="legal">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.legal.detail.title')">Legal</span> {{legal.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.legalAddress')">Legal Address</span>
                    </dt>
                    <dd>
                        <span>{{legal.legalAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{legal.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.greffe')">Greffe</span>
                    </dt>
                    <dd>
                        <span>{{legal.greffe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.founded')">Founded</span>
                    </dt>
                    <dd>
                        <span>{{legal.founded}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.updateDate')">Update Date</span>
                    </dt>
                    <dd>
                        <span>{{legal.updateDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.legalForm')">Legal Form</span>
                    </dt>
                    <dd>
                        <span>{{legal.legalForm}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="legal.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: legal.competitor.id}}">{{legal.competitor.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.ownership')">Ownership</span>
                    </dt>
                    <dd>
                        <div v-if="legal.ownership">
                            <router-link :to="{name: 'ListOwnershipsView', params: {ownershipId: legal.ownership.id}}">{{legal.ownership.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.activity')">Activity</span>
                    </dt>
                    <dd>
                        <div v-if="legal.activity">
                            <router-link :to="{name: 'ListActivitiesView', params: {activityId: legal.activity.id}}">{{legal.activity.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.legal.pricing')">Pricing</span>
                    </dt>
                    <dd>
                        <div v-if="legal.pricing">
                            <router-link :to="{name: 'ListPricingsView', params: {pricingId: legal.pricing.id}}">{{legal.pricing.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="legal.id" :to="{name: 'LegalEdit', params: {legalId: legal.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./legal-details.component.ts">
</script>
