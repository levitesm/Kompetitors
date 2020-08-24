<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="capital">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.capital.detail.title')">Capital</span> {{capital.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.competitorSiren')">Competitor Siren</span>
                    </dt>
                    <dd>
                        <span>{{capital.competitorSiren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.montant')">Montant</span>
                    </dt>
                    <dd>
                        <span>{{capital.montant}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.devise')">Devise</span>
                    </dt>
                    <dd>
                        <span>{{capital.devise}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.nbrParts')">Nbr Parts</span>
                    </dt>
                    <dd>
                        <span>{{capital.nbrParts}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.pourcentageDetentionPP')">Pourcentage Detention PP</span>
                    </dt>
                    <dd>
                        <span>{{capital.pourcentageDetentionPP}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.pourcentageDetentionPM')">Pourcentage Detention PM</span>
                    </dt>
                    <dd>
                        <span>{{capital.pourcentageDetentionPM}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.listed')">Listed</span>
                    </dt>
                    <dd>
                        <span>{{capital.listed}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.privateCapital')">Private Capital</span>
                    </dt>
                    <dd>
                        <span>{{capital.privateCapital}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.independentC')">Independent C</span>
                    </dt>
                    <dd>
                        <span>{{capital.independentC}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.independentE')">Independent E</span>
                    </dt>
                    <dd>
                        <span>{{capital.independentE}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.capital.old')">Old</span>
                    </dt>
                    <dd>
                        <span>{{capital.old}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="capital.id" :to="{name: 'CapitalEdit', params: {capitalId: capital.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./capital-details.component.ts">
</script>
