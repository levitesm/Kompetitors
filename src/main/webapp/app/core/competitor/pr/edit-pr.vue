<template>
    <b-modal id="edit-pr-modal" scrollable lazy @shown="resetForm">
         <span slot="modal-title" class="kmp-modal__title">
            <img class="kmp-modal__img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('app.social-networks') }}</span>
        </span>
        <Question />
        <div>
            <b-form role="form" v-on:submit.prevent="saveChanges()" @keyup.enter="saveChanges()">
                <div class="sn-field">
                    <img src="../../../../content/images/facebook.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="facebookUrl" type="text" v-model="urlsModel.facebookUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/twitter.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="twitterUrl" type="text" v-model="urlsModel.twitterUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/linkedin.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="linkedinUrl" type="text" v-model="urlsModel.linkedinUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/instagram.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="instagramUrl" type="text" v-model="urlsModel.instagramUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/youtube.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="youtubeUrl" type="text" v-model="urlsModel.youtubeUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/blog.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="blogFeed" type="text" v-model="urlsModel.blogFeed">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div class="sn-field">
                    <img src="../../../../content/images/github.svg" class="sn-field__pic">
                    <b-form-group class="sn-field__input">
                        <b-form-input class="kmp-modal__input" id="githubUrl" type="text" v-model="urlsModel.githubUrl">
                        </b-form-input>
                    </b-form-group>
                </div>
                <div>
                    <b-alert show variant="danger" v-if="savingError">
                        <strong>{{ $t('edit-pr.failed-save') }}</strong>
                        <p>{{savingError}}</p>
                    </b-alert>
                </div>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="kmp-modal__btn" @click="saveChanges">
            {{ $t('edit-pr.save') }}
        </b-button>
    </b-modal>
</template>
<script lang="ts" src="./edit-pr.component.ts">
</script>
<style scoped lang="scss">
    @import "../../../../content/scss/kmp-modal.scss";

    .sn-field {
        display: flex;
        margin-top: 1rem;

        &__input {
            width: 100%;
            margin-left: .5rem;
        }

        &__pic {
            height: 40px;
            width: 40px;
        }
    }
</style>
