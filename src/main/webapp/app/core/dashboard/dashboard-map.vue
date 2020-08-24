<template>
  <div class="fight-container">
      <div class="names">
          <span class="axis">X</span>
              <select class="form-control competitor" v-model="selectedFirst" >
              <option v-bind:value="item.name" v-for="item in allStatistics[0].statistics" :key="item.name">
                  {{  $t(item.name) }}
              </option>
          </select>
          <img src="../../../content/images/graph.svg" class="judo_pic">
          <select class="form-control competitor" v-model="selectedSecond" >
              <option v-bind:value="item.name" v-for="item in allStatistics[0].statistics" :key="item.name">
                  {{ $t(item.name) }}
              </option>
          </select>
          <span class="axis">Y</span>
           </div>
      <div v-if="selectedFirst && selectedSecond" style="padding: 3rem">
          <span class="formula">{{allStatistics.length}} companies found, {{companiesWithData().length}} companies have data.</span><span class="formula" style="color: #6B48FF"> Exclude from Deviation = </span>
          <select class="form-control formula" style="display: inline-block; width: 280px; color: #6B48FF" v-model.number="d" >
          <option v-bind:value="3">3 (Major Outliers)</option>
              <option v-bind:value="1.5">1.5 (All Outliers)</option>
          </select>
          <br>
              <span class="formula" style="text-decoration: underline">{{selectedStatistics.length}} companies selected.</span>
          <div class="map_body">
              <img v-if="getStat(item, selectedFirst) !== null && getStat(item, selectedSecond) !== null" v-for="item in selectedStatistics" :src="getLogo(item)" class="logo"
                   @click="showCompetitor(item.competitorId)" :style="'left: ' + getX(getStat(item, selectedFirst)) + 'px; top: ' + getY(getStat(item, selectedSecond)) + 'px;'"
                   :title="item.competitorName + ' - ' + $t(selectedFirst) + ' : ' + getStat(item, selectedFirst) + ', ' + $t(selectedSecond) + ' : ' + getStat(item, selectedSecond)">
              <div
                  v-if="getStat(item, selectedFirst) !== null && getStat(item, selectedSecond) !== null && (getStat(item, selectedSecond) < (func(getStat(item, selectedFirst)) - Number(c)))"
                  v-for="item in selectedStatistics" class="dot_red"
                  :style="'left: ' + getX(getStat(item, selectedFirst)) + 'px; top: ' + getY(getStat(item, selectedSecond)) + 'px;'"></div>
              <div
                  v-if="getStat(item, selectedFirst) !== null && getStat(item, selectedSecond) !== null && (getStat(item, selectedSecond) > (func(getStat(item, selectedFirst)) + Number(c)))"
                  v-for="item in selectedStatistics" class="dot_green"
                  :style="'left: ' + getX(getStat(item, selectedFirst)) + 'px; top: ' + getY(getStat(item, selectedSecond)) + 'px;'"></div>
              <div
                  v-if="getStat(item, selectedFirst) !== null && getStat(item, selectedSecond) !== null && (getStat(item, selectedSecond) <= (func(getStat(item, selectedFirst)) + Number(c))) && (getStat(item, selectedSecond) >= (func(getStat(item, selectedFirst)) - Number(c)))"
                  v-for="item in selectedStatistics" class="dot_grey"
                  :style="'left: ' + getX(getStat(item, selectedFirst)) + 'px; top: ' + getY(getStat(item, selectedSecond)) + 'px;'"></div>
              <hr class="axisX" :style="'top: ' + getY(0) + 'px;'">
              <hr class="axisY" :style="'left: ' + getX(0) + 'px;'">
              <div class="line1"
                   :style="'width: ' + getLineLength(0) + 'px; top: ' + getY(getLineY1(0)) + 'px; left: ' + getX(getLineX1(0)) + 'px; transform: rotate(' + getLineAngle(0) + 'deg); transform-origin: 0 0;'"></div>
              <div class="line2"
                   :style="'width: ' + getLineLength(Number(c)) + 'px; top: ' + getY(getLineY1(Number(c))) + 'px; left: ' + getX(getLineX1(Number(c))) + 'px; transform: rotate(' + getLineAngle(Number(c)) + 'deg); transform-origin: 0 0;'"></div>
              <div class="line2"
                   :style="'width: ' + getLineLength(-Number(c)) + 'px; top: ' + getY(getLineY1(-Number(c))) + 'px; left: ' + getX(getLineX1(-Number(c))) + 'px; transform: rotate(' + getLineAngle(-Number(c)) + 'deg); transform-origin: 0 0;'"></div>
              <div v-if="companiesWithData().length > 1" class="dot_mean"
                   :style="'left: ' + getX(average(selectedStatistics, selectedFirst)) + 'px; top: ' + getY(average(selectedStatistics, selectedSecond)) + 'px;'"
                   :title="'Average - ' + $t(selectedFirst) + ' : ' + average(selectedStatistics, selectedFirst) + ', ' + $t(selectedSecond) + ' : ' + average(selectedStatistics, selectedSecond)"></div>
          </div>
          <br>
          <span class="formula">Cutoff line : Y = X * </span><b-form-input class="pars" v-model.number="a"></b-form-input><span class="formula"> + </span><b-form-input class="pars" v-model.number="b"></b-form-input><span class="formula"> Margin : </span><b-form-input class="pars" v-model.number="c"></b-form-input>
           <br>
          <div class="GB_section">
              <div class="GB">
                  <span class="formula" :style="(badCompanies().length)>=3?'color: #008800;':'color: #C21F39;'">{{badCompanies().length}}</span><span class="formula"> Bad Companies:</span>
                  <div v-for="unit in badCompanies()" style="margin-top: 0.5rem;">
                      <img :src="getLogo(unit)" alt="logo" class="logo-image">
                      <div @click="showCompetitor(unit.competitorId)" class="competitor-name">{{ unit.competitorName }}</div>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">{{neutralCompanies().length}}</span><span class="formula"> Neutral Companies:</span>
                  <div v-for="unit in neutralCompanies()" style="margin-top: 0.5rem;">
                      <img :src="getLogo(unit)" alt="logo" class="logo-image">
                      <div @click="showCompetitor(unit.competitorId)" class="competitor-name">{{ unit.competitorName }}</div>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula" :style="(goodCompanies().length)>=3?'color: #008800;':'color: #C21F39;'">{{goodCompanies().length}}</span><span class="formula"> Good Companies:</span>
              <div v-for="unit in goodCompanies()" style="margin-top: 0.5rem;">
                  <img :src="getLogo(unit)" alt="logo" class="logo-image">
                  <div @click="showCompetitor(unit.competitorId)" class="competitor-name">{{ unit.competitorName }}</div>
              </div>
          </div>
          </div>
          <hr>
          <span class="formula">STATISTICS:</span>
          <div class="GB_section">
              <div class="GB">
                  <span class="formula">Stat Name</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span :style="(test(stat.name)!==null && (test(stat.name))<0.05)?'color : red;':''">{{$t(stat.name)}} </span>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">{{referenceStatistics.competitorName}}</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span><b>{{(getStat(referenceStatistics, stat.name) !==null)?getStat(referenceStatistics, stat.name).toFixed(2):'NA'}}</b></span>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">ALL:</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span><b>{{(average(allStatistics, stat.name) !== null)?average(allStatistics, stat.name).toFixed(2):'NA'}}</b></span>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">BAD:</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span :style="(test(stat.name)!==null && (test(stat.name))<0.05)?'color : red;':''"><b>{{(average(badCompanies(), stat.name) !== null)?average(badCompanies(), stat.name).toFixed(2):'NA'}}</b></span>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">GOOD:</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span :style="(test(stat.name)!==null && (test(stat.name))<0.05)?'color : red;':''"><b>{{(average(goodCompanies(), stat.name) !== null)?average(goodCompanies(), stat.name).toFixed(2):'NA'}}</b></span>
                  </div>
              </div>
              <div class="GB">
                  <span class="formula">TEST:</span><hr>
                  <div v-for="stat in allStatistics[0].statistics">
                      <span :style="(test(stat.name)!==null && (test(stat.name))<0.05)?'color : red;':''"><b>{{ (test(stat.name) !== null)?test(stat.name).toFixed(4):'NA' }}</b></span>
                  </div>
              </div>
          </div>
      </div>
      <br>
  </div>
