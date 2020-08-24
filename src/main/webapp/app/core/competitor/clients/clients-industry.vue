<template>
    <div>
        <div class="all_title">{{ $t('clients-tab.industries.header.available') }}</div>
        <div class="add" :class="{'no-access': !hasAccess(CLIENT_EDIT)}" @click="addIndustry()">
            {{$t('clients-tab.industries.add-industry')}}
        </div>
        <br>

        <div :class="{'no-access': !hasAccess(CLIENT_EDIT)}">
            <br/>
            <draggable class="list-group" :list="availableIndustriesDraggableList" group="people">
                <div
                    class="lower_menu_item unselected"
                    v-for="industry in availableIndustriesDraggableList"
                    :id="industry.id"
                >
                    {{ displayName(industry.value) }}
                </div>
            </draggable>
            <br/>
        </div>

        <div class="all_title">{{ $t('clients-tab.industries.header.competitor') }}</div>

        <div :class="{'no-access': !hasAccess(CLIENT_EDIT)}">
            <br/>
            <draggable class="list-group" :list="competitorIndustriesDraggableList" group="people" draggable=".draggable" @change="handleChange($event)" >
                <div
                    class="lower_menu_item"
                    :class="(existingIndustries[industry.id] ? 'not-draggable' : 'draggable')"
                    v-for="industry in competitorIndustriesDraggableList"
                    :id="industry.id"
                >
                    {{ displayName(industry.value)}}
                    <img v-if="!existingIndustries[industry.id]" v-on:click="() => removeIndustry(industry)" class="close-icon" src="content/icons/close-no-background.svg" />
                </div>
            </draggable>
            <br/>
        </div>

        <br/>

        <div>
            <button class="button" :class="{'disabled': isSaveDisabled || !hasAccess(CLIENT_EDIT)}" @click="save">{{ $t('add-agency.save') }}</button>
        </div>

        <add-industry @hide="fetchIndustries"></add-industry>

    </div>
</template>

<script lang="ts" src="./clients-industry.component.ts">
</script>
<style scoped>
    .CBSection1 {
        display: inline-block;
        text-align: left;
    }

    .checkB {
        color: #262626;
        font-weight: 600;
        font-size: 12px;
    }

    .button {
        background-color: #6b48ff;
        color: #ffffff;
        min-width: 80px;
        max-width: 120px;
        height: 40px;
        border-radius: 3px;
        border: 1px solid #6b48ff;
        line-height: 22px;
        font-size: 14px;
        font-weight: bold;
    }
    button:focus {
        outline:0;
    }
    .disabled {
        background-color: #DBE2E8;
        color: #B6C5D1;
        border: 1px solid #DBE2E8;
        pointer-events: none;
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
    .all_title {
        font-weight: 600;
        font-size: 20px;
        display: inline-block;
    }
    .list-group {
        flex-direction: inherit;
        flex-wrap: wrap;
    }
    .lower_menu_item {
        font-size: 14px;
        white-space: nowrap;
        margin-bottom: 14px;
    }
    .unselected {
        border-color: rgb(188, 202, 213);
        color: rgb(107, 72, 255);
    }
    .unselected:hover {
        background-color: rgb(244, 241, 255);
    }
    .draggable {
        background-color: #6242ea;
        border-color: #6242ea;
        color: #ffffff;
    }
    .draggable:hover {
        background-color: #6242ea;
        border-color: #6242ea;
        color: #ffffff;
    }
    .not-draggable {
        background-color: #dbe2e8;
        border: 0px;
        color: #6B48FF;
    }
    .not-draggable:hover {
        background-color: #dbe2e8;
        border-color: #dbe2e8;
        cursor: default;
    }
    .close-icon {
        margin-left: 10px;
        width: 15px;
        height: 15px;
    }
</style>
