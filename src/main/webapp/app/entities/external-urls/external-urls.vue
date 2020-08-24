<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.externalUrls.home.title')" id="external-urls-heading">External Urls</span>
            <router-link :to="{name: 'ExternalUrlsCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-external-urls">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.externalUrls.home.createLabel')">
                    Create a new External Urls
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && externalUrls && externalUrls.length === 0">
            <span v-text="$t('kompetitors2App.externalUrls.home.notFound')">No externalUrls found</span>
        </div>
        <div class="table-responsive" v-if="externalUrls && externalUrls.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('facebookUrl')"><span v-text="$t('kompetitors2App.externalUrls.facebookUrl')">Facebook Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('twitterUrl')"><span v-text="$t('kompetitors2App.externalUrls.twitterUrl')">Twitter Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('instagramUrl')"><span v-text="$t('kompetitors2App.externalUrls.instagramUrl')">Instagram Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('youtubeUrl')"><span v-text="$t('kompetitors2App.externalUrls.youtubeUrl')">Youtube Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('linkedinUrl')"><span v-text="$t('kompetitors2App.externalUrls.linkedinUrl')">Linkedin Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('githubUrl')"><span v-text="$t('kompetitors2App.externalUrls.githubUrl')">Github Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('blogFeed')"><span v-text="$t('kompetitors2App.externalUrls.blogFeed')">Blog Feed</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('googleAlertsFeed')"><span v-text="$t('kompetitors2App.externalUrls.googleAlertsFeed')">Google Alerts Feed</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('competitorId')"><span v-text="$t('kompetitors2App.externalUrls.competitor')">Competitor</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="externalUrls in externalUrls"
                    :key="externalUrls.id">
                    <td>
                        <router-link :to="{name: 'ExternalUrlsView', params: {externalUrlsId: externalUrls.id}}">{{externalUrls.id}}</router-link>
                    </td>
                    <td>{{externalUrls.facebookUrl}}</td>
                    <td>{{externalUrls.twitterUrl}}</td>
                    <td>{{externalUrls.instagramUrl}}</td>
                    <td>{{externalUrls.youtubeUrl}}</td>
                    <td>{{externalUrls.linkedinUrl}}</td>
                    <td>{{externalUrls.githubUrl}}</td>
                    <td>{{externalUrls.blogFeed}}</td>
                    <td>{{externalUrls.googleAlertsFeed}}</td>
                    <td>
                        <div v-if="externalUrls.competitorId">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: externalUrls.competitorId}}">{{externalUrls.competitorId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ExternalUrlsView', params: {externalUrlsId: externalUrls.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ExternalUrlsEdit', params: {externalUrlsId: externalUrls.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(externalUrls)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kompetitors2App.externalUrls.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-externalUrls-heading" v-bind:title="$t('kompetitors2App.externalUrls.delete.question')">Are you sure you want to delete this External Urls?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-externalUrls" v-text="$t('entity.action.delete')" v-on:click="removeExternalUrls()">Delete</button>
            </div>
        </b-modal>
        <div v-show="externalUrls && externalUrls.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./external-urls.component.ts">
</script>
