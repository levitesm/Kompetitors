<template>
    <b-modal id="add-industry" scrollable lazy>
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('clients-tab.industries.add-industry-header') }}</span>
        </span>
    <div class="main">
            <b-form role="form" v-on:submit.prevent="save">
                <b-form-group>
                    <div class="itm-txt">{{ $t('clients-tab.industries.add-industry-name') }}</div>
                    <b-form-input id="industry-name" type="text" v-model="model.value"></b-form-input>
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
            {{ $t('edit-pr.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./add-industry.component.ts">
</script>

<style scoped>
    .itm-txt {
        font-weight: 600;
    }
</style>
