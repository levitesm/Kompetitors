<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="infogreffe">
                <h2 class="jh-entity-heading"><span v-text="$t('kompetitors2App.infogreffe.detail.title')">Infogreffe</span> {{infogreffe.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.departement')">Departement</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.departement}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.ville')">Ville</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.ville}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.numDept')">Num Dept</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.numDept}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.codeGreffe')">Code Greffe</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.codeGreffe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.dateImmatriculation')">Date Immatriculation</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.dateImmatriculation}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.ca1')">Ca 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.ca1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.siren')">Siren</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.siren}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.ca2')">Ca 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.ca2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.formeJuridique')">Forme Juridique</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.formeJuridique}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.resultat3')">Resultat 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.resultat3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.resultat2')">Resultat 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.resultat2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.resultat1')">Resultat 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.resultat1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.ficheidentite')">Ficheidentite</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.ficheidentite}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.duree1')">Duree 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.duree1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.dateDePublication')">Date De Publication</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.dateDePublication}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.statut')">Statut</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.statut}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.nic')">Nic</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.nic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.codeApe')">Code Ape</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.codeApe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.adresse')">Adresse</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.adresse}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime3')">Tranche Ca Millesime 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.trancheCaMillesime3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.denomination')">Denomination</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.denomination}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.duree2')">Duree 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.duree2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.effectif1')">Effectif 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.effectif1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.effectif3')">Effectif 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.effectif3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.effectif2')">Effectif 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.effectif2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.ca3')">Ca 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.ca3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime1')">Tranche Ca Millesime 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.trancheCaMillesime1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.duree3')">Duree 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.duree3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime2')">Tranche Ca Millesime 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.trancheCaMillesime2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.codePostal')">Code Postal</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.codePostal}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice1')">Date De Cloture Exercice 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.dateDeClotureExercice1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice3')">Date De Cloture Exercice 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.dateDeClotureExercice3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice2')">Date De Cloture Exercice 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.dateDeClotureExercice2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.libelleApe')">Libelle Ape</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.libelleApe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.greffe')">Greffe</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.greffe}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.millesime3')">Millesime 3</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.millesime3}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.millesime2')">Millesime 2</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.millesime2}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.millesime1')">Millesime 1</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.millesime1}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.region')">Region</span>
                    </dt>
                    <dd>
                        <span>{{infogreffe.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('kompetitors2App.infogreffe.competitor')">Competitor</span>
                    </dt>
                    <dd>
                        <div v-if="infogreffe.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: infogreffe.competitor.id}}">{{infogreffe.competitor.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="infogreffe.id" :to="{name: 'InfogreffeEdit', params: {infogreffeId: infogreffe.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./infogreffe-details.component.ts">
</script>
