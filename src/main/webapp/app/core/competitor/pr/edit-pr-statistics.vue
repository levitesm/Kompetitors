<template>
    <b-modal id="edit-pr-statistics-modal" scrollable lazy @shown="resetForm">
        <span slot="modal-title" class="kmp-modal__title" >
            <img class="kmp-modal__img" src="../../../../content/images/logo2.svg" alt="K">
            <span>Edit pr statistics</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form" v-on:submit.prevent="saveChanges()" @keyup.enter="saveChanges()">
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.marketing-workforce') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.marketingWorkforce" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.marketing-budget') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.marketingBudget" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.experience-feedback') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.experienceFeedback" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.linkedin-subs') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.linkedInSubscribers" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.linkedin-engagement') }}:</div>
                    <b-form-input type="text" v-model="prInfoModel.linkedInEngageRate"></b-form-input>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.linkedin-post-week') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.linkedInPostWeek" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.linkedin-post-day') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.linkedInPostDay" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.twitter-followers') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.twitterFollowers" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.twitter-posts-week') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.twitterPostWeek" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.twitter-posts-day') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.twitterPostDay" mask="############"></the-mask>
                </b-form-group>
                <b-form-group>
                    <div class="kmp-modal__text">{{ $t('pr-tab.statistics.instagram-followers') }}:</div>
                    <the-mask class="kmp-modal__input" type="text" v-model="prInfoModel.instagramFollowers" mask="############"></the-mask>
                </b-form-group>
                <div>
                    <b-alert show variant="danger" v-if="savingError">
                        <strong>{{ $t('edit-pr.failed-save') }}</strong>
                        <p>{{ savingError }}</p>
                    </b-alert>
                </div>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="kmp-modal__btn" @click="saveChanges">
            {{ $t('edit-pr.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./edit-pr-statistics.component.ts">
</script>

<style scoped lang="scss">
    @import "../../../../content/scss/kmp-modal.scss";

</style>
