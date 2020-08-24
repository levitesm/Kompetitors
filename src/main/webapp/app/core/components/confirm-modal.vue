<template>
    <b-modal id="confirm-modal" scrollable lazy>
        <span slot="modal-title" class="confirm__title">
                <img class="confirm__img" src="../../../content/images/logo2.svg" alt="K">
                <span>{{ $t('confirm-modal.confirmation') }}</span>
        </span>
        <div class="confirm__text">
            {{ message }}
        </div>
        <div>
            <b-alert show variant="danger" v-if="savingError">
                <strong>{{ $t('confirm-modal.failed-save') }}</strong>
                <p>{{ savingError }}</p>
            </b-alert>
        </div>
        <div slot="modal-footer" class="confirm__footer">
            <CmpButton :text="$t('confirm-modal.cancel')" :onClick="closeModal" class="confirm__btn" :type="'outlined'"></CmpButton>
            <CmpButton :text="$t('confirm-modal.confirm')" :onClick="confirm" class="confirm__btn"></CmpButton>
        </div>
    </b-modal>
</template>

<script lang="ts" src="./confirm-modal.component.ts">
</script>

<style scoped lang="scss">
    .confirm {
        &__title {
            display: flex;
            flex-direction: row;
            font-size: 24px;
            font-weight: 600;
        }
        
        &__img {
            margin-right: 1rem;
        }
        
        &__text {
            margin: 1rem;
        }
        
        &__footer {
            display: flex;
            justify-content: space-around;
            width: 100%;
        }
        
        &__btn {
            width: 40%;
        }
    }
</style>
