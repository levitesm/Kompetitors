<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.listTechPartners.home.title')" id="list-tech-partners-heading">List Tech Partners</span>
            <router-link :to="{name: 'ListTechPartnersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-list-tech-partners">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.listTechPartners.home.createLabel')">
                    Create a new List Tech Partners
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
        <div class="alert alert-warning" v-if="!isFetching && listTechPartners && listTechPartners.length === 0">
            <span v-text="$t('kompetitors2App.listTechPartners.home.notFound')">No listTechPartners found</span>
        </div>
        <div class="table-responsive" v-if="listTechPartners && listTechPartners.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.listTechPartners.value')">Value</span></th>
                    <th><span v-text="$t('kompetitors2App.listTechPartners.image')">Image</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="listTechPartners in listTechPartners"
                    :key="listTechPartners.id">
                    <td>
                        <router-link :to="{name: 'ListTechPartnersView', params: {listTechPartnersId: listTechPartners.id}}">{{listTechPartners.id}}</router-link>
                    </td>
                    <td>{{listTechPartners.value}}</td>
                    <td>
                        <a v-if="listTechPartners.image" v-on:click="openFile(listTechPartners.imageContentType, listTechPartners.image)">
                            <img v-bind:src="'data:' + listTechPartners.imageContentType + ';base64,' + listTechPartners.image" style="max-height: 30px;" alt="listTechPartners image"/>
                        </a>
                        <span v-if="listTechPartners.image">{{listTechPartners.imageContentType}}, {{byteSize(listTechPartners.image)}}</span>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ListTechPartnersView', params: {listTechPartnersId: listTechPartners.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ListTechPartnersEdit', params: {listTechPartnersId: listTechPartners.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(listTechPartners)"
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
            <span slot="modal-title"><span id="kompetitors2App.listTechPartners.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-listTechPartners-heading" v-bind:title="$t('kompetitors2App.listTechPartners.delete.question')">Are you sure you want to delete this List Tech Partners?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-listTechPartners" v-text="$t('entity.action.delete')" v-on:click="removeListTechPartners()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./list-tech-partners.component.ts">
</script>
