export interface ITechPartners {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechPartners implements ITechPartners {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
