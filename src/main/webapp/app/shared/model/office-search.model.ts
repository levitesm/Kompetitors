export class OfficeSearchDTO {
  public id?: number;
  public competitorname?: string;
  public groupname?: string;
  public country?: string;
  public city?: string;
  public post?: string;
  public address?: string;
  public regionzip?: string;
  public latitude?: number;
  public longitude?: number;

  public static empty() {
    return new OfficeSearchDTO(null, '', '', '', '', '', '', '', 0, 0);
  }

  constructor(
    id: number,
    competitorname: string,
    groupname: string,
    country: string,
    city: string,
    post: string,
    address: string,
    regionzip: string,
    latitude: number,
    longitude: number
  ) {
    this.id = id;
    this.competitorname = competitorname;
    this.groupname = groupname;
    this.country = country;
    this.city = city;
    this.post = post;
    this.address = address;
    this.regionzip = regionzip;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
