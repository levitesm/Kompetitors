import { Prop, Vue } from 'vue-property-decorator';
import Component from 'vue-class-component';
import { Infogreffe } from '@/shared/model/infogreffe.model';
import { Legal } from '@/shared/model/legal.model';

enum Month {
  NoInformation,
  January,
  February,
  March,
  April,
  May,
  June,
  July,
  August,
  September,
  October,
  November,
  December
}

@Component
export default class LegalInformationComponent extends Vue {
  @Prop() readonly infogreffe: Infogreffe[];
  @Prop() readonly legal: Legal[];

  numeralDateToTextual(date: string): string {
    const index = Number(date);
    return this.$root.$t('kompetitors2App.legal.information.month.' + Month[index].toString().toLowerCase()) as string;
  }

  publicationDate(): string {
    const numeralDate: string = this.infogreffe[0].dateDeClotureExercice1
      ? this.infogreffe[0].dateDeClotureExercice1.substring(
          this.infogreffe[0].dateDeClotureExercice1.indexOf('-') + 1,
          this.infogreffe[0].dateDeClotureExercice1.lastIndexOf('-')
        )
      : '0';
    return this.numeralDateToTextual(numeralDate);
  }
}
