export interface IRegionList {
  id?: number;
  region?: string;
  country?: string;
}

export class RegionList implements IRegionList {
  constructor(public id?: number, public region?: string, public country?: string) {}
}
