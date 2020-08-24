<template>
    <div>
        <div class="icon-edit suggest-changes"
             :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
             @click="showEditClients()">
            &nbsp;&nbsp;{{ $t('competitor.bottom-main-section.suggest-changes') }}
        </div>
        <div>
            <competitive-rate style="display: inline-block" :title="$t('clients-tab.general.total-competitive-rate-clients')" :rate="rate"></competitive-rate>
            <hr class="vr">
            <div class="clients_numbers_part">
                <span class="numbers_titles">{{ $t('clients-tab.general.sales-manager-total') }}</span>
                <br>
                <span class="numbers_values">0</span>
            </div>
            <hr class="vr">
            <div class="clients_numbers_part">
                <head-of
                    :title="$t('clients-tab.general.head-of-sales')" :name="headDisplayName"
                    :linkedinUrl="headSales && headSales.linkedPage ? headSales.linkedPage : ''">
                </head-of>
            </div>
            <hr class="vr">
            <div class="clients_numbers_part right_part">
                <span class="numbers_titles">{{ $t('clients-tab.general.pricing') }}</span>
                <br>
                <div class="head_of_mini_section">{{pricing || '-'}}</div>
            </div>
        </div>
        <br>
        <br>
        <div class="all_title red">{{ $t('clients-tab.general.all-agencies-clients') }}</div>
        <div class="add" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
             @click="addClient()">{{ $t('clients-tab.general.add-new-client') }}
        </div>
        <br>
        <div v-if="clients.length > 0">
            <div class="colmn tit">{{ $t('clients-tab.general.client-name') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.partners-since') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.agency') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.client-industry') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.projects') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.status') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.ippons-client') }}</div>
            <div class="colmn tit">{{ $t('clients-tab.general.last-update') }}</div>
        </div>
        <div v-if="clients.length === 0">
            <span class="grey">{{ $t('clients-tab.general.no-client') }}</span>
        </div>

        <div class="unit" v-for="c in clients">
            <hr class="hr">
            <div class="colmn">{{c.name}}</div>
            <div class="colmn">{{c.since}}</div>
            <div class="colmn">{{c.offices.cityAsText || c.offices.city.value}}</div>
            <div class="colmn">{{getIndustry(c.industry)}}</div>
            <div class="colmn">
                <span v-for="p in c.projects">{{p.projectType.value}}</span>
            </div>
            <div class="colmn">{{getStatus(c)}}</div>
            <div class="colmn">{{c.isIppon ? $t('yes') : $t('no')}}</div>
            <div class="colmn">
                {{c.updateDate}}
                <span class="icon-close close-ico" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
                      @click="deleteClient(c)"></span>
                <span class="icon-edit close-ico" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
                      @click="showEditOneClient(c)"></span>
            </div>
        </div>


        <b-modal id="add-client" hide-footer lazy>
           <span slot="modal-title" id="add-client" class="sign-in-title">
               <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
               <span class="tit">{{ $t('clients-tab.general.add-client') }}</span>
           </span>
            <Question />
            <add-client :clientId="addClientId"></add-client>
        </b-modal>

        <b-modal id="edit-clients-page" hide-footer lazy>
           <span slot="modal-title" id="edit-clients-title" class="sign-in-title">
               <img class="logo-img" src="../../../../content/images/logo2.svg" alt="K">
               <span class="tit">{{ $t('clients-tab.general.edit-sales-info') }}</span>
           </span>
            <Question />
            <edit-sales></edit-sales>
        </b-modal>
    </div>
</template>
<script lang="ts" src="./clients.component.ts">
</script>
<style scoped>
    .vr {
        display: inline-block;
        height: 50px;
        width: 0;
        border: 1px solid #D6DADF;
        margin: 30px 2% 50px 20px;
    }

    .small_logo_pic {
        height: 24px;
        width: 24px;
        filter:
    }

    .small_logo_pic_d {
        height: 24px;
        width: 24px;
        filter: invert(50%);
    }

    .suggest-changes {
        margin: 0;
        width: 100%;
        text-align: end;
    }

    .all_title {
        font-weight: 600;
        font-size: 24px;
        display: inline-block;
    }

    .add {
        font-size: 14px;
        color: #6b48ff;
        display: inline-block;
        float: right;
    }

    .add:hover {
        cursor: pointer;
        text-decoration: underline;
    }

    .colmn {
        display: inline-block;
        font-size: 10px;
        width: 11%;
    }

    .tit {
        font-weight: 600;
        margin-top: 2em;
    }

    .hr {
        margin-top: 0.5em;
        margin-bottom: 0.5em;
    }

    .grey {
        color: #cccccc !important;
        cursor: default;
    }

    .close-ico {
        width: 15px;
        height: 15px;
        float: right;
    }

    .close-ico:hover {
        cursor: pointer;
    }

    .clients_numbers_part {
        display: inline-block;
        margin-left: 0px;
        margin-right: 0px;
        width: 18%;
        vertical-align: top;
    }

    .red {
        color: #ff0000;
    }
</style>
