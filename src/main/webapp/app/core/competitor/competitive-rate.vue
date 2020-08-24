<template>
    <div class="rate-container">
        <span class="rate-title">{{ title }}</span>
        <span class="rate-value">{{ rateChecked().toFixed(0) + '%' }}</span>
        <div class="rate-stars">
            <span v-for="i in ratingOn()" class="icon-note star_ico_on"></span>
            <span v-for="i in (5 - ratingOn())" class="icon-note star_ico_off"></span>
        </div>
    </div>
</template>

<script lang="ts" src="./competitive-rate.component.ts">
</script>

<style scoped>
    .star_ico_off {
        margin-left: 2px;
        color: #dbe2e8;
        font-size: 24px;
    }
    .star_ico_on {
        margin-left: 2px;
        color: #1ee3cf;
        font-size: 24px;
    }
    .rate-container {
        display: flex;
        flex-direction: column;
        width: min-content;
        align-items: flex-start;
    }
    .rate-stars {
        flex-direction: row;
        align-content: space-evenly;
        white-space: nowrap;
    }
    .rate-title {
        color: #909090;
        font-size: 12px;
        font-weight: normal;
        white-space: nowrap;
    }
    .rate-value {
        color: #262626;
        font-size: 56px;
    }
</style>
