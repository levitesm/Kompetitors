<template>
    <b-modal id="technology-edit-cto-modal" scrollable lazy>
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('technology-tab.information.cto-head-edit') }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="saveChanges()" @keyup.enter="saveChanges()">
                <b-form-group>
                    <div class="itm-txt">{{ $t('technology-tab.information.cto-head-fname') }}:</div>
                    <b-form-input class="edit_main_input" type="text" v-model="ctoModel.fName" ></b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('technology-tab.information.cto-head-lname') }}:</div>
                    <b-form-input class="edit_main_input" type="text" v-model="ctoModel.lName" ></b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('technology-tab.information.cto-url') }}:</div>
                    <b-form-input class="edit_main_input" type="text" v-model="ctoModel.linkedPage"></b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('technology-tab.information.tech-spec-count') }}:</div>
                    <b-form-input class="edit_main_input" type="text" v-model="techInfoModel.techSpecialistsNumber"></b-form-input>
                </b-form-group>
                <div>
                    <b-alert show variant="danger" v-if="savingError">
                        <strong>{{ $t('edit-pr.failed-save') }}</strong>
                        <p>{{ savingError }}</p>
                    </b-alert>
                </div>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="edit-cto-save" @click="saveChanges">
            {{ $t('edit-pr.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./technology-edit-cto.component.ts">
</script>

<style scoped>
    .edit-cto-save {
        background-color: #6b48ff;
        color: #ffffff;
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border-color: #6b48ff;
    }
    .tech-title {
        display: flex;
        flex-direction: row;
        font-size: 24px;
        font-weight: 600;
    }
    .title-img {
        margin-right: 10px;
    }
    .itm-txt {
        font-weight: 600;
    }
</style>
