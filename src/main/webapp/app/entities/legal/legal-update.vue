<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.legal.home.createOrEditLabel" v-text="$t('kompetitors2App.legal.home.createOrEditLabel')">Create or edit a Legal</h2>
                <div>
                    <div class="form-group" v-if="legal.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="legal.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.legalAddress')" for="legal-legalAddress">Legal Address</label>
                        <input type="text" class="form-control" name="legalAddress" id="legal-legalAddress"
                            :class="{'valid': !$v.legal.legalAddress.$invalid, 'invalid': $v.legal.legalAddress.$invalid }" v-model="$v.legal.legalAddress.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.siren')" for="legal-siren">Siren</label>
                        <input type="text" class="form-control" name="siren" id="legal-siren"
                            :class="{'valid': !$v.legal.siren.$invalid, 'invalid': $v.legal.siren.$invalid }" v-model="$v.legal.siren.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.greffe')" for="legal-greffe">Greffe</label>
                        <input type="text" class="form-control" name="greffe" id="legal-greffe"
                            :class="{'valid': !$v.legal.greffe.$invalid, 'invalid': $v.legal.greffe.$invalid }" v-model="$v.legal.greffe.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.founded')" for="legal-founded">Founded</label>
                        <div class="input-group">
                            <input id="legal-founded" type="date" class="form-control" name="founded"  :class="{'valid': !$v.legal.founded.$invalid, 'invalid': $v.legal.founded.$invalid }"
                            v-model="$v.legal.founded.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.updateDate')" for="legal-updateDate">Update Date</label>
                        <div class="input-group">
                            <input id="legal-updateDate" type="date" class="form-control" name="updateDate"  :class="{'valid': !$v.legal.updateDate.$invalid, 'invalid': $v.legal.updateDate.$invalid }"
                            v-model="$v.legal.updateDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.legalForm')" for="legal-legalForm">Legal Form</label>
                        <input type="text" class="form-control" name="legalForm" id="legal-legalForm"
                            :class="{'valid': !$v.legal.legalForm.$invalid, 'invalid': $v.legal.legalForm.$invalid }" v-model="$v.legal.legalForm.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.legal.competitor')" for="legal-competitor">Competitor</label>
                        <select class="form-control" id="legal-competitor" name="competitor" v-model="legal.competitor">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="legal.competitor && competitorsOption.id === legal.competitor.id ? legal.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.legal.ownership')" for="legal-ownership">Ownership</label>
                        <select class="form-control" id="legal-ownership" name="ownership" v-model="legal.ownership">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="legal.ownership && listOwnershipsOption.id === legal.ownership.id ? legal.ownership : listOwnershipsOption" v-for="listOwnershipsOption in listOwnerships" :key="listOwnershipsOption.id">{{listOwnershipsOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.legal.activity')" for="legal-activity">Activity</label>
                        <select class="form-control" id="legal-activity" name="activity" v-model="legal.activity">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="legal.activity && listActivitiesOption.id === legal.activity.id ? legal.activity : listActivitiesOption" v-for="listActivitiesOption in listActivities" :key="listActivitiesOption.id">{{listActivitiesOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.legal.pricing')" for="legal-pricing">Pricing</label>
                        <select class="form-control" id="legal-pricing" name="pricing" v-model="legal.pricing">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="legal.pricing && listPricingsOption.id === legal.pricing.id ? legal.pricing : listPricingsOption" v-for="listPricingsOption in listPricings" :key="listPricingsOption.id">{{listPricingsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.legal.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./legal-update.component.ts">
</script>
