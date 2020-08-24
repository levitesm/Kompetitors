<template>
    <b-modal id="add-agency-modal" scrollable lazy @shown="setOffice">
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../content/images/logo2.svg" alt="K">
            <span v-if="officeModel.id" >{{ $t('add-agency.edit-agency') }}</span>
            <span v-else >{{ $t('add-agency.new-agency') }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save" @keyup.enter="save">
                <b-form-group>
                    <div v-if="officeModel.id" class="comp-tit">{{ selectedOffice ? selectedOffice.cityAsText : ''}} {{ $t('edit-competitor-main.updated-office.agency') }}</div>
                    <div v-else class="comp-tit">{{ competitor.name }}</div>
                    <hr>

                    <div class="itm-txt">{{ $t('add-agency.city') }}</div>
                    <b-form-input class="edit_main_input" id="city" type="text" v-model="officeModel.cityAsText"></b-form-input>

                    <div class="itm-txt">{{ $t('add-agency.postal-code') }}</div>
                    <the-mask type="text" class="edit_main_input form-control"
                              v-model="officeModel.post"
                              placeholder="#####" mask="#####"/>

                    <div class="itm-txt">{{ $t('add-agency.address') }}</div>
                    <b-form-input class="edit_main_input" id="address" type="text" v-model="officeModel.address"></b-form-input>

                    <div class="itm-txt">{{ $t('add-agency.phone') }}</div>
                    <b-form-input class="edit_main_input" id="phone" type="text" v-model="officeModel.phone"></b-form-input>

                    <div class="itm-txt">{{ $t('add-agency.foundation') }}</div>
                    <the-mask type="text" class="edit_main_input form-control"
                              v-model="establishedYear"
                              placeholder="YYYY" mask="####"/>

                    <div class="itm-txt">{{ $t('add-agency.employees') }}</div>
                    <b-form-input class="edit_main_input" id="facebook" type="text" v-model="officeModel.numberEmployees"></b-form-input>

                    <div class="itm-txt">{{ $t('add-agency.consultants') }}</div>
                    <b-form-input class="edit_main_input" id="facebook" type="text" v-model="officeModel.numberConsultants"></b-form-input>
                </b-form-group>
                <div>
                    <b-alert show variant="danger" v-if="savingError">
                        <strong>{{ $t('edit-pr.failed-save') }}</strong>
                        <p>{{ savingError }}</p>
                    </b-alert>
                </div>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="login" v-on:click="save">
            {{ $t('add-agency.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./add-agency.component.ts">
</script>

<style scoped>
.title-img {
    margin-right: 10px;
}
.tech-title {
    display: flex;
    flex-direction: row;
    font-size: 24px;
    font-weight: 600;
}
.edit_main_input {
    height: 48px!important;
    display: inline-block!important;
}
.itm-txt {
    font-weight: 600;
}
.login {
    background-color: #6b48ff;
    color: #ffffff;
    width: 100%;
    height: 40px;
    border-radius: 3px;
    border-color: #6b48ff;
}
.comp-tit {
    color: #6b48ff;
    font-size: 24px;
    font-weight: 600;
}
</style>
