export interface ICompetitorIndustry {
  id?: number;
  competitorId?: number;
  industryId?: number;
}

export class CompetitorIndustry implements ICompetitorIndustry {
  constructor(public id?: number, public competitorId?: number, public industryId?: number) {}
}
