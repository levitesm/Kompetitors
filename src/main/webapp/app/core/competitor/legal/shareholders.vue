<template>
   <div>
       <br>
       <div class="update_text"  @click="updateShares()">{{$t('legal-tab.shareholders.update')}} ({{$t('legal-tab.shareholders.last-update')}}: {{updateDate}})</div>
       <br>

       <div v-if="!this.capital">
           <span class="grey">{{$t('legal-tab.shareholders.no-info')}}</span>
       </div>
       <div v-if="this.capital">
           <div class="title">
               {{$t('kompetitors2App.capital.home.title')}}
           </div>
           <br>
           <hr class="hr">
           <span class="label">{{$t('kompetitors2App.capital.montant')}}</span>
           <span class="value">{{getMoney(capital.montant)}} </span>
           <hr class="hr">
           <span class="label">{{$t('kompetitors2App.capital.nbrParts')}}</span>
           <span class="value">{{capital.nbrParts}}</span>
           <hr class="hr">
           <span class="label">{{$t('kompetitors2App.capital.pourcentageDetentionPP')}}</span>
           <span class="value tit">{{capital.pourcentageDetentionPP}} %<span class="wrong" v-if="(capital.pourcentageDetentionPM + capital.pourcentageDetentionPP) !== 100">!</span></span>
           <hr class="hr">
           <span class="label">{{$t('kompetitors2App.capital.pourcentageDetentionPM')}}</span>
           <span class="value tit">{{capital.pourcentageDetentionPM}} %<span class="wrong" v-if="(capital.pourcentageDetentionPM + capital.pourcentageDetentionPP) !== 100">!</span></span>
           <hr class="hr">
           <br>

           <div>
               <br>
               <div class="CBSection1">
                   <b-form-checkbox id="listed" name="listed" v-model="isListed" @change="changeBox()" :disabled="!hasAccess(LEGAL_EDIT)">
                       <span class="checkB">{{$t('kompetitors2App.capital.listed')}}</span>
                   </b-form-checkbox>
               </div>
               <div class="CBSection2">
                   <b-form-checkbox id="independ" name="independ" v-model="isIndependent" disabled>
                       <span class="checkB">{{$t('kompetitors2App.capital.independent')}}</span>
                   </b-form-checkbox>
               </div>
               <div class="CBSection3">
               <b-form-checkbox id="privateE" name="privateE" v-model="isPrivateEquity" @change="changeBoxPE()" :disabled="!hasAccess(LEGAL_EDIT) || PEflag">
                   <span class="checkB">{{$t('kompetitors2App.capital.privateCapital')}}</span>
               </b-form-checkbox>
           </div>
               <br>
           </div>
       </div>

       <div v-if="this.isIndependent">
           <br><br>
           <div class="title">
               {{$t('legal-tab.shareholders.independent')}}
           </div>
           <br>
           <span class="c_left tit">{{$t('kompetitors2App.shareHolders.fullName')}}</span>
           <span class="c_middle tit">{{$t('kompetitors2App.shareHolders.dateNaissance')}}</span>
           <span class="c_middle2 tit">{{$t('kompetitors2App.shareHolders.nbrParts')}}</span>
           <span class="c_right tit">{{$t('kompetitors2App.shareHolders.pourcentageDetention')}}</span>
           <hr class="hr">

           <div v-for="ss in this.shareholders" v-if="!ss.old && ss.pourcentageDetention > 50">
               <div v-if="ss.typePersonne === 'Personne Physique'">
                   <span class="c_left">{{(ss.civilite?ss.civilite:'') + ' ' + ss.prenom + ' ' + (ss.nomPatronymique?ss.nomPatronymique:'') + ' ' + (ss.nomUsage?ss.nomUsage:'')}}</span>
                   <span class="c_middle">{{ss.dateNaissance}}</span>
                   <span class="c_middle2">{{ss.nbrParts}}</span>
                   <span class="c_right">
                   <div class="percent">{{ss.pourcentageDetention}} %</div>
                   <div class="percent2"><div class="bar" :style="getBarWidth(ss.pourcentageDetention)"></div></div>
               </span>
                   <hr class="hr">
               </div>

           </div>
       </div>

       <div v-if="this.isPrivateEquity">
           <br><br>
           <div class="title">
               {{$t('legal-tab.shareholders.private-equity-company')}}
           </div>
           <br>
           <div class="shareholders-row">
               <span class="c_left tit">{{$t('kompetitors2App.shareHolders.fullName')}}</span>
               <span class="c_middle tit">{{$t('kompetitors2App.shareHolders.codeApe')}}</span>
               <span class="c_middle2 tit">{{$t('kompetitors2App.shareHolders.nbrParts')}}</span>
               <span class="c_right tit">{{$t('kompetitors2App.shareHolders.pourcentageDetention')}}</span>
           </div>
           <hr class="hr">

           <div v-for="ss in this.shareholders" v-if="!ss.old && (ss.prenom === '@PRIVATE')">
               <div v-if="ss.typePersonne === 'Personne Morale'">
                   <div class="shareholders-row">
                       <div class="c_left">{{ss.denomination + (ss.libelleFormeJuridique?(' - ' + ss.libelleFormeJuridique):"")}}</div>
                       <div class="c_middle">{{ss.codeApe}}</div>
                       <div class="c_middle2">{{ss.nbrParts}}</div>
                       <div class="c_right shareholders-row">
                           <span class="percent">{{ss.pourcentageDetention}} %</span>
                           <div class="percent2">
                               <div class="bar" :style="getBarWidth(ss.pourcentageDetention)"></div>
                           </div>
                           <span class="icon-edit close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                                 @click="editShareholderPE(ss)" ></span>
                           <span class="icon-close close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                                 @click="deleteShareholder(ss)"></span>
                       </div>
                   </div>
                   <hr class="hr">
               </div>
           </div>
           <div class="form" v-if="showAddSsPE">
           <span class="c_left"><b-form-input class="ss_input" id="input" type="text" name="in" v-model="newSsPE.denomination"></b-form-input></span>
           <span class="c_middle">
               <b-form-input class="ss_input small" id="input" type="text" name="in" v-model="newSsPE.codeApe"></b-form-input>
               <b-form-input class="ss_input small" id="input" type="text" name="in" disabled value="Morale"></b-form-input>
           </span>
           <span class="c_middle2"><b-form-input class="ss_input" id="input" type="text" name="in" v-model="partsPE"></b-form-input></span>
           <span class="c_right">
               <b-form-input class="ss_input small" id="input" type="text" name="in" v-model="percPE"></b-form-input>
               <b-button class="add_butt" @click="saveSsPE()">Save</b-button>
                <span class="icon-close close-ico" @click="hideAddSsPE()"></span>
           </span>
       </div >
           <div v-if="!showAddSsPE" class="add_ss_but"
                :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                @click="showAddsspe()">
               {{$t('edit-pr.add')}}
           </div>
       </div>
       <b-alert style="margin-top: 1em" show variant="danger" v-if="errorPE!==''">
           <strong>{{ errorPE }}</strong>
       </b-alert>
       <div>
           <br><br>
           <div class="title">
               {{$t('legal-tab.shareholders.all')}}
           </div>
           <br>
           <div class="shareholders-row">
               <span class="c_left tit">{{$t('kompetitors2App.shareHolders.fullName')}}/{{$t('kompetitors2App.shareHolders.denomination')}}</span>
               <span class="c_middle tit">{{$t('kompetitors2App.shareHolders.codeApe')}}/{{$t('kompetitors2App.shareHolders.dateNaissance')}}</span>
               <span class="c_middle2 tit">{{$t('kompetitors2App.shareHolders.nbrParts')}}</span>
               <span class="c_right tit">{{$t('kompetitors2App.shareHolders.pourcentageDetention')}}</span>
           </div>
           <hr class="hr">

       <div v-for="ss in this.shareholders" v-if="!ss.old">
           <div v-if="ss.typePersonne === 'Personne Morale'">
               <div class="shareholders-row">
                   <span class="c_left">{{ss.denomination + (ss.libelleFormeJuridique?(' - ' + ss.libelleFormeJuridique):"")}}</span>
                   <span class="c_middle">{{ss.codeApe}}</span>
                   <span class="c_middle2">{{ss.nbrParts}}</span>
                   <span class="c_right shareholders-row">
                       <div class="percent">{{ss.pourcentageDetention}} %</div>
                       <div class="percent2">
                           <div class="bar" :style="getBarWidth(ss.pourcentageDetention)"></div>
                       </div>
                       <span class="icon-edit close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                             @click="editShareholderSS(ss)">
                       </span>
                       <span class="icon-close close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                             @click="deleteShareholder(ss)">
                       </span>
                   </span>
               </div>
               <hr class="hr">
           </div>
           <div v-if="ss.typePersonne === 'Personne Physique'">
               <div class="shareholders-row">
                   <span class="c_left">{{(ss.civilite?ss.civilite:'') + ' ' + ss.prenom + ' ' + (ss.nomPatronymique?ss.nomPatronymique:'') + ' ' + (ss.nomUsage?ss.nomUsage:'')}}</span>
                   <span class="c_middle">{{ss.dateNaissance}}</span>
                   <span class="c_middle2">{{ss.nbrParts}}</span>
                   <span class="c_right shareholders-row">
                       <div class="percent">{{ss.pourcentageDetention}} %</div>
                       <div class="percent2">
                           <div class="bar" :style="getBarWidth(ss.pourcentageDetention)"></div>
                       </div>
                       <span class="icon-edit close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                             @click="editShareholderSS(ss)">
                       </span>
                       <span class="icon-close close-ico" :class="{'no-access': !hasAccess(LEGAL_EDIT)}"
                             @click="deleteShareholder(ss)">
                       </span>
                   </span>
               </div>
               <hr class="hr">
           </div>
           
       </div>
           <div class="form" v-if="showAddSS">
           <span class="c_left"><b-form-input class="ss_input" id="input" type="text" name="in" v-model="newSS.denomination"></b-form-input></span>
           <span class="c_middle">
               <b-form-input class="ss_input small" id="input" type="text" name="in" v-model="newSS.codeApe"></b-form-input>
               <select :disabled="this.specFlag" class="form-control ss_input2 small" id="input" name="in" v-model="newSS.typePersonne" >
                   <option value="Personne Morale" selected="selected">{{$t('kompetitors2App.shareHolders.morale')}}</option>
                   <option value="Personne Physique">{{$t('kompetitors2App.shareHolders.physique')}}</option>
               </select>
           </span>
           <span class="c_middle2"><b-form-input class="ss_input" id="input" type="text" name="in" v-model="partsSS"></b-form-input></span>
           <span class="c_right">

               <b-form-input class="ss_input small" id="input" type="text" name="in" v-model="percSS"></b-form-input>
               <b-button class="add_butt" @click="saveSS()">{{$t('edit-pr.save')}}</b-button>
                <span class="icon-close close-ico" @click="hideAddSS()"></span>
           </span>
           </div>
           <div v-if="!showAddSS" class="add_ss_but" :class="{'no-access': !hasAccess(LEGAL_EDIT)}" @click="showAddss()">{{$t('edit-pr.add')}}</div>
           <b-alert style="margin-top: 1em" show variant="danger" v-if="errorSS!==''">
               <strong>{{ errorSS }}</strong>
           </b-alert>
   </div>

   </div>
