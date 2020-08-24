<template>
  <div class="item-container">
      <div class="item-name">{{ $t(statisticItem.name) }}</div>
      <div class="value-container">
          <div>{{ displayValue(statisticItem.value) }}</div>
          <div class="value-unit">{{ displayUnit(statisticItem.value) }}</div>
          <div>{{ statisticItem.unit }}</div>
      </div>
      <span class="select-button" :class="{active: isActive}" @click="selectStat(statisticItem)">
          {{ $t('dashboard.compare') }}
      </span>
  </div>
</template>

<script lang="ts" src="./dashboard-statistic-item.component.ts">
</script>

<style scoped>
    .item-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        align-self: flex-end;
    }
    .item-name {
        color: #666666;
        font-size: 12px;
        line-height: 20px;
    }
    .value-container {
        display: flex;
        flex-direction: row;
        color: #262626;
        font-size: 56px;
        line-height: 46px;
        margin: 10px 0;
    }
    .value-unit {
        font-size: 20px;
        line-height: 20px;
        margin-top: auto;
        color: #262626;
    }
    .select-button {
        box-sizing: border-box;
        height: 45px;
        width: 118px;
        border: 1px solid #979797;
        border-radius: 20px;
        color: #8C8C8C;
        font-size: 14px;
        font-weight: 600;
        line-height: 42px;
        text-align: center;
        cursor: pointer;
    }
    .active {
        background-color: #6b48ff;
        color: white;
        cursor: none;
        pointer-events: none;
    }
</style>
