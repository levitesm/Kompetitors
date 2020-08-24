<template>
    <b-modal id="technology-add-modal" scrollable lazy>
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ title }}</span>
        </span>
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save">
                <div v-if="entity === 'techPartners'" class="form-group">
                    <label class="itm-txt" v-text="$t('edit-competitor-main.logo') + ':'" for="model-logo"></label>
                    <div>
                        <span v-on:click="selectFile()">
                            <img :src="getLogo()" alt="logo" class="competitor_logo_img" style="margin-bottom: 0">
                        </span>
                        <span v-if="model.image" class="clearfix">
                            <span class="pull-left">{{ model.imageContentType }}, {{ byteSize(model.image) }}</span>
                            <img src="../../../../content/icons/close-active.svg" style="height:24px;"
                                 v-on:click="clearLogo()">
                        </span>
                        <span v-else v-text="$t('edit-competitor-main.select-logo')"></span>
                    </div>
                    <input type="file" ref="fileLogo" v-on:change="setFileData($event, model, 'image', true)" hidden/>
                    <input type="hidden" class="form-control" id="model-logo" v-model="model.image"/>
                    <input type="hidden" class="form-control" v-model="model.imageContentType"/>
                </div>
                <b-form-group>
                    <div class="itm-txt">{{ $t('technology-tab.information.name') }}:</div>
                    <b-form-input id="technology-name" type="text" v-model="model.value"></b-form-input>
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

<script lang="ts" src="./technology-add.component.ts">
</script>

<style scoped>
    .itm-txt {
        font-weight: 600;
    }
    .competitor_logo_img {
        height: 100px;
        width: 100px;
        object-fit: contain;
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
