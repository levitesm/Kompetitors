<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.shareHolders.home.createOrEditLabel" v-text="$t('kompetitors2App.shareHolders.home.createOrEditLabel')">Create or edit a ShareHolders</h2>
                <div>
                    <div class="form-group" v-if="shareHolders.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="shareHolders.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.competitorSiren')" for="share-holders-competitorSiren">Competitor Siren</label>
                        <input type="text" class="form-control" name="competitorSiren" id="share-holders-competitorSiren"
                            :class="{'valid': !$v.shareHolders.competitorSiren.$invalid, 'invalid': $v.shareHolders.competitorSiren.$invalid }" v-model="$v.shareHolders.competitorSiren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.typePersonne')" for="share-holders-typePersonne">Type Personne</label>
                        <input type="text" class="form-control" name="typePersonne" id="share-holders-typePersonne"
                            :class="{'valid': !$v.shareHolders.typePersonne.$invalid, 'invalid': $v.shareHolders.typePersonne.$invalid }" v-model="$v.shareHolders.typePersonne.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.denomination')" for="share-holders-denomination">Denomination</label>
                        <input type="text" class="form-control" name="denomination" id="share-holders-denomination"
                            :class="{'valid': !$v.shareHolders.denomination.$invalid, 'invalid': $v.shareHolders.denomination.$invalid }" v-model="$v.shareHolders.denomination.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.civilite')" for="share-holders-civilite">Civilite</label>
                        <input type="text" class="form-control" name="civilite" id="share-holders-civilite"
                            :class="{'valid': !$v.shareHolders.civilite.$invalid, 'invalid': $v.shareHolders.civilite.$invalid }" v-model="$v.shareHolders.civilite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.nomPatronymique')" for="share-holders-nomPatronymique">Nom Patronymique</label>
                        <input type="text" class="form-control" name="nomPatronymique" id="share-holders-nomPatronymique"
                            :class="{'valid': !$v.shareHolders.nomPatronymique.$invalid, 'invalid': $v.shareHolders.nomPatronymique.$invalid }" v-model="$v.shareHolders.nomPatronymique.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.nomUsage')" for="share-holders-nomUsage">Nom Usage</label>
                        <input type="text" class="form-control" name="nomUsage" id="share-holders-nomUsage"
                            :class="{'valid': !$v.shareHolders.nomUsage.$invalid, 'invalid': $v.shareHolders.nomUsage.$invalid }" v-model="$v.shareHolders.nomUsage.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.prenom')" for="share-holders-prenom">Prenom</label>
                        <input type="text" class="form-control" name="prenom" id="share-holders-prenom"
                            :class="{'valid': !$v.shareHolders.prenom.$invalid, 'invalid': $v.shareHolders.prenom.$invalid }" v-model="$v.shareHolders.prenom.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.libelleFormeJuridique')" for="share-holders-libelleFormeJuridique">Libelle Forme Juridique</label>
                        <input type="text" class="form-control" name="libelleFormeJuridique" id="share-holders-libelleFormeJuridique"
                            :class="{'valid': !$v.shareHolders.libelleFormeJuridique.$invalid, 'invalid': $v.shareHolders.libelleFormeJuridique.$invalid }" v-model="$v.shareHolders.libelleFormeJuridique.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.codeFormeJuridique')" for="share-holders-codeFormeJuridique">Code Forme Juridique</label>
                        <input type="text" class="form-control" name="codeFormeJuridique" id="share-holders-codeFormeJuridique"
                            :class="{'valid': !$v.shareHolders.codeFormeJuridique.$invalid, 'invalid': $v.shareHolders.codeFormeJuridique.$invalid }" v-model="$v.shareHolders.codeFormeJuridique.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.siren')" for="share-holders-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="share-holders-siren"
                            :class="{'valid': !$v.shareHolders.siren.$invalid, 'invalid': $v.shareHolders.siren.$invalid }" v-model="$v.shareHolders.siren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.codeApe')" for="share-holders-codeApe">Code Ape</label>
                        <input type="text" class="form-control" name="codeApe" id="share-holders-codeApe"
                            :class="{'valid': !$v.shareHolders.codeApe.$invalid, 'invalid': $v.shareHolders.codeApe.$invalid }" v-model="$v.shareHolders.codeApe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.dateNaissance')" for="share-holders-dateNaissance">Date Naissance</label>
                        <input type="text" class="form-control" name="dateNaissance" id="share-holders-dateNaissance"
                            :class="{'valid': !$v.shareHolders.dateNaissance.$invalid, 'invalid': $v.shareHolders.dateNaissance.$invalid }" v-model="$v.shareHolders.dateNaissance.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.nbrParts')" for="share-holders-nbrParts">Nbr Parts</label>
                        <input type="number" class="form-control" name="nbrParts" id="share-holders-nbrParts"
                            :class="{'valid': !$v.shareHolders.nbrParts.$invalid, 'invalid': $v.shareHolders.nbrParts.$invalid }" v-model.number="$v.shareHolders.nbrParts.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.pourcentageDetention')" for="share-holders-pourcentageDetention">Pourcentage Detention</label>
                        <input type="number" class="form-control" name="pourcentageDetention" id="share-holders-pourcentageDetention"
                            :class="{'valid': !$v.shareHolders.pourcentageDetention.$invalid, 'invalid': $v.shareHolders.pourcentageDetention.$invalid }" v-model.number="$v.shareHolders.pourcentageDetention.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.shareHolders.old')" for="share-holders-old">Old</label>
                        <input type="checkbox" class="form-check" name="old" id="share-holders-old"
                            :class="{'valid': !$v.shareHolders.old.$invalid, 'invalid': $v.shareHolders.old.$invalid }" v-model="$v.shareHolders.old.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.shareHolders.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./share-holders-update.component.ts">
</script>
