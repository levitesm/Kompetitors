<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.annualAccount.home.title')" id="annual-account-heading">Annual Accounts</span>
            <router-link :to="{name: 'AnnualAccountCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-annual-account">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.annualAccount.home.createLabel')">
                    Create a new Annual Account
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
        <div class="alert alert-warning" v-if="!isFetching && annualAccounts && annualAccounts.length === 0">
            <span v-text="$t('kompetitors2App.annualAccount.home.notFound')">No annualAccounts found</span>
        </div>
        <div class="table-responsive" v-if="annualAccounts && annualAccounts.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('siren')"><span v-text="$t('kompetitors2App.annualAccount.siren')">Siren</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('year')"><span v-text="$t('kompetitors2App.annualAccount.year')">Year</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('code')"><span v-text="$t('kompetitors2App.annualAccount.code')">Code</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('value')"><span v-text="$t('kompetitors2App.annualAccount.value')">Value</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="annualAccount in annualAccounts"
                    :key="annualAccount.id">
                    <td>
                        <router-link :to="{name: 'AnnualAccountView', params: {annualAccountId: annualAccount.id}}">{{annualAccount.id}}</router-link>
                    </td>
                    <td>{{annualAccount.siren}}</td>
                    <td>{{annualAccount.year}}</td>
                    <td>{{annualAccount.code}}</td>
                    <td>{{annualAccount.value}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AnnualAccountView', params: {annualAccountId: annualAccount.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AnnualAccountEdit', params: {annualAccountId: annualAccount.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(annualAccount)"
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
            <span slot="modal-title"><span id="kompetitors2App.annualAccount.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-annualAccount-heading" v-bind:title="$t('kompetitors2App.annualAccount.delete.question')">Are you sure you want to delete this Annual Account?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-annualAccount" v-text="$t('entity.action.delete')" v-on:click="removeAnnualAccount()">Delete</button>
            </div>
        </b-modal>
        <div v-show="annualAccounts && annualAccounts.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./annual-account.component.ts">
</script>
