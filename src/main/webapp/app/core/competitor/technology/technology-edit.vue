<template>
    <b-modal :id="pageId" scrollable lazy>
        <span slot="modal-title" class="tech-title" >
            <img class="title-img" src="../../../../content/images/logo2.svg" alt="K">
            <span>{{ $t('technology-tab.information.edit-title') }}</span>
        </span>
        <Question />
        <div class="main">
            <b-form role="form">
                <b-form-checkbox-group v-model="selected" name="notUsed" class="scroll">
                    <template v-for="item in dictionary">
                        <b-form-checkbox :key="item.id" :value="item" @change="change(item, $event)">
                            <div style="font-weight: 600; height: 40px">
                                {{item.value}}
                            </div>
                        </b-form-checkbox>
                    </template>
                </b-form-checkbox-group>
            </b-form>
        </div>
        <b-button slot="modal-footer" variant="primary" class="login" v-on:click="save">
            {{ $t('add-agency.save') }}
        </b-button>
    </b-modal>
</template>

<script lang="ts" src="./technology-edit.component.ts"></script>

<style scoped>
    .main {
        margin-top: 20px;
        margin-left: 40px;
    }
    .scroll {
        display: flex;
        flex-direction: column;
    }
    .login {
        background-color: #6b48ff;
        color: #ffffff;
        width: 100%;
        height: 40px;
        border-radius: 3px;
        border-color: #6b48ff;
    }
    .title-img {
        margin-right: 10px;
    }
    .tech-title {
        display: flex;
        flex-direction: row;
        font-size: 24px;
        font-weight: 600;
    }
</style>
