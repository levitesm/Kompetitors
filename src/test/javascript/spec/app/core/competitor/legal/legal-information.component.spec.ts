import { shallowMount, Wrapper } from '@vue/test-utils';
import LegalInformationComponent from '@/core/competitor/legal/legal-information.component';

const en_noInformationAvailable = 'No information available';
const en_january = 'January';
const en_february = 'February';
const en_march = 'March';
const en_april = 'April';
const en_may = 'May';
const en_june = 'June';
const en_july = 'July';
const en_august = 'August';
const en_september = 'September';
const en_october = 'October';
const en_november = 'November';
const en_december = 'December';

const i18n_noInformationAvailable = 'kompetitors2App.legal.information.month.noinformation';
const i18n_january = 'kompetitors2App.legal.information.month.january';
const i18n_february = 'kompetitors2App.legal.information.month.february';
const i18n_march = 'kompetitors2App.legal.information.month.march';
const i18n_april = 'kompetitors2App.legal.information.month.april';
const i18n_may = 'kompetitors2App.legal.information.month.may';
const i18n_june = 'kompetitors2App.legal.information.month.june';
const i18n_july = 'kompetitors2App.legal.information.month.july';
const i18n_august = 'kompetitors2App.legal.information.month.august';
const i18n_september = 'kompetitors2App.legal.information.month.september';
const i18n_october = 'kompetitors2App.legal.information.month.october';
const i18n_november = 'kompetitors2App.legal.information.month.november';
const i18n_december = 'kompetitors2App.legal.information.month.december';

const enNumeralToMonth = [
  ['0', en_noInformationAvailable],
  ['01', en_january],
  ['02', en_february],
  ['03', en_march],
  ['04', en_april],
  ['05', en_may],
  ['06', en_june],
  ['07', en_july],
  ['08', en_august],
  ['09', en_september],
  ['10', en_october],
  ['11', en_november],
  ['12', en_december]
];

describe('LegalInformationComponent', () => {
  let legalInformation: LegalInformationComponent;
  let wrapper: Wrapper<LegalInformationComponent>;

  beforeEach(() => {
    wrapper = shallowMount<LegalInformationComponent>(LegalInformationComponent, {
      mocks: {
        $t: enStubbedI18n
      }
    });

    legalInformation = wrapper.vm;
  });

  it('should be a Vue instance', () => {
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  describe.each(enNumeralToMonth)(`numeralDateToTextual`, (numeral, expectedMonth) => {
    test(`should convert ${numeral} to ${expectedMonth}`, () => expect(legalInformation.numeralDateToTextual(numeral)).toBe(expectedMonth));
  });

  function enStubbedI18n(input) {
    switch (input) {
      case i18n_noInformationAvailable:
        return en_noInformationAvailable;
      case i18n_january:
        return en_january;
      case i18n_february:
        return en_february;
      case i18n_march:
        return en_march;
      case i18n_april:
        return en_april;
      case i18n_may:
        return en_may;
      case i18n_june:
        return en_june;
      case i18n_july:
        return en_july;
      case i18n_august:
        return en_august;
      case i18n_september:
        return en_september;
      case i18n_october:
        return en_october;
      case i18n_november:
        return en_november;
      case i18n_december:
        return en_december;
    }
  }
});
