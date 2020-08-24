<template>
    <div class="terms">
        <h1 class="terms__title">{{ $t('terms.title') }}</h1>
        <p class="terms__paragraph">
            <b>Static content right now:</b> Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
        <p class="terms__paragraph">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
    </div>
</template>
<script lang="ts" src="./terms.component.ts">
</script>
<style scoped>
    .terms {
        padding: 25px;
    }
    .terms__title {
        font-size: 34px;
        font-weight: 700;
    }
    .terms__paragraph {
        margin: 20px 0 0;
    }
</style>
