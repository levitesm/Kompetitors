export interface ITechProjects {
  id?: number;
  valueId?: number;
  competitorId?: number;
}

export class TechProjects implements ITechProjects {
  constructor(public id?: number, public valueId?: number, public competitorId?: number) {}
}
