<template>
    <div class="cmp-button"
         :class="{
          'cmp-button__contained': type === 'contained',
          'cmp-button__contained--disabled': disabled && type === 'contained',
          'cmp-button__outlined': type === 'outlined',
          'cmp-button__outlined--disabled': disabled && type === 'outlined'}"
         @click="onClick" >
        <p>{{ text }}</p>
    </div>
</template>

<script lang="ts" src="./cmp-button.component.ts">
</script>

<style scoped lang="scss">
    .cmp-button {
        display: flex;
        height: 40px;
        border-radius: 3px;
        font-size: 14px;
        font-weight: 600;
        text-transform: uppercase;
        cursor: pointer;
        
        p {
            margin: auto;
            text-align: center;
        }
        
        &__contained {
            background-color: #6b48ff;
            color: #ffffff;
            border: 2px solid #6b48ff;
            
            &:hover {
                background-color: #6242eb;
                border: 2px solid #6242eb;
            }
            
            &:focus {
                background-color: rgba(0,0,0,0.15);
                border: 2px solid rgba(0,0,0,0.15);
            }
            
            &--disabled {
                pointer-events: none;
                background-color: #DBE2E8;
                border-color: #DBE2E8;
                color: #B6C5D1;
            }
        }
        
        &__outlined {
            background-color: #ffffff;
            color: #6b48ff;
            border: 2px solid #B6C5D1;
    
            &:hover {
                color: #6242EB;
                background-color: rgba(107,72,255,0.08);
                border: 2px solid #B6C5D1;
            }
    
            &:focus {
                color: #5B3DD9;
                background-color: rgba(107,72,255,0.08);
                border: 2px solid #5B3DD9;
            }
            
            &--disabled {
                pointer-events: none;
                background-color: rgba(107,72,255,0.08);
                border-color: #DBE2E8;
                color: #B6C5D1;
                
            }
        }
    }
</style>
