<template>
    <b-modal id="edit-pricing-modal" scrollable lazy @close="reset">
        <span slot="modal-title">
            <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('clients-tab.pricing.add-employee-pricing-header') }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save">
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.role-name') }}:</div>
                    <b-form-select id="role-level-select" v-model="pricingModel.employeeRoleId" :options="roleOptions"></b-form-select>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.level') }}:</div>
                    <b-form-select id="role-level-select" v-model="pricingModel.level" :options="levels"></b-form-select>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.price') }}:</div>
                    <b-form-input id="role-level" type="text" v-model="pricingModel.price"></b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.currency') }}:</div>
                    <b-form-select id="role-currency-select" v-model="pricingModel.currency" :options="currencies"></b-form-select>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.payment-type') }}:</div>
                    <b-form-select id="role-currency-select" v-model="pricingModel.paymentType" :options="paymentTypes"></b-form-select>
                </b-form-group>
                <div>
                    <b-alert show variant="danger" v-if="savingError">
                        <strong>{{ $t('edit-pr.failed-save') }}</strong>
                        <p>{{ savingError }}</p>
                    </b-alert>
                </div>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="login" @click="save">
            {{ $t('add-agency.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./edit-pricing.component.ts">
</script>

<style scoped>
    .login {
        background-color: #6b48ff;
        color: #ffffff;
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border-color: #6b48ff;
    }
</style>
