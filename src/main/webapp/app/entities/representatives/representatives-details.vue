<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="representatives">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.representatives.detail.title')">Representatives</span> {{representatives.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.competitorSiren')">Competitor Siren</span>
                    </dt>
                    <dd>
                        <span>{{representatives.competitorSiren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.qualite')">Qualite</span>
                    </dt>
                    <dd>
                        <span>{{representatives.qualite}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.type')">Type</span>
                    </dt>
                    <dd>
                        <span>{{representatives.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.nom')">Nom</span>
                    </dt>
                    <dd>
                        <span>{{representatives.nom}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.prenom')">Prenom</span>
                    </dt>
                    <dd>
                        <span>{{representatives.prenom}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.nomUsage')">Nom Usage</span>
                    </dt>
                    <dd>
                        <span>{{representatives.nomUsage}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.dateNaissance')">Date Naissance</span>
                    </dt>
                    <dd>
                        <span>{{representatives.dateNaissance}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.denominationPM')">Denomination PM</span>
                    </dt>
                    <dd>
                        <span>{{representatives.denominationPM}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.sirenPM')">Siren PM</span>
                    </dt>
                    <dd>
                        <span>{{representatives.sirenPM}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.linkedInUrl')">Linked In Url</span>
                    </dt>
                    <dd>
                        <span>{{representatives.linkedInUrl}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.representatives.old')">Old</span>
                    </dt>
                    <dd>
                        <span>{{representatives.old}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="representatives.id" :to="{name: 'RepresentativesEdit', params: {representativesId: representatives.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./representatives-details.component.ts">
</script>
