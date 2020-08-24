<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.representatives.home.title')" id="representatives-heading">Representatives</span>
            <router-link :to="{name: 'RepresentativesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-representatives">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.representatives.home.createLabel')">
                    Create a new Representatives
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
        <div class="alert alert-warning" v-if="!isFetching && representatives && representatives.length === 0">
            <span v-text="$t('kompetitors2App.representatives.home.notFound')">No representatives found</span>
        </div>
        <div class="table-responsive" v-if="representatives && representatives.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.competitorSiren')">Competitor Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.qualite')">Qualite</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.type')">Type</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.nom')">Nom</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.prenom')">Prenom</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.nomUsage')">Nom Usage</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.dateNaissance')">Date Naissance</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.denominationPM')">Denomination PM</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.sirenPM')">Siren PM</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.linkedInUrl')">Linked In Url</span></th>
                    <th><span v-text="$t('kompetitors2App.representatives.old')">Old</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="representatives in representatives"
                    :key="representatives.id">
                    <td>
                        <router-link :to="{name: 'RepresentativesView', params: {representativesId: representatives.id}}">{{representatives.id}}</router-link>
                    </td>
                    <td>{{representatives.competitorSiren}}</td>
                    <td>{{representatives.qualite}}</td>
                    <td>{{representatives.type}}</td>
                    <td>{{representatives.nom}}</td>
                    <td>{{representatives.prenom}}</td>
                    <td>{{representatives.nomUsage}}</td>
                    <td>{{representatives.dateNaissance}}</td>
                    <td>{{representatives.denominationPM}}</td>
                    <td>{{representatives.sirenPM}}</td>
                    <td>{{representatives.linkedInUrl}}</td>
                    <td>{{representatives.old}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RepresentativesView', params: {representativesId: representatives.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RepresentativesEdit', params: {representativesId: representatives.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(representatives)"
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
            <span slot="modal-title"><span id="kompetitors2App.representatives.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-representatives-heading" v-bind:title="$t('kompetitors2App.representatives.delete.question')">Are you sure you want to delete this Representatives?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-representatives" v-text="$t('entity.action.delete')" v-on:click="removeRepresentatives()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./representatives.component.ts">
</script>
