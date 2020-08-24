<template>
    <div>
        <div class="title">
            <div>{{ displayName }}</div>
            <img src="../../../../content/images/info-icon.jpg" :title="tooltip" alt="Capacité d'autofinancement">
        </div>
        <table>
            <tr>
                <th>{{ `${prevYear}, ${unit}` }}</th>
                <th>{{ `${prevYear - 1}, ${unit}` }}</th>
                <th>{{ `${prevYear - 2}, ${unit}` }}</th>
                <th>{{ 'Variation ' + prevYear + '/' + (prevYear - 1) }}</th>
                <th>{{ 'Variation ' + prevYear + '/' + (prevYear - 2) }}</th>
            </tr>
            <tr class="table-body">
                <th v-text="func(prevYear).toLocaleString('fr-FR')" class="money"
                    :class="{green: getGreen(prevYear), red: getGreen(prevYear) !== undefined && !getGreen(prevYear)}">
                </th>
                <th v-text="func(prevYear - 1).toLocaleString('fr-FR')" class="money"
                    :class="{green: getGreen(prevYear - 1), red: getGreen(prevYear - 1) !== undefined && !getGreen(prevYear - 1)}">
                </th>
                <th v-text="func(prevYear - 2).toLocaleString('fr-FR')" class="money"
                    :class="{green: getGreen(prevYear - 2), red: getGreen(prevYear - 2) !== undefined && !getGreen(prevYear - 2)}">
                </th>
                <th v-text="percent(prevYear - 1, prevYear).toLocaleString('fr-FR') + ' %'"
                    :class="{red: percent(prevYear - 1, prevYear) < 0, green: percent(prevYear - 1, prevYear) > 0}">
                </th>
                <th v-text="percent(prevYear - 2, prevYear).toLocaleString('fr-FR') + ' %'"
                    :class="{red: percent(prevYear - 2, prevYear) < 0, green: percent(prevYear - 2, prevYear) > 0}">
                </th>
            </tr>
        </table>
    </div>
</template>

<script lang="ts" src="./finance-monthly-ratios.component.ts">
</script>

<style scoped>
table {
    width: 100%;
    font-size: 12px;
    font-weight: 600;
}
tr {
    border: 1px solid #e5e5e5;
}
th {
    width: 20%;
    text-align: center;
    font-weight: normal;
}
img {
    width: 15px;
    height: 16px;
}
.table-body {
    background-color: #fafafa;
}
.title {
    font-weight: bold;
    display: flex;
    flex-direction: row;
}
.red {
    color: red;
}
.green {
    color: green;
}
.money {
    letter-spacing: 1px;
}
</style>
