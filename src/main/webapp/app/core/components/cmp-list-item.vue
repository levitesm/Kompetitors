<template>
    <div class="cmp-list-item">
        <div class="cmp-list-item__hr"></div>
        <div class="cmp-list-item__data" >
            <div class="cmp-list-item__label">{{ name || '' }}</div>
            <div class="cmp-list-item__value">{{ (value || 0).toLocaleString('fr-FR') }}</div>
            <div v-if="unit && value" class="cmp-list-item__unit">{{ unit || '' }}</div>
        </div>
    </div>
</template>

<script lang="ts" src="./cmp-list-item.component.ts">
</script>

<style scoped lang="scss">
    .cmp-list-item {
        display: flex;
        flex-direction: column;
        
        &__hr {
            width: 100%;
            border-top: .5px solid rgba(0, 0, 0, 0.1);;
            margin: 0.5rem 0;
        }
        
        &__data {
            font-size: 12px;
            display: flex;
        }
        
        &__label {
            margin-right: auto;
        }
        
        &__value {
            letter-spacing: 1px;
        }
        
        &__unit {
            margin-left: 3px;
        }
    }
</style>
