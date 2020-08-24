<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="competitors">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.competitors.detail.title')">Competitors</span> {{competitors.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.competitors.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{competitors.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitors.webSite')">Web Site</span>
                    </dt>
                    <dd>
                        <span>{{competitors.webSite}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitors.countryPhone')">Country Phone</span>
                    </dt>
                    <dd>
                        <span>{{competitors.countryPhone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitors.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="competitors.country">
                            <router-link :to="{name: 'ListCountriesView', params: {countryId: competitors.country.id}}">{{competitors.country.id}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.competitors.globalGroups')">Global Groups</span>
                    </dt>
                    <dd>
                        <div v-if="competitors.globalGroups">
                            <router-link :to="{name: 'GlobalGroupsView', params: {globalGroupsId: competitors.globalGroups.id}}">{{competitors.globalGroups.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="competitors.id" :to="{name: 'CompetitorsEdit', params: {competitorsId: competitors.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./competitors-details.component.ts">
</script>
