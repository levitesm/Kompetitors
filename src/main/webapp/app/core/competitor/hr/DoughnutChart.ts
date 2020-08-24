import { Doughnut, mixins } from 'vue-chartjs';

const { reactiveProp } = mixins;

export default {
  extends: Doughnut,
  mixins: [reactiveProp],
  props: ['options'],
  async mounted() {
    // Draw year inside Doughnut
    this.addPlugin({
      beforeDraw: chart => {
        const width = chart.chart.width,
          height = chart.chart.height,
          ctx = chart.chart.ctx;

        ctx.restore();
        const fontSize = (height / 150).toFixed(2);
        ctx.font = fontSize + 'em sans-serif';
        ctx.textBaseline = 'middle';
        const text = chart.data.datasets[0].label,
          textX = Math.round((width - ctx.measureText(text).width) / 2),
          textY = height / 2;

        ctx.fillText(text, textX, textY);
        ctx.textBaseline = 'bottom';
        ctx.save();
      }
    });
    // this.chartData is created in the mixin.
    // If you want to pass options please create a local options object
    await this.renderChart(this.chartData, this.options);
  }
};
