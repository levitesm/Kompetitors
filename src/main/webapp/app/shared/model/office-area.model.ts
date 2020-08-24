import { OfficeSearchDTO } from '@/shared/model/office-search.model';

export class OfficeArea {
  public offices?: OfficeSearchDTO[];
  public latitude?: number;
  public longitude?: number;

  public static empty(): OfficeArea {
    return new OfficeArea([], 0, 0);
  }

  constructor(offices: OfficeSearchDTO[], latitude: number, longitude: number) {
    this.offices = offices;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
