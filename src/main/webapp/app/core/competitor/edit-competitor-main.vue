<template>
    <b-modal id="edit-competitor-main" scrollable lazy @shown="resetForm">
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('app.competitor-info')  }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="save" @keyup.enter="save">
                <div class="comp-tit">{{ globalGroupModel.name }}</div>
                <hr>
                <div class="form-group">
                    <label class="itm-txt" v-text="$t('edit-competitor-main.logo') + ':'" for="competitor-logo"></label>
                    <div>
                        <span v-on:click="selectFile()">
                            <img :src="getLogo()" alt="logo" class="competitor_logo_img" style="margin-bottom: 0">
                        </span>
                        <span v-if="globalGroupModel.logo" class="clearfix">
                            <span class="pull-left">{{ globalGroupModel.logoContentType}}, {{byteSize(globalGroupModel.logo)}}</span>
                            <img src="../../../content/icons/close-active.svg" style="height:24px;"
                                 v-on:click="resLogo()" alt="close">
                        </span>
                        <span v-else v-text="$t('edit-competitor-main.select-logo')"></span>
                    </div>
                    <input type="file" ref="fileLogo" v-on:change="setFileData($event, globalGroupModel, 'logo', true)" hidden/>
                    <input type="hidden" class="form-control" id="competitor-logo" v-model="globalGroupModel.logo"/>
                    <input type="hidden" class="form-control" v-model="globalGroupModel.logoContentType"/>
                </div>
                <b-form-group>
                    <div class="itm-txt">{{ $t('edit-competitor-main.website') }}</div>
                    <b-form-input class="edit_main_input" type="text" v-model="globalGroupModel.webSite">
                    </b-form-input>
                </b-form-group>
                <br>
                <div class="comp-tit">{{ competitorModel.name }}</div>
                <hr>
                <b-form-group>
                    <div class="itm-txt">{{ $t('edit-competitor-main.established') }}</div>
                    <the-mask type="text" class="edit_main_input form-control"
                              v-model="foundedModel"
                              placeholder="YYYY" mask="####"/>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('edit-competitor-main.website') }}</div>
                    <b-form-input class="edit_main_input" id="facebook" type="text" name="facebook" v-model="competitorModel.webSite">
                    </b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="itm-txt">{{ $t('edit-competitor-main.last-year-revenue') }} &#8364;:</div>
                    <b-form-input class="edit_main_input" id="facebook" type="text" name="facebook" v-model="revenueModel">
                    </b-form-input>
                </b-form-group>
            </b-form>
        </div>
        <div>
            <b-alert show variant="danger" v-if="savingError">
                <strong>{{ $t('edit-competitor-main.failed-save') }}</strong>
                <p>{{savingError}}</p>
            </b-alert>
        </div>
        <b-button slot="modal-footer" variant="primary" class="login" v-on:click="save">
            {{ $t('add-agency.save') }}
        </b-button>
    </b-modal>
</template>
<script lang="ts" src="./edit-competitor-main.component.ts">
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
    .edit_main_input {
        height: 48px !important;
        display: inline-block !important;
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
    .competitor_logo_img {
        height: 100px;
        width: 100px;
        object-fit: contain;
    }
</style>
