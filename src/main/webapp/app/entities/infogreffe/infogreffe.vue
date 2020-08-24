<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.infogreffe.home.title')" id="infogreffe-heading">Infogreffes</span>
            <router-link :to="{name: 'InfogreffeCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-infogreffe">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.infogreffe.home.createLabel')">
                    Create a new Infogreffe
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && infogreffes && infogreffes.length === 0">
            <span v-text="$t('kompetitors2App.infogreffe.home.notFound')">No infogreffes found</span>
        </div>
        <div class="table-responsive" v-if="infogreffes && infogreffes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.departement')">Departement</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.ville')">Ville</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.numDept')">Num Dept</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.codeGreffe')">Code Greffe</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.dateImmatriculation')">Date Immatriculation</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.ca1')">Ca 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.siren')">Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.ca2')">Ca 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.formeJuridique')">Forme Juridique</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.resultat3')">Resultat 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.resultat2')">Resultat 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.resultat1')">Resultat 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.ficheidentite')">Ficheidentite</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.duree1')">Duree 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.dateDePublication')">Date De Publication</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.statut')">Statut</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.nic')">Nic</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.codeApe')">Code Ape</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.adresse')">Adresse</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime3')">Tranche Ca Millesime 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.denomination')">Denomination</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.duree2')">Duree 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.effectif1')">Effectif 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.effectif3')">Effectif 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.effectif2')">Effectif 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.ca3')">Ca 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime1')">Tranche Ca Millesime 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.duree3')">Duree 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime2')">Tranche Ca Millesime 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.codePostal')">Code Postal</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice1')">Date De Cloture Exercice 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice3')">Date De Cloture Exercice 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice2')">Date De Cloture Exercice 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.libelleApe')">Libelle Ape</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.greffe')">Greffe</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.millesime3')">Millesime 3</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.millesime2')">Millesime 2</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.millesime1')">Millesime 1</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.region')">Region</span></th>
                    <th><span v-text="$t('kompetitors2App.infogreffe.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="infogreffe in infogreffes"
                    :key="infogreffe.id">
                    <td>
                        <router-link :to="{name: 'InfogreffeView', params: {infogreffeId: infogreffe.id}}">{{infogreffe.id}}</router-link>
                    </td>
                    <td>{{infogreffe.departement}}</td>
                    <td>{{infogreffe.ville}}</td>
                    <td>{{infogreffe.numDept}}</td>
                    <td>{{infogreffe.codeGreffe}}</td>
                    <td>{{infogreffe.dateImmatriculation}}</td>
                    <td>{{infogreffe.ca1}}</td>
                    <td>{{infogreffe.siren}}</td>
                    <td>{{infogreffe.ca2}}</td>
                    <td>{{infogreffe.formeJuridique}}</td>
                    <td>{{infogreffe.resultat3}}</td>
                    <td>{{infogreffe.resultat2}}</td>
                    <td>{{infogreffe.resultat1}}</td>
                    <td>{{infogreffe.ficheidentite}}</td>
                    <td>{{infogreffe.duree1}}</td>
                    <td>{{infogreffe.dateDePublication}}</td>
                    <td>{{infogreffe.statut}}</td>
                    <td>{{infogreffe.nic}}</td>
                    <td>{{infogreffe.codeApe}}</td>
                    <td>{{infogreffe.adresse}}</td>
                    <td>{{infogreffe.trancheCaMillesime3}}</td>
                    <td>{{infogreffe.denomination}}</td>
                    <td>{{infogreffe.duree2}}</td>
                    <td>{{infogreffe.effectif1}}</td>
                    <td>{{infogreffe.effectif3}}</td>
                    <td>{{infogreffe.effectif2}}</td>
                    <td>{{infogreffe.ca3}}</td>
                    <td>{{infogreffe.trancheCaMillesime1}}</td>
                    <td>{{infogreffe.duree3}}</td>
                    <td>{{infogreffe.trancheCaMillesime2}}</td>
                    <td>{{infogreffe.codePostal}}</td>
                    <td>{{infogreffe.dateDeClotureExercice1}}</td>
                    <td>{{infogreffe.dateDeClotureExercice3}}</td>
                    <td>{{infogreffe.dateDeClotureExercice2}}</td>
                    <td>{{infogreffe.libelleApe}}</td>
                    <td>{{infogreffe.greffe}}</td>
                    <td>{{infogreffe.millesime3}}</td>
                    <td>{{infogreffe.millesime2}}</td>
                    <td>{{infogreffe.millesime1}}</td>
                    <td>{{infogreffe.region}}</td>
                    <td>
                        <div v-if="infogreffe.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: infogreffe.competitor.id}}">{{infogreffe.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'InfogreffeView', params: {infogreffeId: infogreffe.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'InfogreffeEdit', params: {infogreffeId: infogreffe.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(infogreffe)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kompetitors2App.infogreffe.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-infogreffe-heading" v-bind:title="$t('kompetitors2App.infogreffe.delete.question')">Are you sure you want to delete this Infogreffe?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-infogreffe" v-text="$t('entity.action.delete')" v-on:click="removeInfogreffe()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./infogreffe.component.ts">
</script>
