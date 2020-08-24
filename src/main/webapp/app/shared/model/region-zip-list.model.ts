export interface IRegionZipList {
  id?: number;
  region?: string;
  zip?: string;
}

export class RegionZipList implements IRegionZipList {
  constructor(public id?: number, public region?: string, public zip?: string) {}
}
