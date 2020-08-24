<template>
   <div>
       <br>
       <div class="update_text"  @click="updateReps()">{{ $t('legal-tab.representatives.update') }} ({{ $t('legal-tab.representatives.last-update') }}: {{updateDate}})</div>
       <div v-if="this.numPP > 0" class="icon-edit suggest-changes"  @click="showEditReps()">&nbsp;&nbsp;{{ $t('legal-tab.representatives.suggest-changes') }}</div>
       <span class="clear-block"></span>
       <div v-if="this.numPP === 0 && this.numPM === 0">
           <span class="grey">{{ $t('legal-tab.representatives.no-info') }}</span>
       </div>
       <div v-if="this.numPP > 0">
       <div class="title">
           People:
       </div>
       <br>
       <span class="label tit">{{$t('kompetitors2App.representatives.qualite')}}</span>
       <span class="middle tit">{{$t('kompetitors2App.representatives.fullName')}}</span>
       <span class="linked tit">{{$t('kompetitors2App.representatives.linkedInUrl')}}</span>
       <span class="value tit">{{$t('kompetitors2App.representatives.dateNaissance')}}</span>
       <hr class="hr">

       <div v-for="r in this.representatives" v-if="!r.old && r.type === 'PP'">
           <span class="label">{{r.qualite}}</span>
           <span class="middle">{{r.prenom + ' ' + r.nom + ' ' + (r.nomUsage ? r.nomUsage : '')}}</span>
           <span class="linked">
               <a v-if="r.linkedInUrl !== ''" :href="r.linkedInUrl" target="_blank" >
                                              <img src="../../../content/images/linkedin.svg" class="small_logo_pic"></a>
               <img v-if="r.linkedInUrl === ''" src="../../../content/images/linkedin.svg" class="small_logo_pic_d" >
           </span>
           <span class="value">{{r.dateNaissance}}</span>
           <hr class="hr">
       </div>
       </div>
       <div v-if="this.numPM > 0">
       <br v-if="this.numPP > 0">
       <div class="title">
           Companies:
       </div>
       <br>
       <span class="c_left tit">{{$t('kompetitors2App.representatives.qualite')}}</span>
       <span class="c_middle tit">{{$t('kompetitors2App.representatives.denominationPM')}}</span>
       <span class="c_right tit">{{$t('kompetitors2App.representatives.sirenPM')}}</span>
       <hr class="hr">

       <div v-for="r in this.representatives" v-if="!r.old && r.type === 'PM'">
           <span class="c_left">{{r.qualite}}</span>
           <span class="c_middle">{{r.denominationPM}}</span>
           <span class="c_right siren" :class="sirenSet.includes(r.sirenPM)?'select':''" @click="addSiren(r.sirenPM)">{{r.sirenPM}}</span>
           <div class="inside" v-if="sirenSet.includes(r.sirenPM)">
               <representatives :siren="r.sirenPM" :mountFlag="false"></representatives>
           </div>
           <hr class="hr">
       </div>
       </div>
   </div>
</template>
<script lang="ts" src="./representatives.component.ts">
</script>
<style scoped>


    .unit{
        display: inline-block;
        width: 100%;
    }
    .title{
        font-size: 20px;
        font-weight: 600;
    }
    .label{
        display: inline-block;
        width: 30%;
        font-size: 12px;
        text-align: left;
    }
    .middle {
        display: inline-block;
        width: 40%;
        font-size: 12px;
        text-align: left;
    }
    .linked {
        display: inline-block;
        width: 12%;
        font-size: 12px;
        text-align: left;
    }
    .value {
        display: inline-block;
        width: 16%;
        font-size: 12px;
        text-align: right;
    }
    .c_left {
        display: inline-block;
        width: 30%;
        font-size: 12px;
        text-align: left;
    }
    .c_middle {
        display: inline-block;
        width: 59%;
        font-size: 12px;
        text-align: left;
    }
    .c_right {
        display: inline-block;
        width: 9%;
        font-size: 12px;
        text-align: right;
        font-weight: 600;
    }

    .hr {
        margin-top: 0.5rem!important;
        margin-bottom: 0.5rem!important;
    }

    .small_logo_pic{
        height: 24px;
        width: 24px;
        filter:
    }
    .small_logo_pic_d{
        height: 24px;
        width: 24px;
        filter: invert(50%);
    }
    .suggest-changes {
        width: 50%;
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
    .inside {
        display: inline-block;
        width: 98%;
        margin-left: 1%;
        border-left-style: dashed;
        border-width: 1px;
        padding-left: 1%;
    }
    .siren {
        cursor: pointer;
    }
    .siren:hover{
        text-decoration: underline;
    }
    .select {
        color: #6b48ff;
    }
    .grey {
        color: #cccccc!important;
        cursor: default;
    }
</style>
