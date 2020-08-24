<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.capital.home.createOrEditLabel" v-text="$t('kompetitors2App.capital.home.createOrEditLabel')">Create or edit a Capital</h2>
                <div>
                    <div class="form-group" v-if="capital.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="capital.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.competitorSiren')" for="capital-competitorSiren">Competitor Siren</label>
                        <input type="text" class="form-control" name="competitorSiren" id="capital-competitorSiren"
                            :class="{'valid': !$v.capital.competitorSiren.$invalid, 'invalid': $v.capital.competitorSiren.$invalid }" v-model="$v.capital.competitorSiren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.montant')" for="capital-montant">Montant</label>
                        <input type="number" class="form-control" name="montant" id="capital-montant"
                            :class="{'valid': !$v.capital.montant.$invalid, 'invalid': $v.capital.montant.$invalid }" v-model.number="$v.capital.montant.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.devise')" for="capital-devise">Devise</label>
                        <input type="text" class="form-control" name="devise" id="capital-devise"
                            :class="{'valid': !$v.capital.devise.$invalid, 'invalid': $v.capital.devise.$invalid }" v-model="$v.capital.devise.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.nbrParts')" for="capital-nbrParts">Nbr Parts</label>
                        <input type="number" class="form-control" name="nbrParts" id="capital-nbrParts"
                            :class="{'valid': !$v.capital.nbrParts.$invalid, 'invalid': $v.capital.nbrParts.$invalid }" v-model.number="$v.capital.nbrParts.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.pourcentageDetentionPP')" for="capital-pourcentageDetentionPP">Pourcentage Detention PP</label>
                        <input type="number" class="form-control" name="pourcentageDetentionPP" id="capital-pourcentageDetentionPP"
                            :class="{'valid': !$v.capital.pourcentageDetentionPP.$invalid, 'invalid': $v.capital.pourcentageDetentionPP.$invalid }" v-model.number="$v.capital.pourcentageDetentionPP.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.pourcentageDetentionPM')" for="capital-pourcentageDetentionPM">Pourcentage Detention PM</label>
                        <input type="number" class="form-control" name="pourcentageDetentionPM" id="capital-pourcentageDetentionPM"
                            :class="{'valid': !$v.capital.pourcentageDetentionPM.$invalid, 'invalid': $v.capital.pourcentageDetentionPM.$invalid }" v-model.number="$v.capital.pourcentageDetentionPM.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.listed')" for="capital-listed">Listed</label>
                        <input type="checkbox" class="form-check" name="listed" id="capital-listed"
                            :class="{'valid': !$v.capital.listed.$invalid, 'invalid': $v.capital.listed.$invalid }" v-model="$v.capital.listed.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.privateCapital')" for="capital-privateCapital">Private Capital</label>
                        <input type="checkbox" class="form-check" name="privateCapital" id="capital-privateCapital"
                            :class="{'valid': !$v.capital.privateCapital.$invalid, 'invalid': $v.capital.privateCapital.$invalid }" v-model="$v.capital.privateCapital.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.independentC')" for="capital-independentC">Independent C</label>
                        <input type="checkbox" class="form-check" name="independentC" id="capital-independentC"
                            :class="{'valid': !$v.capital.independentC.$invalid, 'invalid': $v.capital.independentC.$invalid }" v-model="$v.capital.independentC.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.independentE')" for="capital-independentE">Independent E</label>
                        <input type="checkbox" class="form-check" name="independentE" id="capital-independentE"
                            :class="{'valid': !$v.capital.independentE.$invalid, 'invalid': $v.capital.independentE.$invalid }" v-model="$v.capital.independentE.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.capital.old')" for="capital-old">Old</label>
                        <input type="checkbox" class="form-check" name="old" id="capital-old"
                            :class="{'valid': !$v.capital.old.$invalid, 'invalid': $v.capital.old.$invalid }" v-model="$v.capital.old.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.capital.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./capital-update.component.ts">
</script>
