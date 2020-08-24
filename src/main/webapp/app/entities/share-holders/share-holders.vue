<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.shareHolders.home.title')" id="share-holders-heading">{{$t('kompetitors2App.shareHolders.home.title')}}</span>
            <router-link :to="{name: 'ShareHoldersCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-share-holders">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.shareHolders.home.createLabel')">
                    {{$t('kompetitors2App.shareHolders.home.createLabel')}}
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
        <div class="alert alert-warning" v-if="!isFetching && shareHolders && shareHolders.length === 0">
            <span v-text="$t('kompetitors2App.shareHolders.home.notFound')">{{$t('kompetitors2App.shareHolders.home.notFound')}}</span>
        </div>
        <div class="table-responsive" v-if="shareHolders && shareHolders.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.competitorSiren')">{{$t('kompetitors2App.shareHolders.competitorSiren')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.typePersonne')">{{$t('kompetitors2App.shareHolders.typePersonne')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.denomination')">{{$t('kompetitors2App.shareHolders.denomination')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.civilite')">{{$t('kompetitors2App.shareHolders.civilite')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.nomPatronymique')">{{$t('kompetitors2App.shareHolders.nomPatronymique')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.nomUsage')">{{$t('kompetitors2App.shareHolders.nomUsage')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.prenom')">{{$t('kompetitors2App.shareHolders.prenom')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.libelleFormeJuridique')">{{$t('kompetitors2App.shareHolders.libelleFormeJuridique')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.codeFormeJuridique')">{{$t('kompetitors2App.shareHolders.codeFormeJuridique')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.siren')">{{$t('kompetitors2App.shareHolders.siren')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.codeApe')">{{$t('kompetitors2App.shareHolders.codeApe')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.dateNaissance')">{{$t('kompetitors2App.shareHolders.dateNaissance')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.nbrParts')">{{$t('kompetitors2App.shareHolders.nbrParts')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.pourcentageDetention')">{{$t('kompetitors2App.shareHolders.pourcentageDetention')}}</span></th>
                    <th><span v-text="$t('kompetitors2App.shareHolders.old')">{{$t('kompetitors2App.shareHolders.old')}}</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="shareHolders in shareHolders"
                    :key="shareHolders.id">
                    <td>
                        <router-link :to="{name: 'ShareHoldersView', params: {shareHoldersId: shareHolders.id}}">{{shareHolders.id}}</router-link>
                    </td>
                    <td>{{shareHolders.competitorSiren}}</td>
                    <td>{{shareHolders.typePersonne}}</td>
                    <td>{{shareHolders.denomination}}</td>
                    <td>{{shareHolders.civilite}}</td>
                    <td>{{shareHolders.nomPatronymique}}</td>
                    <td>{{shareHolders.nomUsage}}</td>
                    <td>{{shareHolders.prenom}}</td>
                    <td>{{shareHolders.libelleFormeJuridique}}</td>
                    <td>{{shareHolders.codeFormeJuridique}}</td>
                    <td>{{shareHolders.siren}}</td>
                    <td>{{shareHolders.codeApe}}</td>
                    <td>{{shareHolders.dateNaissance}}</td>
                    <td>{{shareHolders.nbrParts}}</td>
                    <td>{{shareHolders.pourcentageDetention}}</td>
                    <td>{{shareHolders.old}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ShareHoldersView', params: {shareHoldersId: shareHolders.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">{{$t('entity.action.view')}}</span>
                            </router-link>
                            <router-link :to="{name: 'ShareHoldersEdit', params: {shareHoldersId: shareHolders.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">{{$t('entity.action.edit')}}</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(shareHolders)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">{{$t('entity.action.delete')}}</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="kompetitors2App.shareHolders.delete.question" v-text="$t('entity.delete.title')">{{$t('entity.delete.title')}}</span></span>
            <div class="modal-body">
                <p id="jhi-delete-shareHolders-heading" v-bind:title="$t('kompetitors2App.shareHolders.delete.question')">{{$t('kompetitors2App.shareHolders.delete.question')}}</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">{{$t('entity.action.cancel')}}</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-shareHolders" v-text="$t('entity.action.delete')" v-on:click="removeShareHolders()">{{$t('entity.action.delete')}}</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./share-holders.component.ts">
</script>