</template>
<script lang="ts" src="./shareholders.component.ts">
</script>
<style scoped>
    .title{
        font-size: 20px;
        font-weight: 600;
    }
    .label{
        display: inline-block;
        width: 70%;
        font-size: 12px;
        text-align: left;
    }

    .value {
        display: inline-block;
        width: 29%;
        font-size: 12px;
        text-align: right;
    }
    .checkB {
        color: #262626;
        font-weight: 600;
        font-size: 12px;
    }
    .c_left {
        display: inline-block;
        width: 46%;
        font-size: 12px;
        text-align: left;
    }
    .c_middle {
        display: inline-block;
        width: 20%;
        font-size: 12px;
        text-align: left;
        vertical-align: bottom;
    }
    .c_middle2 {
        display: inline-block;
        width: 12%;
        font-size: 12px;
        text-align: left;
    }
    .c_right {
        display: inline-block;
        width: 18%;
        font-size: 12px;
        text-align: left;
        font-weight: 600;
        vertical-align: bottom;
    }

    .percent {
        width: 25%;
        display: inline-block;
        white-space: nowrap;
    }
    .percent2 {
        display: inline-block;
        width: 57%;
        border-right-style: solid;
        border-left-style: solid;
        border-width: 1px;
    }
    .bar {
        background-color: #6b48ff;
        display: inline-block;
        height: 8px;
    }
    .hr {
        margin-top: 0.5rem!important;
        margin-bottom: 0.5rem!important;
    }

    .update_text {
        color: #6b48ff;
        font-size: 14px;
        display: inline-block;
        text-align: left;
        width: 49%;
        cursor: pointer;
    }
    .update_text:hover{
        text-decoration: underline;
    }
    .tit {
        font-weight: 600;
    }
    .CBSection1 {
        display: inline-block;
        width: 32%;
        text-align: left;
    }
    .CBSection2 {
        display: inline-block;
        width: 33%;
        text-align: center;
    }
    .CBSection3 {
        display: inline-block;
        width: 33%;
        text-align: right;
    }
    .grey {
        color: #cccccc!important;
        cursor: default;
    }
    .wrong {
        background-color: #e60027;
        color: #ffffff;
        font-weight: 600;
        border-radius: 2em;
        padding-left: 1em;
        padding-right: 1em;
        padding-top: 0.5em;
        padding-bottom: 0.5em;
        margin-left: 1em;
    }
    .close-ico {
        width: 15px;
        height: 15px;
        margin-left: .5rem;
    }
    .close-ico:hover {
        cursor: pointer;
    }
    .ss_input {
        width: 100%;
        display: inline-block;
        height: 40px;
        margin-top: 1px;
    }
    .ss_input2 {
        width: 100%;
        display: inline-block;
        height: 40px;
        margin-top: 2px;
    }
    .small {
        width: 49% !important;
    }
    .add_butt {
        background-color: #6b48ff;
        color: #ffffff;
        width: 39%;
        height: 41px;
        border-radius: 3px;
        border-color: #6b48ff;
        border-width: 0;
        font-size: 14px;
        font-weight: 600;
        margin-top: -7px;
    }
    .add_butt:hover{
        background-color: #6242eb;
    }
    .form {

    }
    .add_ss_but {
        width: 100%;
        text-align: right;
        color: #6b48ff;
        font-size: 14px;
    }
    .add_ss_but:hover {
        text-decoration: underline;
        cursor: pointer;
    }
    .shareholders-row {
        display: flex;
        align-items: center;
    }
</style>
