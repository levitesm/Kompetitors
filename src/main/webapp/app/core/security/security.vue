<template>
    <div class="security">
        <h1 class="security__title">{{ $t('security.title') }}</h1>
        <p class="security__paragraph">
            <b>Static content right now:</b> Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
        <p class="security__paragraph">
            Lorem Ipsum is simply dummy text of the printing and typesetting industry.
            Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,
            when an unknown printer took a galley of type and scrambled it to make a type specimen book.
            It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
            It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
        </p>
        <p>
            v 1.0
        </p>
    </div>
</template>
<script lang="ts" src="./security.component.ts">
</script>
<style scoped>
    .security {
        padding: 25px;
    }
    .security__title {
        font-size: 34px;
        font-weight: 700;
    }
    .security__paragraph {
        margin: 20px 0 0;
    }
</style>
