import Component from 'vue-class-component';
import AnnualAccountMixin from '@/shared/mixins/annual-account-mixin';
import { Mixins } from 'vue-property-decorator';
import EmployeesMixin from '@/shared/mixins/employees-mixin';

@Component
export default class FinanceFormulaMixin extends Mixins(AnnualAccountMixin, EmployeesMixin) {
  nullableSum(x: number, y: number): number {
    return !x && !y ? undefined : (x ? x : 0) + (y ? y : 0);
  }

  nullableDiff(x: number, y: number): number {
    return !x && !y ? undefined : (x ? x : 0) - (y ? y : 0);
  }

  // General formulas
  payroll(year: number): number {
    return (
      this.nullableSum(this.valueByCodeAndYear('N47', year), this.valueByCodeAndYear('N48', year)) ||
      this.nullableSum(this.valueByCodeAndYear('CFY', year), this.valueByCodeAndYear('CFZ', year)) ||
      0
    );
  }
  grossSales(year: number): number {
    return this.valueByCodeAndYear('N40', year) || this.valueByCodeAndYear('CFL', year) || 0;
  }
  subContract(year: number): number {
    return this.valueByCodeAndYear('YT', year) || this.valueByCodeAndYear('MYT', year) || 0;
  }
  operatingResult(year: number): number {
    return this.valueByCodeAndYear('CGG', year) || 0;
  }
  deficit(year: number): number {
    return (
      this.valueByCodeAndYear('156', year) ||
      this.valueByCodeAndYear('P1', year) ||
      this.valueByCodeAndYear('NP1', year) ||
      this.nullableSum(this.valueByCodeAndYear('BDU', year), this.valueByCodeAndYear('BDV', year)) ||
      0
    );
  }
  equity(year: number): number {
    return this.valueByCodeAndYear('142', year) || this.valueByCodeAndYear('BDL', year) || 0;
  }
  dividends(year: number): number {
    return this.valueByCodeAndYear('ZE', year) || this.valueByCodeAndYear('MZE', year) || 0;
  }
  netResult(year: number): number {
    return (
      this.valueByCodeAndYear('P8', year) ||
      this.valueByCodeAndYear('R4', year) ||
      this.valueByCodeAndYear('136', year) ||
      this.valueByCodeAndYear('DI', year) ||
      this.valueByCodeAndYear('BDI', year) ||
      this.valueByCodeAndYear('HN', year) ||
      this.valueByCodeAndYear('DHN', year) ||
      0
    );
  }
  subcontracting(year: number): number {
    return this.valueByCodeAndYear('YT', year) || this.valueByCodeAndYear('MYT', year) || 0;
  }
  shareCapital(year: number): number {
    return this.valueByCodeAndYear('120', year) || this.valueByCodeAndYear('A1H', year) || this.valueByCodeAndYear('BDA', year) || 0;
  }
  retainedEarnings(year: number): number {
    return this.valueByCodeAndYear('134', year) || this.valueByCodeAndYear('BDH', year) || this.valueByCodeAndYear('MZG', year) || 0;
  }
  availability(year: number): number {
    return (
      this.valueByCodeAndYear('187', year) ||
      this.valueByCodeAndYear('ACF', year) ||
      this.valueByCodeAndYear('ACG', year) ||
      this.valueByCodeAndYear('A2P', year) ||
      this.valueByCodeAndYear('A4F', year) ||
      this.nullableDiff(this.valueByCodeAndYear('N11', year), this.valueByCodeAndYear('BEH', year)) ||
      0
    );
  }
  share(year: number): number {
    return (
      this.nullableDiff(this.valueByCodeAndYear('120', year), this.valueByCodeAndYear('N16', year)) ||
      this.nullableDiff(this.valueByCodeAndYear('120', year), this.valueByCodeAndYear('120', year - 1)) ||
      0
    );
  }
  participation(year: number): number {
    return this.valueByCodeAndYear('DHJ', year) || 0;
  }
  goodwill(year: number): boolean {
    return this.valueByCodeAndYear('AAH', year) && this.valueByCodeAndYear('AAH', year) >= 0;
  }
  researchTax(year: number): number {
    return this.valueByCodeAndYear('DHK', year) || 0;
  }