</template>

<script lang="ts" src="./dashboard-map.component.ts">
</script>

<style scoped>


    .names {
        padding: 1rem 3rem;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    .judo_pic {
        height: 50px;
        padding: 0 2rem;
    }
    .competitor {
        font-weight: 600;
    }

    .axis {
        font-size: 20px;
        font-weight: 600;
        padding: 0 1rem;
    }

    .map_body {
        position: relative;
        width: 1000px;
        height: 1000px;
        background-color: #E8E8E8;
        border-style: solid;
        border-width: 2px;
        border-color: #888888;
        margin-top: 2rem;
    }
    .logo {
        width: 40px;
        height: 40px;
        object-fit: contain;
        cursor: pointer;
        position: absolute;
        top: 0px;
        left: 0px;
    }
    .axisX {
        width: 995px;
        position: absolute;
        top: 0px;
        left: 0px;
        margin: 0;
        padding: 0;
        border-top: 2px dotted #6B48FF;
    }
    .axisY {
        height: 995px;
        position: absolute;
        top: 0px;
        left: 0px;
        margin: 0;
        padding: 0;
        border-left: 2px dotted #6B48FF;
    }
    .dot_red {
        width: 10px;
        height: 10px;
        background-color: #C21F39;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: white;
        position: absolute;
    }
    .dot_green {
        width: 10px;
        height: 10px;
        background-color: #008800;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: white;
        position: absolute;
    }
    .dot_grey {
        width: 10px;
        height: 10px;
        background-color: #ffc107;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: black;
        position: absolute;
    }
    .dot_mean {
        width: 15px;
        height: 15px;
        background-color: white;
        border-radius: 10px;
        border-style: solid;
        border-width: 2px;
        border-color: red;
        position: absolute;
    }
    .dot2 {
        width: 4px;
        height: 4px;
        background-color: #7f7f7f;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: #555555;
        position: absolute;
    }
    .dot3 {
        width: 4px;
        height: 4px;
        background-color: #cccccc;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: #555555;
        position: absolute;
    }
    .pars {
        display: inline-block;
        width: 235px;
        font-size: 20px;
        font-weight: 600;
    }
    .formula {
        margin-top: 1rem;
        font-size: 20px;
        font-weight: 600;
    }
    .GB_section {
        margin-top: 3rem;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
    }
    .GB {

    }
    .logo-image {
        width: 70px;
        height: 70px;
        object-fit: contain;
        display: inline-block;
    }
    .competitor-name {
        display: inline-block;
        font-size: 18px;
        font-weight: 600;
        cursor: pointer;
        color: #5B3DD9;
    }
    .line1 {
        position: absolute;
        border-top: 3px dotted #7f7f7f;
    }
    .line2 {
        position: absolute;
        border-top: 2px dotted #888888;
    }
</style>
