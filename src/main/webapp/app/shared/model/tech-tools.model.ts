export interface ITechTools {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechTools implements ITechTools {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
