<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.societeMain.home.title')" id="societe-main-heading">Societe Mains</span>
            <router-link :to="{name: 'SocieteMainCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-societe-main">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.societeMain.home.createLabel')">
                    Create a new Societe Main
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
        <div class="alert alert-warning" v-if="!isFetching && societeMains && societeMains.length === 0">
            <span v-text="$t('kompetitors2App.societeMain.home.notFound')">No societeMains found</span>
        </div>
        <div class="table-responsive" v-if="societeMains && societeMains.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.siren')">Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.deno')">Deno</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.greffe')">Greffe</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.enseigne')">Enseigne</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.psiret')">Psiret</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.adresse')">Adresse</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.codepostal')">Codepostal</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.normcommune')">Normcommune</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.commune')">Commune</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.ape')">Ape</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.apetexte')">Apetexte</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.dateimmat')">Dateimmat</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.dcren')">Dcren</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.nationalite')">Nationalite</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.formejur')">Formejur</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.capital')">Capital</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.devisecap')">Devisecap</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.typecap')">Typecap</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.url')">Url</span></th>
                    <th><span v-text="$t('kompetitors2App.societeMain.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="societeMain in societeMains"
                    :key="societeMain.id">
                    <td>
                        <router-link :to="{name: 'SocieteMainView', params: {societeMainId: societeMain.id}}">{{societeMain.id}}</router-link>
                    </td>
                    <td>{{societeMain.siren}}</td>
                    <td>{{societeMain.deno}}</td>
                    <td>{{societeMain.greffe}}</td>
                    <td>{{societeMain.enseigne}}</td>
                    <td>{{societeMain.psiret}}</td>
                    <td>{{societeMain.adresse}}</td>
                    <td>{{societeMain.codepostal}}</td>
                    <td>{{societeMain.normcommune}}</td>
                    <td>{{societeMain.commune}}</td>
                    <td>{{societeMain.ape}}</td>
                    <td>{{societeMain.apetexte}}</td>
                    <td>{{societeMain.dateimmat}}</td>
                    <td>{{societeMain.dcren}}</td>
                    <td>{{societeMain.nationalite}}</td>
                    <td>{{societeMain.formejur}}</td>
                    <td>{{societeMain.capital}}</td>
                    <td>{{societeMain.devisecap}}</td>
                    <td>{{societeMain.typecap}}</td>
                    <td>{{societeMain.url}}</td>
                    <td>
                        <div v-if="societeMain.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: societeMain.competitor.id}}">{{societeMain.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'SocieteMainView', params: {societeMainId: societeMain.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'SocieteMainEdit', params: {societeMainId: societeMain.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(societeMain)"
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
            <span slot="modal-title"><span id="kompetitors2App.societeMain.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-societeMain-heading" v-bind:title="$t('kompetitors2App.societeMain.delete.question')">Are you sure you want to delete this Societe Main?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-societeMain" v-text="$t('entity.action.delete')" v-on:click="removeSocieteMain()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./societe-main.component.ts">
</script>
