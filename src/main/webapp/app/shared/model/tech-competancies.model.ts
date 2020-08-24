export interface ITechCompetancies {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechCompetancies implements ITechCompetancies {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
