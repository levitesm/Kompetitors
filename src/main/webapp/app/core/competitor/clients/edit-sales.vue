<template>
    <div class="modal-body sign">
        <b-form role="form" v-on:submit.prevent="saveChanges()">
            <b-form-group>
                <div class="itm-txt">{{ $t('clients-tab.edit-sales.pricing-type') }}</div>
                <select class="form-control edit_main_input" id="offices" name="competitors" v-model="legalModel.pricing" >
                    <option :value="null"></option>
                    <option v-bind:value="pricingOption" v-for="pricingOption in pricingList" :key="pricingOption.id">{{ localisationFromName(pricingOption.value) }}</option>
                </select>
            </b-form-group>

            <b-form-group>
                <div class="itm-txt">{{ $t('clients-tab.edit-sales.head-of-sales-name') }}:</div>
                <b-form-input class="edit_main_input" id="cometitor-name" type="text" name="facebook" v-model="head_sales.fName"></b-form-input>
            </b-form-group>

            <b-form-group>
                <div class="itm-txt">{{ $t('clients-tab.edit-sales.head-of-sales-linkedIn') }}:</div>
                <b-form-input class="edit_main_input" id="cometitor-name" type="text" name="facebook" v-model="head_sales.linkedPage"></b-form-input>
            </b-form-group>

            <div>
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
</template>
<script lang="ts" src="./edit-sales.component.ts">
</script>
<style scoped>
    .edit_main_input {
        height: 48px!important;

        display: inline-block!important;
    }
    .details_area {
        display: inline-block;
        width: 455px!important;
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
