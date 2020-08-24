<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.societeMain.home.createOrEditLabel" v-text="$t('kompetitors2App.societeMain.home.createOrEditLabel')">Create or edit a SocieteMain</h2>
                <div>
                    <div class="form-group" v-if="societeMain.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="societeMain.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.siren')" for="societe-main-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="societe-main-siren"
                            :class="{'valid': !$v.societeMain.siren.$invalid, 'invalid': $v.societeMain.siren.$invalid }" v-model="$v.societeMain.siren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.deno')" for="societe-main-deno">Deno</label>
                        <input type="text" class="form-control" name="deno" id="societe-main-deno"
                            :class="{'valid': !$v.societeMain.deno.$invalid, 'invalid': $v.societeMain.deno.$invalid }" v-model="$v.societeMain.deno.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.greffe')" for="societe-main-greffe">Greffe</label>
                        <input type="text" class="form-control" name="greffe" id="societe-main-greffe"
                            :class="{'valid': !$v.societeMain.greffe.$invalid, 'invalid': $v.societeMain.greffe.$invalid }" v-model="$v.societeMain.greffe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.enseigne')" for="societe-main-enseigne">Enseigne</label>
                        <input type="text" class="form-control" name="enseigne" id="societe-main-enseigne"
                            :class="{'valid': !$v.societeMain.enseigne.$invalid, 'invalid': $v.societeMain.enseigne.$invalid }" v-model="$v.societeMain.enseigne.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.psiret')" for="societe-main-psiret">Psiret</label>
                        <input type="text" class="form-control" name="psiret" id="societe-main-psiret"
                            :class="{'valid': !$v.societeMain.psiret.$invalid, 'invalid': $v.societeMain.psiret.$invalid }" v-model="$v.societeMain.psiret.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.adresse')" for="societe-main-adresse">Adresse</label>
                        <input type="text" class="form-control" name="adresse" id="societe-main-adresse"
                            :class="{'valid': !$v.societeMain.adresse.$invalid, 'invalid': $v.societeMain.adresse.$invalid }" v-model="$v.societeMain.adresse.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.codepostal')" for="societe-main-codepostal">Codepostal</label>
                        <input type="text" class="form-control" name="codepostal" id="societe-main-codepostal"
                            :class="{'valid': !$v.societeMain.codepostal.$invalid, 'invalid': $v.societeMain.codepostal.$invalid }" v-model="$v.societeMain.codepostal.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.normcommune')" for="societe-main-normcommune">Normcommune</label>
                        <input type="text" class="form-control" name="normcommune" id="societe-main-normcommune"
                            :class="{'valid': !$v.societeMain.normcommune.$invalid, 'invalid': $v.societeMain.normcommune.$invalid }" v-model="$v.societeMain.normcommune.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.commune')" for="societe-main-commune">Commune</label>
                        <input type="text" class="form-control" name="commune" id="societe-main-commune"
                            :class="{'valid': !$v.societeMain.commune.$invalid, 'invalid': $v.societeMain.commune.$invalid }" v-model="$v.societeMain.commune.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.ape')" for="societe-main-ape">Ape</label>
                        <input type="text" class="form-control" name="ape" id="societe-main-ape"
                            :class="{'valid': !$v.societeMain.ape.$invalid, 'invalid': $v.societeMain.ape.$invalid }" v-model="$v.societeMain.ape.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.apetexte')" for="societe-main-apetexte">Apetexte</label>
                        <input type="text" class="form-control" name="apetexte" id="societe-main-apetexte"
                            :class="{'valid': !$v.societeMain.apetexte.$invalid, 'invalid': $v.societeMain.apetexte.$invalid }" v-model="$v.societeMain.apetexte.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.dateimmat')" for="societe-main-dateimmat">Dateimmat</label>
                        <input type="text" class="form-control" name="dateimmat" id="societe-main-dateimmat"
                            :class="{'valid': !$v.societeMain.dateimmat.$invalid, 'invalid': $v.societeMain.dateimmat.$invalid }" v-model="$v.societeMain.dateimmat.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.dcren')" for="societe-main-dcren">Dcren</label>
                        <input type="text" class="form-control" name="dcren" id="societe-main-dcren"
                            :class="{'valid': !$v.societeMain.dcren.$invalid, 'invalid': $v.societeMain.dcren.$invalid }" v-model="$v.societeMain.dcren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.nationalite')" for="societe-main-nationalite">Nationalite</label>
                        <input type="text" class="form-control" name="nationalite" id="societe-main-nationalite"
                            :class="{'valid': !$v.societeMain.nationalite.$invalid, 'invalid': $v.societeMain.nationalite.$invalid }" v-model="$v.societeMain.nationalite.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.formejur')" for="societe-main-formejur">Formejur</label>
                        <input type="text" class="form-control" name="formejur" id="societe-main-formejur"
                            :class="{'valid': !$v.societeMain.formejur.$invalid, 'invalid': $v.societeMain.formejur.$invalid }" v-model="$v.societeMain.formejur.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.capital')" for="societe-main-capital">Capital</label>
                        <input type="text" class="form-control" name="capital" id="societe-main-capital"
                            :class="{'valid': !$v.societeMain.capital.$invalid, 'invalid': $v.societeMain.capital.$invalid }" v-model="$v.societeMain.capital.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.devisecap')" for="societe-main-devisecap">Devisecap</label>
                        <input type="text" class="form-control" name="devisecap" id="societe-main-devisecap"
                            :class="{'valid': !$v.societeMain.devisecap.$invalid, 'invalid': $v.societeMain.devisecap.$invalid }" v-model="$v.societeMain.devisecap.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.typecap')" for="societe-main-typecap">Typecap</label>
                        <input type="text" class="form-control" name="typecap" id="societe-main-typecap"
                            :class="{'valid': !$v.societeMain.typecap.$invalid, 'invalid': $v.societeMain.typecap.$invalid }" v-model="$v.societeMain.typecap.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.url')" for="societe-main-url">Url</label>
                        <input type="text" class="form-control" name="url" id="societe-main-url"
                            :class="{'valid': !$v.societeMain.url.$invalid, 'invalid': $v.societeMain.url.$invalid }" v-model="$v.societeMain.url.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.societeMain.competitor')" for="societe-main-competitor">Competitor</label>
                        <select class="form-control" id="societe-main-competitor" name="competitor" v-model="societeMain.competitor">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="societeMain.competitor && competitorsOption.id === societeMain.competitor.id ? societeMain.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.societeMain.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./societe-main-update.component.ts">
</script>
