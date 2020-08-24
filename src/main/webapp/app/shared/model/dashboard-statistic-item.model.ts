export interface IDashboardStatisticItem {
  name?: string;
  value?: number;
  unit?: string;
}

export class DashboardStatisticItem implements IDashboardStatisticItem {
  constructor(public name?: string, public value?: number, public unit?: string) {}
}
