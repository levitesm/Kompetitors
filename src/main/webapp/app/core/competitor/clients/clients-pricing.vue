<template>
    <div>
        <div class="table-legend">
            <div class="all_title" >{{ $t('clients-tab.pricing.emp-pricing') }}</div>
            <div class="link-btn" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
                 @click="showAddRole">+ {{ $t('clients-tab.pricing.add-role') }}
            </div>
        </div>
        <br>
        <table v-if="pricings.length > 0">
            <tr>
                <th>{{ $t('clients-tab.pricing.role-name') }}</th>
                <th>{{ $t('clients-tab.pricing.level') }}</th>
                <th>{{ $t('clients-tab.pricing.price') }}</th>
                <th>{{ $t('clients-tab.pricing.currency') }}</th>
                <th>{{ $t('clients-tab.pricing.payment-type') }}</th>
                <th>{{ $t('clients-tab.pricing.last-update') }}</th>
                <th></th>
            </tr>
            <tr class="t-body" v-for="item in pricings">
                <td>{{ roleById(item.employeeRoleId).name }}</td>
                <td>{{ item.level }}</td>
                <td>{{ item.price }}</td>
                <td>{{ item.currency }}</td>
                <td>{{ formatPaymentType(item.paymentType) }}</td>
                <td>{{ formatLocalDate(item.modified) }}</td>
                <td>
                    <span class="icon-close close-ico icon-button" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
                          @click="deletePricing(item)">
                    </span>
                    <span class="icon-edit close-ico" :class="{'no-access': !hasAccess(CLIENT_EDIT)}"
                          @click="showEditPricing(item)">
                    </span>
                </td>
            </tr>
        </table>
        <div v-else>
            <span class="grey">{{ $t('clients-tab.pricing.no-info') }}</span>
        </div>
        <br>
        <div class="link-btn" :class="{'no-access': !hasAccess(CLIENT_EDIT)}" style="float: right" @click="showAddPricing">+ {{ $t('clients-tab.pricing.add-pricing') }}</div>
        <br>
        <edit-pricing-modal :pricings="pricings" :selected="selectedPricing" :roles="roles"
                            :paymentTypes="paymentTypes" @pricingUpdate="updatePricing">
        </edit-pricing-modal>
        <add-employee-role-modal :roles="roles"  @addRole="addRole"></add-employee-role-modal>
    </div>
</template>

<script lang="ts" src="./clients-pricing.component.ts">
</script>

<style scoped>
    .close-ico:hover {
        cursor: pointer;
    }
    .all_title {
        font-weight: 600;
        font-size: 20px;
        display: inline-block;
    }
    .grey {
        color: #cccccc !important;
        cursor: default;
    }
    table {
        width: 100%;
    }
    .t-body {
        border-top: 1px solid rgba(0, 0, 0, 0.1);
    }
    td {
        padding-top: 0.5rem!important;
        padding-bottom: 0.5rem!important;
        font-size: 12px;
    }
    th {
        font-weight: 600;
        font-size: 12px;
    }
    .table-legend {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }
    .link-btn {
        color: #6b48ff;
        font-size: 12px;
    }
    .link-btn:hover {
        cursor: pointer;
        text-decoration: underline;
    }
    .icon-button {
        margin-right: 10px;
        color: red;
    }
</style>
