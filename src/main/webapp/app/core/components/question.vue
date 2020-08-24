<template>
    <div class="question-cont" @click="alertq"><img src="../../../content/images/question-mark.svg" :title="$t('app.question')" class="question-class"></div>
</template>
<script lang="ts" src="./question.component.ts">
</script>
<style scoped>
    .question-class {
        width: 20px;
        height: 20px;
    }
    .question-cont {
        width: 100%;
        text-align: right;
    }
</style>
