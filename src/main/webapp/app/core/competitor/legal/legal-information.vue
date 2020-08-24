<template>
  <div>
      <span class="title" @click="refreshLegal()">{{ $t('kompetitors2App.legal.information.info') }}</span>
      <hr style="margin-bottom: 0.5rem">
      <div v-if="!infogreffe || infogreffe.length === 0">
          <div class="unit" v-if="legal && legal.length > 0">
              <div class="label">Siren: </div>
              <div class="value">{{legal[0].siren || 0}}</div>
          </div>
          <hr>
          <span class="grey">{{ $t('kompetitors2App.legal.information.no-info') }}</span>
      </div>
      <div v-else>
          <div class="unit" >
              <div class="label" style="font-weight: 600">{{ $t('kompetitors2App.legal.information.name') }}:</div>
              <div class="value" style="font-weight: 600">{{infogreffe[0].denomination || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.siren') }}: </div>
              <div class="value">{{infogreffe[0].siren || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.identity-sheet') }}: </div>
              <div class="value">
                  <a v-if="infogreffe[0].ficheidentite" :href="infogreffe[0].ficheidentite" target="_blank">
                      {{infogreffe[0].ficheidentite || 0}}
                  </a>
                  <div v-else>-</div>
              </div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.registration-date') }}: </div>
              <div class="value">{{infogreffe[0].dateImmatriculation || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.legal-form') }}: </div>
              <div class="value">{{infogreffe[0].formeJuridique || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.ape-code') }}: </div>
              <div class="value">{{infogreffe[0].codeApe || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.ape-label') }}: </div>
              <div class="value">{{infogreffe[0].libelleApe || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.address') }}: </div>
              <div class="value">{{infogreffe[0].adresse || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.postal') }}: </div>
              <div class="value">{{infogreffe[0].codePostal || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.city') }}: </div>
              <div class="value">{{infogreffe[0].ville || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.department') }}: </div>
              <div class="value">{{infogreffe[0].departement || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.department-number') }}: </div>
              <div class="value">{{infogreffe[0].numDept || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.region') }}: </div>
              <div class="value">{{infogreffe[0].region || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label">{{ $t('kompetitors2App.legal.information.fiscal-year-end') }}: </div>
              <div class="value">{{infogreffe[0].dateDeClotureExercice1 || 0}}</div>
          </div>
          <hr>
          <div class="unit" >
              <div class="label" style="font-weight: 600">{{ $t('kompetitors2App.legal.information.publication-month') }}: </div>
              <div class="value" style="font-weight: 600" v-text="publicationDate()"></div>
          </div>
          <hr>
      </div>
  </div>
</template>

<script lang="ts" src="./legal-information.component.ts">
</script>

<style scoped>
    .title{
        font-size: 20px;
        font-weight: 600;
    }
    .unit{
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    .label{
        font-size: 12px;
        text-align: left;
    }
    .value {
        font-size: 12px;
        text-align: right;
    }
    hr {
        margin-top: 0.5rem!important;
        margin-bottom: 0.5rem!important;
    }
    .grey {
        color: #cccccc!important;
        cursor: default;
    }
</style>
