export interface ITechServices {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechServices implements ITechServices {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
