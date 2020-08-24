<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="kompetitors2App.externalUrls.home.createOrEditLabel" v-text="$t('kompetitors2App.externalUrls.home.createOrEditLabel')">Create or edit a ExternalUrls</h2>
                <div>
                    <div class="form-group" v-if="externalUrls.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="externalUrls.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.facebookUrl')" for="external-urls-facebookUrl">Facebook Url</label>
                        <input type="text" class="form-control" name="facebookUrl" id="external-urls-facebookUrl"
                            :class="{'valid': !$v.externalUrls.facebookUrl.$invalid, 'invalid': $v.externalUrls.facebookUrl.$invalid }" v-model="$v.externalUrls.facebookUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.twitterUrl')" for="external-urls-twitterUrl">Twitter Url</label>
                        <input type="text" class="form-control" name="twitterUrl" id="external-urls-twitterUrl"
                            :class="{'valid': !$v.externalUrls.twitterUrl.$invalid, 'invalid': $v.externalUrls.twitterUrl.$invalid }" v-model="$v.externalUrls.twitterUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.instagramUrl')" for="external-urls-instagramUrl">Instagram Url</label>
                        <input type="text" class="form-control" name="instagramUrl" id="external-urls-instagramUrl"
                            :class="{'valid': !$v.externalUrls.instagramUrl.$invalid, 'invalid': $v.externalUrls.instagramUrl.$invalid }" v-model="$v.externalUrls.instagramUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.youtubeUrl')" for="external-urls-youtubeUrl">Youtube Url</label>
                        <input type="text" class="form-control" name="youtubeUrl" id="external-urls-youtubeUrl"
                            :class="{'valid': !$v.externalUrls.youtubeUrl.$invalid, 'invalid': $v.externalUrls.youtubeUrl.$invalid }" v-model="$v.externalUrls.youtubeUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.linkedinUrl')" for="external-urls-linkedinUrl">Linkedin Url</label>
                        <input type="text" class="form-control" name="linkedinUrl" id="external-urls-linkedinUrl"
                            :class="{'valid': !$v.externalUrls.linkedinUrl.$invalid, 'invalid': $v.externalUrls.linkedinUrl.$invalid }" v-model="$v.externalUrls.linkedinUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.githubUrl')" for="external-urls-githubUrl">Github Url</label>
                        <input type="text" class="form-control" name="githubUrl" id="external-urls-githubUrl"
                            :class="{'valid': !$v.externalUrls.githubUrl.$invalid, 'invalid': $v.externalUrls.githubUrl.$invalid }" v-model="$v.externalUrls.githubUrl.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.blogFeed')" for="external-urls-blogFeed">Blog Feed</label>
                        <input type="text" class="form-control" name="blogFeed" id="external-urls-blogFeed"
                            :class="{'valid': !$v.externalUrls.blogFeed.$invalid, 'invalid': $v.externalUrls.blogFeed.$invalid }" v-model="$v.externalUrls.blogFeed.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.googleAlertsFeed')" for="external-urls-googleAlertsFeed">Google Alerts Feed</label>
                        <input type="text" class="form-control" name="googleAlertsFeed" id="external-urls-googleAlertsFeed"
                            :class="{'valid': !$v.externalUrls.googleAlertsFeed.$invalid, 'invalid': $v.externalUrls.googleAlertsFeed.$invalid }" v-model="$v.externalUrls.googleAlertsFeed.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('kompetitors2App.externalUrls.competitor')" for="external-urls-competitor">Competitor</label>
                        <select class="form-control" id="external-urls-competitor" name="competitor" v-model="externalUrls.competitorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="competitorsOption.id" v-for="competitorsOption in competitors" :key="competitorsOption.id">{{competitorsOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.externalUrls.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./external-urls-update.component.ts">
</script>