  // Ratios formulas
  payrollToGrossSales(year: number): number {
    const payroll = this.payroll(year);
    const gross = this.grossSales(year) !== 0 ? this.grossSales(year) : 1;
    return Number(((payroll * 100) / gross).toFixed(0));
  }
  grossSalesToWorkForce(year: number): number {
    const gross = this.grossSales(year);
    const workForce = this.workForce(year) !== 0 ? this.workForce(year) : 1;
    return Number(gross / workForce > 1 ? (gross / workForce).toFixed(0) : gross / workForce);
  }
  grossSubcontractWorkforce(year: number): number {
    const gross = this.grossSales(year);
    const sub = this.subContract(year);
    const workForce = this.workForce(year) !== 0 ? this.workForce(year) : 1;
    return Number(((gross - sub) / workForce).toFixed(0));
  }
  operatingResultToGrossSales(year: number): number {
    const oper = this.operatingResult(year);
    const gross = this.grossSales(year) !== 0 ? this.grossSales(year) : 1;
    return Number(((oper * 100) / gross).toFixed(0));
  }
  deficitToEquity(year: number): number {
    const deficit = this.deficit(year);
    const equity = this.equity(year) !== 0 ? this.equity(year) : 1;
    return Number(((deficit * 100) / equity).toFixed(0));
  }
  dividendsToNetResult(year: number): number {
    const dividends = this.dividends(year);
    const result = this.netResult(year) !== 0 ? this.netResult(year) : 1;
    return Number(((dividends * 100) / result).toFixed(0));
  }
  payrollToWorkforce(year: number): number {
    const payroll = this.payroll(year);
    const workForce = this.workForce(year) !== 0 ? this.workForce(year) : 1;
    return Number(payroll / workForce > 1 ? (payroll / workForce).toFixed(0) : payroll / workForce) * 0.678; // * 0.678 for Taxes
  }
  researchTaxToPayroll(year: number): number {
    const rnd = this.researchTax(year);
    const payroll = this.payroll(year) !== 0 ? this.payroll(year) : 1;
    return Number(((rnd * 100) / 0.3 / payroll).toFixed(0));
  }
  profitPayroll(year: number): number {
    return 0;
  }
  netResultToEquity(year: number): number {
    const net = this.netResult(year);
    const equity = this.equity(year);
    return equity !== 0 ? Number(((net * 100) / equity).toFixed(0)) : 0;
  }
  netResultToGrossSales(year: number): number {
    const net = this.netResult(year);
    const gross = this.grossSales(year) !== 0 ? this.grossSales(year) : 1;
    return net !== 0 ? Number(((net * 100) / gross).toFixed(0)) : 0;
  }

  grossMarginRatio(year: number): number {
    const gross = this.grossSales(year) || 0;
    const payroll = this.payroll(year) || 0;
    const subcontracting = this.subContract(year) || 0;
    return gross - payroll - subcontracting;
  }

  currentAssets(year: number): number {
    const priority =
      (this.valueByCodeAndYear('ABV', year) || 0) +
      (this.valueByCodeAndYear('ABX', year) || 0) +
      (this.valueByCodeAndYear('ABZ', year) || 0);
    return priority !== 0
      ? priority
      : (this.valueByCodeAndYear('064', year) || 0) +
          (this.valueByCodeAndYear('068', year) || 0) +
          (this.valueByCodeAndYear('072', year) || 0);
  }

  currentLiabilities(year: number): number {
    const priority =
      (this.valueByCodeAndYear('BDV', year) || 0) +
      (this.valueByCodeAndYear('BDX', year) || 0) +
      (this.valueByCodeAndYear('BDY', year) || 0);
    return priority !== 0
      ? priority
      : (this.valueByCodeAndYear('164', year) || 0) +
          (this.valueByCodeAndYear('166', year) || 0) +
          (this.valueByCodeAndYear('172', year) || 0);
  }

  workingCapitalRequirements(year: number): number {
    return this.currentAssets(year) - this.currentLiabilities(year);
  }
}
