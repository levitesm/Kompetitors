<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="societeMain">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.societeMain.detail.title')">SocieteMain</span> {{societeMain.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.deno')">Deno</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.deno}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.greffe')">Greffe</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.greffe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.enseigne')">Enseigne</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.enseigne}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.psiret')">Psiret</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.psiret}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.adresse')">Adresse</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.adresse}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.codepostal')">Codepostal</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.codepostal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.normcommune')">Normcommune</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.normcommune}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.commune')">Commune</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.commune}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.ape')">Ape</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.ape}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.apetexte')">Apetexte</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.apetexte}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.dateimmat')">Dateimmat</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.dateimmat}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.dcren')">Dcren</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.dcren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.nationalite')">Nationalite</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.nationalite}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.formejur')">Formejur</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.formejur}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.capital')">Capital</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.capital}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.devisecap')">Devisecap</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.devisecap}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.typecap')">Typecap</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.typecap}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{societeMain.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.societeMain.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="societeMain.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: societeMain.competitor.id}}">{{societeMain.competitor.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="societeMain.id" :to="{name: 'SocieteMainEdit', params: {societeMainId: societeMain.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./societe-main-details.component.ts">
</script>
