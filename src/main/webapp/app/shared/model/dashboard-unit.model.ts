import { IDashboardStatisticItem } from '@/shared/model/dashboard-statistic-item.model';

export interface IDashboardUnit {
  competitorId?: number;
  competitorName?: string;
  year?: number;
  groupLogo?: string;
  groupLogoContentType?: string;
  statistics?: IDashboardStatisticItem[];
}

export class DashboardUnit implements IDashboardUnit {
  constructor(
    public competitorId?: number,
    public competitorName?: string,
    public year?: number,
    public groupLogo?: string,
    public groupLogoContentType?: string,
    public statistics?: IDashboardStatisticItem[]
  ) {}
}
