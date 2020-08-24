<template>
    <b-modal id="add-employee-role-modal" scrollable lazy>
        <span slot="modal-title">
            <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('clients-tab.pricing.add-role-header') }}</span>
        </span>
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save">
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.pricing.role-name') }}:</div>
                    <b-form-input id="role-name" type="text" v-model="nameModel"></b-form-input>
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

<script lang="ts" src="./add-employee-role.component.ts">
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
