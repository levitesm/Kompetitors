<template>
    <div class="privacy">
        <h1 class="privacy__title">{{ $t('privacy.title') }}</h1>
        <p class="privacy__paragraph">
            <b>Static content right now:</b> Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
        <p class="privacy__paragraph">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
    </div>
</template>
<script lang="ts" src="./privacy.component.ts">
</script>
<style scoped>
    .privacy {
        padding: 25px;
    }
    .privacy__title {
        font-size: 34px;
        font-weight: 700;
    }
    .privacy__paragraph {
        margin: 20px 0 0;
    }
</style>
