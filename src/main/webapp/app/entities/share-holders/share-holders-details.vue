<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="shareHolders">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.shareHolders.detail.title')">ShareHolders</span> {{shareHolders.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.competitorSiren')">Competitor Siren</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.competitorSiren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.typePersonne')">Type Personne</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.typePersonne}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.denomination')">Denomination</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.denomination}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.civilite')">Civilite</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.civilite}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.nomPatronymique')">Nom Patronymique</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.nomPatronymique}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.nomUsage')">Nom Usage</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.nomUsage}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.prenom')">Prenom</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.prenom}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.libelleFormeJuridique')">Libelle Forme Juridique</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.libelleFormeJuridique}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.codeFormeJuridique')">Code Forme Juridique</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.codeFormeJuridique}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.codeApe')">Code Ape</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.codeApe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.dateNaissance')">Date Naissance</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.dateNaissance}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.nbrParts')">Nbr Parts</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.nbrParts}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.pourcentageDetention')">Pourcentage Detention</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.pourcentageDetention}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.shareHolders.old')">Old</span>
                    </dt>
                    <dd>
                        <span>{{shareHolders.old}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="shareHolders.id" :to="{name: 'ShareHoldersEdit', params: {shareHoldersId: shareHolders.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./share-holders-details.component.ts">
</script>
