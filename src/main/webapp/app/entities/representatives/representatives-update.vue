<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.representatives.home.createOrEditLabel" v-text="$t('kompetitors2App.representatives.home.createOrEditLabel')">Create or edit a Representatives</h2>
                <div>
                    <div class="form-group" v-if="representatives.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="representatives.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.competitorSiren')" for="representatives-competitorSiren">Competitor Siren</label>
                        <input type="text" class="form-control" name="competitorSiren" id="representatives-competitorSiren"
                            :class="{'valid': !$v.representatives.competitorSiren.$invalid, 'invalid': $v.representatives.competitorSiren.$invalid }" v-model="$v.representatives.competitorSiren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.qualite')" for="representatives-qualite">Qualite</label>
                        <input type="text" class="form-control" name="qualite" id="representatives-qualite"
                            :class="{'valid': !$v.representatives.qualite.$invalid, 'invalid': $v.representatives.qualite.$invalid }" v-model="$v.representatives.qualite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.type')" for="representatives-type">Type</label>
                        <input type="text" class="form-control" name="type" id="representatives-type"
                            :class="{'valid': !$v.representatives.type.$invalid, 'invalid': $v.representatives.type.$invalid }" v-model="$v.representatives.type.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.nom')" for="representatives-nom">Nom</label>
                        <input type="text" class="form-control" name="nom" id="representatives-nom"
                            :class="{'valid': !$v.representatives.nom.$invalid, 'invalid': $v.representatives.nom.$invalid }" v-model="$v.representatives.nom.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.prenom')" for="representatives-prenom">Prenom</label>
                        <input type="text" class="form-control" name="prenom" id="representatives-prenom"
                            :class="{'valid': !$v.representatives.prenom.$invalid, 'invalid': $v.representatives.prenom.$invalid }" v-model="$v.representatives.prenom.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.nomUsage')" for="representatives-nomUsage">Nom Usage</label>
                        <input type="text" class="form-control" name="nomUsage" id="representatives-nomUsage"
                            :class="{'valid': !$v.representatives.nomUsage.$invalid, 'invalid': $v.representatives.nomUsage.$invalid }" v-model="$v.representatives.nomUsage.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.dateNaissance')" for="representatives-dateNaissance">Date Naissance</label>
                        <input type="text" class="form-control" name="dateNaissance" id="representatives-dateNaissance"
                            :class="{'valid': !$v.representatives.dateNaissance.$invalid, 'invalid': $v.representatives.dateNaissance.$invalid }" v-model="$v.representatives.dateNaissance.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.denominationPM')" for="representatives-denominationPM">Denomination PM</label>
                        <input type="text" class="form-control" name="denominationPM" id="representatives-denominationPM"
                            :class="{'valid': !$v.representatives.denominationPM.$invalid, 'invalid': $v.representatives.denominationPM.$invalid }" v-model="$v.representatives.denominationPM.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.sirenPM')" for="representatives-sirenPM">Siren PM</label>
                        <input type="text" class="form-control" name="sirenPM" id="representatives-sirenPM"
                            :class="{'valid': !$v.representatives.sirenPM.$invalid, 'invalid': $v.representatives.sirenPM.$invalid }" v-model="$v.representatives.sirenPM.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.linkedInUrl')" for="representatives-linkedInUrl">Linked In Url</label>
                        <input type="text" class="form-control" name="linkedInUrl" id="representatives-linkedInUrl"
                            :class="{'valid': !$v.representatives.linkedInUrl.$invalid, 'invalid': $v.representatives.linkedInUrl.$invalid }" v-model="$v.representatives.linkedInUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.representatives.old')" for="representatives-old">Old</label>
                        <input type="checkbox" class="form-check" name="old" id="representatives-old"
                            :class="{'valid': !$v.representatives.old.$invalid, 'invalid': $v.representatives.old.$invalid }" v-model="$v.representatives.old.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.representatives.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./representatives-update.component.ts">
</script>
