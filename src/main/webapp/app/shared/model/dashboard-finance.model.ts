export interface IDashboardFinance {
  id?: number;
  grossSales?: number;
  grossSalesGrowth?: number;
  grossSalesPerEmployee?: number;
  ebit?: number;
  netResult?: number;
  netResultGrowth?: number;
  workforce?: number;
  year?: number;
  grossSalesPerConsultant?: number;
  averagePay?: number;
  netResultPercent?: number;
  competitorId?: number;
  competitorName?: string;
  groupLogo?: any;
  groupLogoContentType?: string;
}

export class DashboardFinance implements IDashboardFinance {
  constructor(
    public id?: number,
    public grossSales?: number,
    public grossSalesGrowth?: number,
    public grossSalesPerEmployee?: number,
    public ebit?: number,
    public netResult?: number,
    public netResultGrowth?: number,
    public workforce?: number,
    public year?: number,
    public grossSalesPerConsultant?: number,
    public averagePay?: number,
    public netResultPercent?: number,
    public competitorId?: number,
    public competitorName?: string,
    public groupLogo?: any,
    public groupLogoContentType?: string
  ) {}
}
