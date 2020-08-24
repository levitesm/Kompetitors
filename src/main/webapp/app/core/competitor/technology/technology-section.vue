<template>
    <div class="section">
        <div class="title">
            {{ title }}
            <span class="icon-edit suggest_link" :class="{'no-access': !hasAccess(TECH_EDIT)}" @click="showEditModal()"></span>
            <span class="icon-add suggest_link" :class="{'no-access': !hasAccess(TECH_EDIT)}" @click="showCreateModal()"></span>
        </div>
        <hr class="tech-hr">
        <template v-for="value in values">
            <div class="name-block">
                <img v-if="displayName(value, 'image').length > 0"
                     v-bind:src="'data:' + displayName(value, 'imageContentType') + ';base64,' + displayName(value, 'image')"
                     alt="logo"
                />
                <div class="item">{{ displayName(value, 'value') }}</div>
            </div>
            <hr class="tech-hr">
        </template>
        <div>
            <span v-for="i in Array(rate).keys()" class="icon-note star_ico_on"></span>
            <span v-for="i in Array(5 - rate).keys()" class="icon-note star_ico_off"></span>
        </div>
        <technology-edit :dictionary="dictionary" :values="values" :page-id="pageId" v-on:save="save"></technology-edit>
    </div>
</template>

<script lang="ts" src="./technology-section.component.ts"></script>

<style scoped>
    .section {
        min-width: 165px;
        width: 165px;
        margin: 0 110px 40px 0;
    }
    .title {
        font-size: 20px;
        font-weight: 600;
        margin-right: -50px;
        display: flex;
        flex-direction: row;
        white-space: nowrap;
    }
    .item {
        font-size: 12px;
        line-height: 20px;
    }
    .suggest_link:hover {
        text-decoration: underline;
    }
    .suggest_link {
        color: #6b48ff;
        font-size: 14px;
        cursor: pointer;
        margin: auto 0 0 10px;
        width: min-content;
        white-space: nowrap;
    }
    .name-block {
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
    }
    img {
        margin-right: 10px;
        max-height: 25px;
        max-width: 25px;
    }
    .tech-hr {
        margin-top: 0.5rem!important;
        margin-bottom: 0.5rem!important;
    }
</style>
