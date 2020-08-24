<template>
    <div class="modal-body sign">
        <div class="">
            <div class="">
                <b-form role="form" v-on:submit.prevent="saveChanges()">
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-specialists-number')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="hrInfoModel.hrSpecialistsNumber"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-recruiters-number')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="hrInfoModel.recruitersNumber"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-interview')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="hrInfoModel.interviewsNumber"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-time')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="hrInfoModel.recrutmentTime"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <span class="checkB">{{$t('hr-tab.general.recruitment-tests')}}:</span>
                        <div class="test_sec">
                            <span>{{$t('hr-tab.general.technical-test') + ': '}}</span>
                            <select class="form-control edit_test_input" v-model="hrInfoModel.technicalTest">
                                <option :value="null" selected>N/A</option>
                                <option :value="true" selected>Yes</option>
                                <option :value="false" selected>No</option>
                            </select>
                        </div>
                        <div class="test_sec">
                            <span>{{$t('hr-tab.general.personality-test') + ': '}}</span>
                            <select class="form-control edit_test_input" v-model="hrInfoModel.personalityTest">
                                <option :value="null" selected>N/A</option>
                                <option :value="true" selected>Yes</option>
                                <option :value="false" selected>No</option>
                            </select>
                        </div>
                        <div class="test_sec">
                            <span>{{$t('hr-tab.general.english-test') + ': '}}</span>
                            <select class="form-control edit_test_input" v-model="hrInfoModel.englishTest">
                                <option :value="null" selected>N/A</option>
                                <option :value="true" selected>Yes</option>
                                <option :value="false" selected>No</option>
                            </select>
                        </div>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-details')}}:</div>
                        <b-form-textarea rows="3" max-rows="10" class="details_area" id="competitor-name" v-model="hrInfoModel.hrDetails"></b-form-textarea>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-signing-incentives')}}:</div>
                        <div class="hr_signing_incentives_hint">{{$t('hr-tab.general.hr-signing-incentives-hint')}}:</div>
                        <b-form-textarea rows="3" max-rows="10" class="details_area" id="signing-incentives" v-model="hrInfoModel.signingIncentives"></b-form-textarea>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.cooptation-premium-amount')}} (€):</div>
                        <b-form-input class="edit_main_input" id="cooptation-premium-amount" type="number" v-model="hrInfoModel.cooptationPremiumAmount"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.junior-salary')}} (€):</div>
                        <b-form-input class="edit_main_input" id="junior-salary" type="number" v-model="hrInfoModel.juniorSalary"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.average-salary')}} (€):</div>
                        <b-form-input class="edit_main_input" id="average-salary" type="number" v-model="hrInfoModel.averageSalary"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.average-contract-duration')}}:</div>
                        <b-form-input class="edit_main_input" id="average-contract-duration" type="number" v-model="hrInfoModel.averageContractDuration"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.open-job-positions-url')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="hrInfoModel.vacanciesUrl"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-head-name')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="headModel.fName"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-head-linked-in')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="headModel.linkedPage"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.recr-head-name')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="headModel2.fName"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.recr-head-linked-in')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" v-model="headModel2.linkedPage"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-glassdoor-rating')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" name="glassdoor-rate" v-model="hrInfoModel.glassdoorRate"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-glassdoor-url')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" name="glassdoor-url" v-model="hrInfoModel.glassdoorUrl"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-viadeo-rating')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" name="viadeo-rate" v-model="hrInfoModel.viadeoRate"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{$t('hr-tab.general.hr-viadeo-rating')}}:</div>
                        <b-form-input class="edit_main_input" id="competitor-name" type="text" name="viadeo-url" v-model="hrInfoModel.viadeoUrl"></b-form-input>
                    </b-form-group>
                    <div class="">
                        <b-alert show variant="danger" v-if="savingError">
                            <strong>{{ $t('edit-pr.failed-save') }}</strong>
                            <p>{{savingError}}</p>
                        </b-alert>
                    </div>
                    <div>
                        <b-button type="submit" variant="primary" class="login">{{ $t('edit-pr.save') }}</b-button>
                    </div>
                </b-form>
                <p></p>
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./edit-hr.component.ts">
</script>
<style scoped>
    .hr_signing_incentives_hint {
        font-size: 12px;
        font-weight: 400;
    }
    .edit_main_input {
        height: 48px!important;
        display: inline-block!important;
    }
    .details_area {
        display: inline-block;
    }

    .itm-txt {
        font-weight: 600;
    }
    .login {
        margin-top: 35px;
        background-color: #6b48ff;
        color: #ffffff;
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border-color: #6b48ff;
    }
    .modal-body {
        padding-top: 0!important;
    }
    .sign {
        width: 465px;
    }
    .checkB {
        font-weight: 600;
    }

    .edit_test_input {
        display: inline-block;
        width: 70px;
    }

    .test_sec {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
</style>
