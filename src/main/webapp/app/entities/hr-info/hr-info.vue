<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('kompetitors2App.hrInfo.home.title')" id="hr-info-heading">Hr Infos</span>
            <router-link :to="{name: 'HrInfoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-hr-info">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('kompetitors2App.hrInfo.home.createLabel')">
                    Create a new Hr Info
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
        <div class="alert alert-warning" v-if="!isFetching && hrInfos && hrInfos.length === 0">
            <span v-text="$t('kompetitors2App.hrInfo.home.notFound')">No hrInfos found</span>
        </div>
        <div class="table-responsive" v-if="hrInfos && hrInfos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span v-text="$t('global.field.id')">ID</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.interviewsNumber')">Interviews Number</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.recrutmentTime')">Recrutment Time</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.reviewedCvPercent')">Reviewed Cv Percent</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.hrDetails')">Hr Details</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.vacanciesUrl')">Vacancies Url</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.hrSpecialistsNumber')">Hr Specialists Number</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.glassdoorRate')">Glassdoor Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.viadeoRate')">Viadeo Rate</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.glassdoorUrl')">Glassdoor Url</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.viadeoUrl')">Viadeo Url</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.cooptationPremiumAmount')">Cooptation Premium Amount</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.juniorSalary')">Junior Salary</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.averageSalary')">Average Salary</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.signingIncentives')">Signing Incentives</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.averageContractDuration')">Average Contract Duration</span></th>
                    <th><span v-text="$t('kompetitors2App.hrInfo.competitor')">Competitor</span></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="hrInfo in hrInfos"
                    :key="hrInfo.id">
                    <td>
                        <router-link :to="{name: 'HrInfoView', params: {hrInfoId: hrInfo.id}}">{{hrInfo.id}}</router-link>
                    </td>
                    <td>{{hrInfo.interviewsNumber}}</td>
                    <td>{{hrInfo.recrutmentTime}}</td>
                    <td>{{hrInfo.reviewedCvPercent}}</td>
                    <td>{{hrInfo.hrDetails}}</td>
                    <td>{{hrInfo.vacanciesUrl}}</td>
                    <td>{{hrInfo.hrSpecialistsNumber}}</td>
                    <td>{{hrInfo.glassdoorRate}}</td>
                    <td>{{hrInfo.viadeoRate}}</td>
                    <td>{{hrInfo.glassdoorUrl}}</td>
                    <td>{{hrInfo.viadeoUrl}}</td>
                    <td>{{hrInfo.cooptationPremiumAmount}}</td>
                    <td>{{hrInfo.juniorSalary}}</td>
                    <td>{{hrInfo.averageSalary}}</td>
                    <td>{{hrInfo.signingIncentives}}</td>
                    <td>{{hrInfo.averageContractDuration}}</td>
                    <td>
                        <div v-if="hrInfo.competitor">
                            <router-link :to="{name: 'CompetitorsView', params: {competitorId: hrInfo.competitor.id}}">{{hrInfo.competitor.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'HrInfoView', params: {hrInfoId: hrInfo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'HrInfoEdit', params: {hrInfoId: hrInfo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(hrInfo)"
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
            <span slot="modal-title"><span id="kompetitors2App.hrInfo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-hrInfo-heading" v-bind:title="$t('kompetitors2App.hrInfo.delete.question')">Are you sure you want to delete this Hr Info?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-hrInfo" v-text="$t('entity.action.delete')" v-on:click="removeHrInfo()">Delete</button>
            </div>
        </b-modal>
    </div>
</template>

<script lang="ts" src="./hr-info.component.ts">
</script>
