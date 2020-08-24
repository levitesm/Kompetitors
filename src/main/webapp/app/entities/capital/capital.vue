<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.capital.home.title')" id="capital-heading">Capitals</span>
            <router-link :to="{name: 'CapitalCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-capital">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.capital.home.createLabel')">
                    Create a new Capital
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
        <div class="alert alert-warning" v-if="!isFetching && capitals && capitals.length === 0">
            <span v-text="$t('kompetitors2App.capital.home.notFound')">No capitals found</span>
        </div>
        <div class="table-responsive" v-if="capitals && capitals.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.competitorSiren')">Competitor Siren</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.montant')">Montant</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.devise')">Devise</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.nbrParts')">Nbr Parts</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.pourcentageDetentionPP')">Pourcentage Detention PP</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.pourcentageDetentionPM')">Pourcentage Detention PM</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.listed')">Listed</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.privateCapital')">Private Capital</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.independentC')">Independent C</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.independentE')">Independent E</span></th>
                    <th><span v-text="$t('kompetitors2App.capital.old')">Old</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="capital in capitals"
                    :key="capital.id">
                    <td>
                        <router-link :to="{name: 'CapitalView', params: {capitalId: capital.id}}">{{capital.id}}</router-link>
                    </td>
                    <td>{{capital.competitorSiren}}</td>
                    <td>{{capital.montant}}</td>
                    <td>{{capital.devise}}</td>
                    <td>{{capital.nbrParts}}</td>
                    <td>{{capital.pourcentageDetentionPP}}</td>
                    <td>{{capital.pourcentageDetentionPM}}</td>
                    <td>{{capital.listed}}</td>
                    <td>{{capital.privateCapital}}</td>
                    <td>{{capital.independentC}}</td>
                    <td>{{capital.independentE}}</td>
                    <td>{{capital.old}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CapitalView', params: {capitalId: capital.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CapitalEdit', params: {capitalId: capital.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(capital)"
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
            <span slot="modal-title"><span id="kompetitors2App.capital.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-capital-heading" v-bind:title="$t('kompetitors2App.capital.delete.question')">Are you sure you want to delete this Capital?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-capital" v-text="$t('entity.action.delete')" v-on:click="removeCapital()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./capital.component.ts">
</script>
