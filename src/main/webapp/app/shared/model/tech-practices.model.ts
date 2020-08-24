export interface ITechPractices {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechPractices implements ITechPractices {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
