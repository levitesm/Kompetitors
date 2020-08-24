<template>
    <div class="head-container">
        <span class="title">{{ title }}</span>
        <span class="name">{{ name }}</span>
        <div class="head-icon-link">
            <a v-if="linkedinUrl" :href="getUrl()" target="_blank" style="color: #6b48ff" >
                <img src="../../../content/images/linkedin.svg" class="small_logo_pic">
            </a>
            <img v-else src="../../../content/images/linkedin.svg" class="small_logo_pic faded" >
        </div>
    </div>
</template>
<script lang="ts" src="./head-of.component.ts">

</script>

<style scoped>
    .head-container {
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }
    .title {
        color: #909090;
        font-size: 12px;
        font-weight: normal;
        white-space: nowrap;
        padding-bottom: 14px;
    }
    .head-icon-link {
        padding-top: 14px;
    }
    .small_logo_pic{
        height: 24px;
        width: 24px;
        filter: invert(0);
    }
    .faded{
        filter: invert(50%) !important;
    }
    .name {
        white-space: nowrap;
    }
</style>
