<template>
    <div class="modal-body sign">
        <div>
            <div>
                <b-form role="form" v-on:submit.prevent="saveChanges()" >
                    <b-form-group>
                        <div class="itm-txt">{{ $t('add-competitor.name') }}</div>
                        <b-form-input
                            class="edit_main_input"
                            id="group-name"
                            type="text"
                            v-model="groupModel.name"
                            :disabled="!createGroup"
                        ></b-form-input>
                        <div class="itm-txt">{{ $t('add-competitor.companyName') }}</div>
                        <b-form-input
                            class="edit_main_input"
                            id="cometitor-name"
                            type="text"
                            v-model="competitorModel.name"
                        ></b-form-input>
                        <div class="itm-txt">{{ $t('add-competitor.country') }}</div>
                        <select class="form-control edit_main_input" v-model="selectedCountry" >
                            <option v-bind:value="countryOption" v-for="countryOption in listCountries" :key="countryOption.id">
                                {{ countryOption.value }}
                            </option>
                        </select>
                        <div class="itm-txt">{{ $t('add-competitor.siren') }}</div>
                        <the-mask type="text" class="edit_main_input form-control"
                              v-model="legalModel.siren"
                              placeholder="#########" mask="#########"/>
                    </b-form-group>
                    <div class="comp-tit">{{ $t('add-competitor.headquarter.title') }}</div>
                    <b-form-group>
                        <div class="itm-txt">{{ $t('add-competitor.headquarter.city') }}</div>
                        <b-form-input
                            class="edit_main_input"
                            id="cityAsText"
                            type="text"
                            v-model="officeModel.cityAsText">
                        </b-form-input>
                        <div class="itm-txt">{{ $t('add-competitor.headquarter.postalcode') }}</div>
                        <the-mask type="text" class="edit_main_input form-control"
                                  v-model="officeModel.post"
                                  placeholder="#####" mask="#####"
                        />
                        <div class="itm-txt">{{ $t('add-competitor.headquarter.address') }}</div>
                        <b-form-input class="edit_main_input" id="address" type="text" v-model="officeModel.address">
                        </b-form-input>
                        <div class="itm-txt">{{ $t('add-competitor.headquarter.phone') }}</div>
                        <b-form-input class="edit_main_input" id="phone" type="text" v-model="officeModel.phone">
                        </b-form-input>
                        <div class="itm-txt">{{ $t('add-competitor.headquarter.foundation') }}</div>
                        <the-mask type="text" class="edit_main_input form-control"
                                  v-model="established"
                                  placeholder="YYYY" mask="####"/>
                    </b-form-group>
                    <div>
                        <b-alert show variant="danger" v-if="savingError">
                            <strong>Failed to save!</strong>
                            <p>{{savingError}}</p>
                        </b-alert>
                    </div>
                    <div>
                        <b-button type="submit" variant="primary" class="login">{{ $t('add-competitor.save') }}</b-button>
                    </div>
                </b-form>
                <p></p>
            </div>
        </div>
    </div>
</template>
<script lang="ts" src="./add-competitor.component.ts">
</script>
<style scoped>
.edit_main_input {
    height: 48px!important;
    display: inline-block!important;
}
.comp-tit {
    color: #6b48ff;
    font-size: 24px;
    font-weight: 600;
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
    width: 490px;
    padding-top: 0!important;
}
.sign {
    width: 465px;
}
</style>
