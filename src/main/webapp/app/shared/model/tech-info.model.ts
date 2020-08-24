export interface ITechInfo {
  id?: number;
  techSpecialistsNumber?: number;
  competitorId?: number;
}

export class TechInfo implements ITechInfo {
  constructor(public id?: number, public techSpecialistsNumber?: number, public competitorId?: number) {}
}
