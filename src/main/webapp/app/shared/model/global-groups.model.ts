import { Competitors, ICompetitors } from '@/shared/model/competitors.model';

export interface IGlobalGroups {
  id?: number;
  name?: string;
  logoContentType?: string;
  logo?: any;
  webSite?: string;
  reference?: boolean;
  competitors?: ICompetitors[];
}

export class GlobalGroups implements IGlobalGroups {
  constructor(
    public id?: number,
    public name?: string,
    public logoContentType?: string,
    public logo?: any,
    public webSite?: string,
    public reference?: boolean,
    public competitors?: ICompetitors[]
  ) {
    this.name = this.name || '';
    this.logoContentType = this.logoContentType || '';
    this.logo = this.logo || null;
    this.webSite = this.webSite || '';
    this.reference = this.reference || false;
    this.competitors = [];
  }
}
