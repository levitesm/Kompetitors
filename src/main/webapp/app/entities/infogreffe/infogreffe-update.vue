<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.infogreffe.home.createOrEditLabel" v-text="$t('kompetitors2App.infogreffe.home.createOrEditLabel')">Create or edit a Infogreffe</h2>
                <div>
                    <div class="form-group" v-if="infogreffe.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="infogreffe.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.departement')" for="infogreffe-departement">Departement</label>
                        <input type="text" class="form-control" name="departement" id="infogreffe-departement"
                            :class="{'valid': !$v.infogreffe.departement.$invalid, 'invalid': $v.infogreffe.departement.$invalid }" v-model="$v.infogreffe.departement.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.ville')" for="infogreffe-ville">Ville</label>
                        <input type="text" class="form-control" name="ville" id="infogreffe-ville"
                            :class="{'valid': !$v.infogreffe.ville.$invalid, 'invalid': $v.infogreffe.ville.$invalid }" v-model="$v.infogreffe.ville.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.numDept')" for="infogreffe-numDept">Num Dept</label>
                        <input type="text" class="form-control" name="numDept" id="infogreffe-numDept"
                            :class="{'valid': !$v.infogreffe.numDept.$invalid, 'invalid': $v.infogreffe.numDept.$invalid }" v-model="$v.infogreffe.numDept.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.codeGreffe')" for="infogreffe-codeGreffe">Code Greffe</label>
                        <input type="text" class="form-control" name="codeGreffe" id="infogreffe-codeGreffe"
                            :class="{'valid': !$v.infogreffe.codeGreffe.$invalid, 'invalid': $v.infogreffe.codeGreffe.$invalid }" v-model="$v.infogreffe.codeGreffe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.dateImmatriculation')" for="infogreffe-dateImmatriculation">Date Immatriculation</label>
                        <input type="text" class="form-control" name="dateImmatriculation" id="infogreffe-dateImmatriculation"
                            :class="{'valid': !$v.infogreffe.dateImmatriculation.$invalid, 'invalid': $v.infogreffe.dateImmatriculation.$invalid }" v-model="$v.infogreffe.dateImmatriculation.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.ca1')" for="infogreffe-ca1">Ca 1</label>
                        <input type="text" class="form-control" name="ca1" id="infogreffe-ca1"
                            :class="{'valid': !$v.infogreffe.ca1.$invalid, 'invalid': $v.infogreffe.ca1.$invalid }" v-model="$v.infogreffe.ca1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.siren')" for="infogreffe-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="infogreffe-siren"
                            :class="{'valid': !$v.infogreffe.siren.$invalid, 'invalid': $v.infogreffe.siren.$invalid }" v-model="$v.infogreffe.siren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.ca2')" for="infogreffe-ca2">Ca 2</label>
                        <input type="text" class="form-control" name="ca2" id="infogreffe-ca2"
                            :class="{'valid': !$v.infogreffe.ca2.$invalid, 'invalid': $v.infogreffe.ca2.$invalid }" v-model="$v.infogreffe.ca2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.formeJuridique')" for="infogreffe-formeJuridique">Forme Juridique</label>
                        <input type="text" class="form-control" name="formeJuridique" id="infogreffe-formeJuridique"
                            :class="{'valid': !$v.infogreffe.formeJuridique.$invalid, 'invalid': $v.infogreffe.formeJuridique.$invalid }" v-model="$v.infogreffe.formeJuridique.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.resultat3')" for="infogreffe-resultat3">Resultat 3</label>
                        <input type="text" class="form-control" name="resultat3" id="infogreffe-resultat3"
                            :class="{'valid': !$v.infogreffe.resultat3.$invalid, 'invalid': $v.infogreffe.resultat3.$invalid }" v-model="$v.infogreffe.resultat3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.resultat2')" for="infogreffe-resultat2">Resultat 2</label>
                        <input type="text" class="form-control" name="resultat2" id="infogreffe-resultat2"
                            :class="{'valid': !$v.infogreffe.resultat2.$invalid, 'invalid': $v.infogreffe.resultat2.$invalid }" v-model="$v.infogreffe.resultat2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.resultat1')" for="infogreffe-resultat1">Resultat 1</label>
                        <input type="text" class="form-control" name="resultat1" id="infogreffe-resultat1"
                            :class="{'valid': !$v.infogreffe.resultat1.$invalid, 'invalid': $v.infogreffe.resultat1.$invalid }" v-model="$v.infogreffe.resultat1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.ficheidentite')" for="infogreffe-ficheidentite">Ficheidentite</label>
                        <input type="text" class="form-control" name="ficheidentite" id="infogreffe-ficheidentite"
                            :class="{'valid': !$v.infogreffe.ficheidentite.$invalid, 'invalid': $v.infogreffe.ficheidentite.$invalid }" v-model="$v.infogreffe.ficheidentite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.duree1')" for="infogreffe-duree1">Duree 1</label>
                        <input type="text" class="form-control" name="duree1" id="infogreffe-duree1"
                            :class="{'valid': !$v.infogreffe.duree1.$invalid, 'invalid': $v.infogreffe.duree1.$invalid }" v-model="$v.infogreffe.duree1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.dateDePublication')" for="infogreffe-dateDePublication">Date De Publication</label>
                        <input type="text" class="form-control" name="dateDePublication" id="infogreffe-dateDePublication"
                            :class="{'valid': !$v.infogreffe.dateDePublication.$invalid, 'invalid': $v.infogreffe.dateDePublication.$invalid }" v-model="$v.infogreffe.dateDePublication.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.statut')" for="infogreffe-statut">Statut</label>
                        <input type="text" class="form-control" name="statut" id="infogreffe-statut"
                            :class="{'valid': !$v.infogreffe.statut.$invalid, 'invalid': $v.infogreffe.statut.$invalid }" v-model="$v.infogreffe.statut.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.nic')" for="infogreffe-nic">Nic</label>
                        <input type="text" class="form-control" name="nic" id="infogreffe-nic"
                            :class="{'valid': !$v.infogreffe.nic.$invalid, 'invalid': $v.infogreffe.nic.$invalid }" v-model="$v.infogreffe.nic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.codeApe')" for="infogreffe-codeApe">Code Ape</label>
                        <input type="text" class="form-control" name="codeApe" id="infogreffe-codeApe"
                            :class="{'valid': !$v.infogreffe.codeApe.$invalid, 'invalid': $v.infogreffe.codeApe.$invalid }" v-model="$v.infogreffe.codeApe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.adresse')" for="infogreffe-adresse">Adresse</label>
                        <input type="text" class="form-control" name="adresse" id="infogreffe-adresse"
                            :class="{'valid': !$v.infogreffe.adresse.$invalid, 'invalid': $v.infogreffe.adresse.$invalid }" v-model="$v.infogreffe.adresse.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime3')" for="infogreffe-trancheCaMillesime3">Tranche Ca Millesime 3</label>
                        <input type="text" class="form-control" name="trancheCaMillesime3" id="infogreffe-trancheCaMillesime3"
                            :class="{'valid': !$v.infogreffe.trancheCaMillesime3.$invalid, 'invalid': $v.infogreffe.trancheCaMillesime3.$invalid }" v-model="$v.infogreffe.trancheCaMillesime3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.denomination')" for="infogreffe-denomination">Denomination</label>
                        <input type="text" class="form-control" name="denomination" id="infogreffe-denomination"
                            :class="{'valid': !$v.infogreffe.denomination.$invalid, 'invalid': $v.infogreffe.denomination.$invalid }" v-model="$v.infogreffe.denomination.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.duree2')" for="infogreffe-duree2">Duree 2</label>
                        <input type="text" class="form-control" name="duree2" id="infogreffe-duree2"
                            :class="{'valid': !$v.infogreffe.duree2.$invalid, 'invalid': $v.infogreffe.duree2.$invalid }" v-model="$v.infogreffe.duree2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.effectif1')" for="infogreffe-effectif1">Effectif 1</label>
                        <input type="text" class="form-control" name="effectif1" id="infogreffe-effectif1"
                            :class="{'valid': !$v.infogreffe.effectif1.$invalid, 'invalid': $v.infogreffe.effectif1.$invalid }" v-model="$v.infogreffe.effectif1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.effectif3')" for="infogreffe-effectif3">Effectif 3</label>
                        <input type="text" class="form-control" name="effectif3" id="infogreffe-effectif3"
                            :class="{'valid': !$v.infogreffe.effectif3.$invalid, 'invalid': $v.infogreffe.effectif3.$invalid }" v-model="$v.infogreffe.effectif3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.effectif2')" for="infogreffe-effectif2">Effectif 2</label>
                        <input type="text" class="form-control" name="effectif2" id="infogreffe-effectif2"
                            :class="{'valid': !$v.infogreffe.effectif2.$invalid, 'invalid': $v.infogreffe.effectif2.$invalid }" v-model="$v.infogreffe.effectif2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.ca3')" for="infogreffe-ca3">Ca 3</label>
                        <input type="text" class="form-control" name="ca3" id="infogreffe-ca3"
                            :class="{'valid': !$v.infogreffe.ca3.$invalid, 'invalid': $v.infogreffe.ca3.$invalid }" v-model="$v.infogreffe.ca3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime1')" for="infogreffe-trancheCaMillesime1">Tranche Ca Millesime 1</label>
                        <input type="text" class="form-control" name="trancheCaMillesime1" id="infogreffe-trancheCaMillesime1"
                            :class="{'valid': !$v.infogreffe.trancheCaMillesime1.$invalid, 'invalid': $v.infogreffe.trancheCaMillesime1.$invalid }" v-model="$v.infogreffe.trancheCaMillesime1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.duree3')" for="infogreffe-duree3">Duree 3</label>
                        <input type="text" class="form-control" name="duree3" id="infogreffe-duree3"
                            :class="{'valid': !$v.infogreffe.duree3.$invalid, 'invalid': $v.infogreffe.duree3.$invalid }" v-model="$v.infogreffe.duree3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.trancheCaMillesime2')" for="infogreffe-trancheCaMillesime2">Tranche Ca Millesime 2</label>
                        <input type="text" class="form-control" name="trancheCaMillesime2" id="infogreffe-trancheCaMillesime2"
                            :class="{'valid': !$v.infogreffe.trancheCaMillesime2.$invalid, 'invalid': $v.infogreffe.trancheCaMillesime2.$invalid }" v-model="$v.infogreffe.trancheCaMillesime2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.codePostal')" for="infogreffe-codePostal">Code Postal</label>
                        <input type="text" class="form-control" name="codePostal" id="infogreffe-codePostal"
                            :class="{'valid': !$v.infogreffe.codePostal.$invalid, 'invalid': $v.infogreffe.codePostal.$invalid }" v-model="$v.infogreffe.codePostal.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice1')" for="infogreffe-dateDeClotureExercice1">Date De Cloture Exercice 1</label>
                        <input type="text" class="form-control" name="dateDeClotureExercice1" id="infogreffe-dateDeClotureExercice1"
                            :class="{'valid': !$v.infogreffe.dateDeClotureExercice1.$invalid, 'invalid': $v.infogreffe.dateDeClotureExercice1.$invalid }" v-model="$v.infogreffe.dateDeClotureExercice1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice3')" for="infogreffe-dateDeClotureExercice3">Date De Cloture Exercice 3</label>
                        <input type="text" class="form-control" name="dateDeClotureExercice3" id="infogreffe-dateDeClotureExercice3"
                            :class="{'valid': !$v.infogreffe.dateDeClotureExercice3.$invalid, 'invalid': $v.infogreffe.dateDeClotureExercice3.$invalid }" v-model="$v.infogreffe.dateDeClotureExercice3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.dateDeClotureExercice2')" for="infogreffe-dateDeClotureExercice2">Date De Cloture Exercice 2</label>
                        <input type="text" class="form-control" name="dateDeClotureExercice2" id="infogreffe-dateDeClotureExercice2"
                            :class="{'valid': !$v.infogreffe.dateDeClotureExercice2.$invalid, 'invalid': $v.infogreffe.dateDeClotureExercice2.$invalid }" v-model="$v.infogreffe.dateDeClotureExercice2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.libelleApe')" for="infogreffe-libelleApe">Libelle Ape</label>
                        <input type="text" class="form-control" name="libelleApe" id="infogreffe-libelleApe"
                            :class="{'valid': !$v.infogreffe.libelleApe.$invalid, 'invalid': $v.infogreffe.libelleApe.$invalid }" v-model="$v.infogreffe.libelleApe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.greffe')" for="infogreffe-greffe">Greffe</label>
                        <input type="text" class="form-control" name="greffe" id="infogreffe-greffe"
                            :class="{'valid': !$v.infogreffe.greffe.$invalid, 'invalid': $v.infogreffe.greffe.$invalid }" v-model="$v.infogreffe.greffe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.millesime3')" for="infogreffe-millesime3">Millesime 3</label>
                        <input type="text" class="form-control" name="millesime3" id="infogreffe-millesime3"
                            :class="{'valid': !$v.infogreffe.millesime3.$invalid, 'invalid': $v.infogreffe.millesime3.$invalid }" v-model="$v.infogreffe.millesime3.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.millesime2')" for="infogreffe-millesime2">Millesime 2</label>
                        <input type="text" class="form-control" name="millesime2" id="infogreffe-millesime2"
                            :class="{'valid': !$v.infogreffe.millesime2.$invalid, 'invalid': $v.infogreffe.millesime2.$invalid }" v-model="$v.infogreffe.millesime2.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.millesime1')" for="infogreffe-millesime1">Millesime 1</label>
                        <input type="text" class="form-control" name="millesime1" id="infogreffe-millesime1"
                            :class="{'valid': !$v.infogreffe.millesime1.$invalid, 'invalid': $v.infogreffe.millesime1.$invalid }" v-model="$v.infogreffe.millesime1.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.region')" for="infogreffe-region">Region</label>
                        <input type="text" class="form-control" name="region" id="infogreffe-region"
                            :class="{'valid': !$v.infogreffe.region.$invalid, 'invalid': $v.infogreffe.region.$invalid }" v-model="$v.infogreffe.region.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.infogreffe.competitor')" for="infogreffe-competitor">Competitor</label>
                        <select class="form-control" id="infogreffe-competitor" name="competitor" v-model="infogreffe.competitor">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="infogreffe.competitor && competitorsOption.id === infogreffe.competitor.id ? infogreffe.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.infogreffe.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./infogreffe-update.component.ts">
</script>
