<template>
  <div class="fight-container">
      <div class="names">
          <img :src="getLogo(selectedFirst)" class="logo" style="margin-right: 10px" @click="selectedFirst?showCompetitor(selectedFirst.competitorId):''" :style="selectedFirst?'cursor: pointer':''">
          <select class="form-control competitor" v-model="selectedFirst" >
              <option :value="null">{{ $t('dashboard.choose-company') }}</option>
              <option v-bind:value="item" v-for="item in currentStatistics" :key="item.competitorid">
                  {{ item.competitorName }}
              </option>
          </select>
          <img src="../../../content/images/judo.jpg" class="judo_pic">
          <select class="form-control competitor" v-model="selectedSecond" >
              <option :value="null">{{ $t('dashboard.choose-company') }}</option>
              <option v-bind:value="item" v-for="item in currentStatistics" :key="item.competitorid">
                  {{ item.competitorName }}
              </option>
          </select>
          <img :src="getLogo(selectedSecond)" class="logo" style="margin-left: 10px" @click="selectedSecond?showCompetitor(selectedSecond.competitorId):''" :style="selectedSecond?'cursor: pointer':''">
      </div>
      <div v-if="selectedFirst && selectedSecond">
          <div class="statistic" v-for="(stat, index) in selectedFirst.statistics">
              <hr style="margin: 0">
              <div class="stat_body">
                  <div class="inner-stat">
                      <div class="indicator" style="margin-right: 5rem"
                           :style="'background-color: ' + ((stat.value > selectedSecond.statistics[index].value)?'#1ee3cf':(stat.value < selectedSecond.statistics[index].value)?'#e60027':'#f49d22')"></div>
                      <div class="value">{{ displayValue(stat.value) + displayUnit(stat.value) + stat.unit }}</div>
                  </div>
                  <span class="stat_name">{{$t(stat.name)}}</span>
                  <div class="inner-stat">
                      <div class="value">{{ displayValue(selectedSecond.statistics[index].value) + displayUnit(selectedSecond.statistics[index].value) +
                          selectedSecond.statistics[index].unit }}
                      </div>
                      <div class="indicator" style="margin-left: 5rem"
                           :style="'background-color: ' + ((stat.value < selectedSecond.statistics[index].value)?'#1ee3cf':(stat.value > selectedSecond.statistics[index].value)?'#e60027':'#f49d22')"></div>
                  </div>
              </div>
              <hr style="margin: 0">
          </div>
      </div>

      <br>
  </div>
</template>

<script lang="ts" src="./dashboard-fight.component.ts">
</script>

<style scoped>

    .value {
        display: inline-block;
        font-size: 30px;
        font-weight: 400;
    }
    .names {
        padding: 1rem 3rem;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    .judo_pic {
        height: 50px;
    }
    .competitor {
        font-weight: 600;
    }
    .statistic {
        padding: 0 3rem;
        margin-bottom: 2rem;
        margin-top: 1rem;
    }
    .stat_body {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    .indicator {
        display: inline-block;
        width: 0.5rem;
        height: 100%;
    }
    .stat_name {
        padding: 1rem 0;
        color: #7f7f7f;
    }
    .inner-stat {
        display: flex;
        align-items: center;
    }
    .logo{
        width: 50px;
        height: 50px;
        margin-top: -7px;
        object-fit: contain;
    }
</style>
