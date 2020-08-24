<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.prInfo.home.createOrEditLabel" v-text="$t('kompetitors2App.prInfo.home.createOrEditLabel')">Create or edit a PrInfo</h2>
                <div>
                    <div class="form-group" v-if="prInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="prInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.date')" for="pr-info-date">Date</label>
                        <div class="input-group">
                            <input id="pr-info-date" type="date" class="form-control" name="date"  :class="{'valid': !$v.prInfo.date.$invalid, 'invalid': $v.prInfo.date.$invalid }"
                            v-model="$v.prInfo.date.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.marketingWorkforce')" for="pr-info-marketingWorkforce">Marketing Workforce</label>
                        <input type="number" class="form-control" name="marketingWorkforce" id="pr-info-marketingWorkforce"
                            :class="{'valid': !$v.prInfo.marketingWorkforce.$invalid, 'invalid': $v.prInfo.marketingWorkforce.$invalid }" v-model.number="$v.prInfo.marketingWorkforce.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.marketingBudget')" for="pr-info-marketingBudget">Marketing Budget</label>
                        <input type="number" class="form-control" name="marketingBudget" id="pr-info-marketingBudget"
                            :class="{'valid': !$v.prInfo.marketingBudget.$invalid, 'invalid': $v.prInfo.marketingBudget.$invalid }" v-model.number="$v.prInfo.marketingBudget.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.experienceFeedback')" for="pr-info-experienceFeedback">Experience Feedback</label>
                        <input type="number" class="form-control" name="experienceFeedback" id="pr-info-experienceFeedback"
                            :class="{'valid': !$v.prInfo.experienceFeedback.$invalid, 'invalid': $v.prInfo.experienceFeedback.$invalid }" v-model.number="$v.prInfo.experienceFeedback.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.linkedInSubscribers')" for="pr-info-linkedInSubscribers">Linked In Subscribers</label>
                        <input type="number" class="form-control" name="linkedInSubscribers" id="pr-info-linkedInSubscribers"
                            :class="{'valid': !$v.prInfo.linkedInSubscribers.$invalid, 'invalid': $v.prInfo.linkedInSubscribers.$invalid }" v-model.number="$v.prInfo.linkedInSubscribers.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.linkedInEngageRate')" for="pr-info-linkedInEngageRate">Linked In Engage Rate</label>
                        <input type="number" class="form-control" name="linkedInEngageRate" id="pr-info-linkedInEngageRate"
                            :class="{'valid': !$v.prInfo.linkedInEngageRate.$invalid, 'invalid': $v.prInfo.linkedInEngageRate.$invalid }" v-model.number="$v.prInfo.linkedInEngageRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.linkedInPostWeek')" for="pr-info-linkedInPostWeek">Linked In Post Week</label>
                        <input type="number" class="form-control" name="linkedInPostWeek" id="pr-info-linkedInPostWeek"
                            :class="{'valid': !$v.prInfo.linkedInPostWeek.$invalid, 'invalid': $v.prInfo.linkedInPostWeek.$invalid }" v-model.number="$v.prInfo.linkedInPostWeek.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.linkedInPostDay')" for="pr-info-linkedInPostDay">Linked In Post Day</label>
                        <input type="number" class="form-control" name="linkedInPostDay" id="pr-info-linkedInPostDay"
                            :class="{'valid': !$v.prInfo.linkedInPostDay.$invalid, 'invalid': $v.prInfo.linkedInPostDay.$invalid }" v-model.number="$v.prInfo.linkedInPostDay.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.twitterFollowers')" for="pr-info-twitterFollowers">Twitter Followers</label>
                        <input type="number" class="form-control" name="twitterFollowers" id="pr-info-twitterFollowers"
                            :class="{'valid': !$v.prInfo.twitterFollowers.$invalid, 'invalid': $v.prInfo.twitterFollowers.$invalid }" v-model.number="$v.prInfo.twitterFollowers.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.twitterPostWeek')" for="pr-info-twitterPostWeek">Twitter Post Week</label>
                        <input type="number" class="form-control" name="twitterPostWeek" id="pr-info-twitterPostWeek"
                            :class="{'valid': !$v.prInfo.twitterPostWeek.$invalid, 'invalid': $v.prInfo.twitterPostWeek.$invalid }" v-model.number="$v.prInfo.twitterPostWeek.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.twitterPostDay')" for="pr-info-twitterPostDay">Twitter Post Day</label>
                        <input type="number" class="form-control" name="twitterPostDay" id="pr-info-twitterPostDay"
                            :class="{'valid': !$v.prInfo.twitterPostDay.$invalid, 'invalid': $v.prInfo.twitterPostDay.$invalid }" v-model.number="$v.prInfo.twitterPostDay.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.prInfo.instagramFollowers')" for="pr-info-instagramFollowers">Instagram Followers</label>
                        <input type="number" class="form-control" name="instagramFollowers" id="pr-info-instagramFollowers"
                            :class="{'valid': !$v.prInfo.instagramFollowers.$invalid, 'invalid': $v.prInfo.instagramFollowers.$invalid }" v-model.number="$v.prInfo.instagramFollowers.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('kompetitors2App.prInfo.competitors')" for="pr-info-competitors">Competitors</label>
                        <select class="form-control" id="pr-info-competitors" name="competitors" v-model="prInfo.competitorsId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.prInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./pr-info-update.component.ts">
</script>
