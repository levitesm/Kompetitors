<template>
    <div class="modal-body">
        <div class="">
            <div class="">
                <b-form role="form" v-on:submit.prevent="saveChanges()">
                    <b-form-group>
                        <div class="itm-txt">{{ $t('hr-tab.recruiters.fname') }}:</div>
                        <b-form-input class="edit_main_input" type="text" name="facebook" v-model="model.fName"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{ $t('hr-tab.recruiters.lname') }}:</div>
                        <b-form-input class="edit_main_input"type="text" name="facebook" v-model="model.lName"></b-form-input>
                    </b-form-group>
                    <b-form-group>
                        <div class="itm-txt">{{ $t('hr-tab.recruiters.url') }}:</div>
                        <b-form-input class="edit_main_input" type="text" name="facebook" v-model="model.linkedPage"></b-form-input>
                    </b-form-group>
                    <div>
                        <b-alert show variant="danger" v-if="savingError">
                            <strong>{{ $t('edit-pr.failed-save') }}</strong>
                            <p>{{ savingError }}</p>
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

<script lang="ts" src="./add-recruiter.component.ts">
</script>

<style scoped>
    .edit_main_input {
        height: 48px!important;
        display: inline-block!important;
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
</style>
