<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.hrInfo.home.createOrEditLabel" v-text="$t('kompetitors2App.hrInfo.home.createOrEditLabel')">Create or edit a HrInfo</h2>
                <div>
                    <div class="form-group" v-if="hrInfo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="hrInfo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.interviewsNumber')" for="hr-info-interviewsNumber">Interviews Number</label>
                        <input type="number" class="form-control" name="interviewsNumber" id="hr-info-interviewsNumber"
                            :class="{'valid': !$v.hrInfo.interviewsNumber.$invalid, 'invalid': $v.hrInfo.interviewsNumber.$invalid }" v-model.number="$v.hrInfo.interviewsNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.recrutmentTime')" for="hr-info-recrutmentTime">Recrutment Time</label>
                        <input type="text" class="form-control" name="recrutmentTime" id="hr-info-recrutmentTime"
                            :class="{'valid': !$v.hrInfo.recrutmentTime.$invalid, 'invalid': $v.hrInfo.recrutmentTime.$invalid }" v-model="$v.hrInfo.recrutmentTime.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.reviewedCvPercent')" for="hr-info-reviewedCvPercent">Reviewed Cv Percent</label>
                        <input type="number" class="form-control" name="reviewedCvPercent" id="hr-info-reviewedCvPercent"
                            :class="{'valid': !$v.hrInfo.reviewedCvPercent.$invalid, 'invalid': $v.hrInfo.reviewedCvPercent.$invalid }" v-model.number="$v.hrInfo.reviewedCvPercent.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.hrDetails')" for="hr-info-hrDetails">Hr Details</label>
                        <input type="text" class="form-control" name="hrDetails" id="hr-info-hrDetails"
                            :class="{'valid': !$v.hrInfo.hrDetails.$invalid, 'invalid': $v.hrInfo.hrDetails.$invalid }" v-model="$v.hrInfo.hrDetails.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.vacanciesUrl')" for="hr-info-vacanciesUrl">Vacancies Url</label>
                        <input type="text" class="form-control" name="vacanciesUrl" id="hr-info-vacanciesUrl"
                            :class="{'valid': !$v.hrInfo.vacanciesUrl.$invalid, 'invalid': $v.hrInfo.vacanciesUrl.$invalid }" v-model="$v.hrInfo.vacanciesUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.hrSpecialistsNumber')" for="hr-info-hrSpecialistsNumber">Hr Specialists Number</label>
                        <input type="number" class="form-control" name="hrSpecialistsNumber" id="hr-info-hrSpecialistsNumber"
                            :class="{'valid': !$v.hrInfo.hrSpecialistsNumber.$invalid, 'invalid': $v.hrInfo.hrSpecialistsNumber.$invalid }" v-model.number="$v.hrInfo.hrSpecialistsNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.glassdoorRate')" for="hr-info-glassdoorRate">Glassdoor Rate</label>
                        <input type="number" class="form-control" name="glassdoorRate" id="hr-info-glassdoorRate"
                            :class="{'valid': !$v.hrInfo.glassdoorRate.$invalid, 'invalid': $v.hrInfo.glassdoorRate.$invalid }" v-model.number="$v.hrInfo.glassdoorRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.viadeoRate')" for="hr-info-viadeoRate">Viadeo Rate</label>
                        <input type="number" class="form-control" name="viadeoRate" id="hr-info-viadeoRate"
                            :class="{'valid': !$v.hrInfo.viadeoRate.$invalid, 'invalid': $v.hrInfo.viadeoRate.$invalid }" v-model.number="$v.hrInfo.viadeoRate.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.glassdoorUrl')" for="hr-info-glassdoorUrl">Glassdoor Url</label>
                        <input type="text" class="form-control" name="glassdoorUrl" id="hr-info-glassdoorUrl"
                            :class="{'valid': !$v.hrInfo.glassdoorUrl.$invalid, 'invalid': $v.hrInfo.glassdoorUrl.$invalid }" v-model="$v.hrInfo.glassdoorUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.viadeoUrl')" for="hr-info-viadeoUrl">Viadeo Url</label>
                        <input type="text" class="form-control" name="viadeoUrl" id="hr-info-viadeoUrl"
                            :class="{'valid': !$v.hrInfo.viadeoUrl.$invalid, 'invalid': $v.hrInfo.viadeoUrl.$invalid }" v-model="$v.hrInfo.viadeoUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.cooptationPremiumAmount')" for="hr-info-cooptationPremiumAmount">Cooptation Premium Amount</label>
                        <input type="number" class="form-control" name="cooptationPremiumAmount" id="hr-info-cooptationPremiumAmount"
                            :class="{'valid': !$v.hrInfo.cooptationPremiumAmount.$invalid, 'invalid': $v.hrInfo.cooptationPremiumAmount.$invalid }" v-model.number="$v.hrInfo.cooptationPremiumAmount.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.juniorSalary')" for="hr-info-juniorSalary">Junior Salary</label>
                        <input type="number" class="form-control" name="juniorSalary" id="hr-info-juniorSalary"
                            :class="{'valid': !$v.hrInfo.juniorSalary.$invalid, 'invalid': $v.hrInfo.juniorSalary.$invalid }" v-model.number="$v.hrInfo.juniorSalary.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.averageSalary')" for="hr-info-averageSalary">Average Salary</label>
                        <input type="number" class="form-control" name="averageSalary" id="hr-info-averageSalary"
                            :class="{'valid': !$v.hrInfo.averageSalary.$invalid, 'invalid': $v.hrInfo.averageSalary.$invalid }" v-model.number="$v.hrInfo.averageSalary.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.signingIncentives')" for="hr-info-signingIncentives">Signing Incentives</label>
                        <input type="text" class="form-control" name="signingIncentives" id="hr-info-signingIncentives"
                            :class="{'valid': !$v.hrInfo.signingIncentives.$invalid, 'invalid': $v.hrInfo.signingIncentives.$invalid }" v-model="$v.hrInfo.signingIncentives.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.averageContractDuration')" for="hr-info-averageContractDuration">Average Contract Duration</label>
                        <input type="number" class="form-control" name="averageContractDuration" id="hr-info-averageContractDuration"
                            :class="{'valid': !$v.hrInfo.averageContractDuration.$invalid, 'invalid': $v.hrInfo.averageContractDuration.$invalid }" v-model.number="$v.hrInfo.averageContractDuration.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.hrInfo.competitor')" for="hr-info-competitor">Competitor</label>
                        <select class="form-control" id="hr-info-competitor" name="competitor" v-model="hrInfo.competitor">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="hrInfo.competitor && competitorsOption.id === hrInfo.competitor.id ? hrInfo.competitor : competitorsOption" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.hrInfo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./hr-info-update.component.ts">
</script>
