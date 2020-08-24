<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="listCities">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.listCities.detail.title')">ListCities</span> {{listCities.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.listCities.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{listCities.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.listCities.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="listCities.country">
                            <router-link :to="{name: 'ListCityCountriesView', params: {countryId: listCities.country.id}}">{{listCities.country.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="listCities.id" :to="{name: 'ListCitiesEdit', params: {listCitiesId: listCities.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./list-cities-details.component.ts">
</script>
