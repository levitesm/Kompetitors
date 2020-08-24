<template>
    <b-modal id="google-alerts-edit-modal" scrollable lazy @shown="resetForm">
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('pr-tab.google-alerts.edit-header') }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save" @keyup.enter="save()">
                <b-form-group>
                    <div class="itm-txt">{{ $t('pr-tab.google-alerts.feed') }}:</div>
                    <b-form-input id="technology-name" type="text" v-model="externalUrlsModel.googleAlertsFeed"></b-form-input>
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

<script lang="ts" src="./google-alerts-edit.component.ts">
</script>

<style scoped>
    .itm-txt {
        font-weight: 600;
    }
    .title-img {
        margin-right: 10px;
    }
    .tech-title {
        display: flex;
        flex-direction: row;
        font-size: 24px;
        font-weight: 600;
    }
</style>
