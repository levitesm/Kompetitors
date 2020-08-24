import { IDialogs } from '@/shared/model/dialogs.model';
import { IFinance } from '@/shared/model/finance.model';
import { IOffices } from '@/shared/model/offices.model';
import { IPeople } from '@/shared/model/people.model';
import { IPrInfo } from '@/shared/model/pr-info.model';
import { IListCountries } from '@/shared/model/list-countries.model';
import { ISocieteMain } from '@/shared/model/societe-main.model';
import { IGlobalGroups } from '@/shared/model/global-groups.model';
import { Legal } from '@/shared/model/legal.model';
import { IHrInfo } from '@/shared/model/hr-info.model';
import { ICompetitiveRates } from '@/shared/model/competitive-rates.model';
import { IInfogreffe } from '@/shared/model/infogreffe.model';
import { ITechCompetancies } from '@/shared/model/tech-competancies.model';
import { ITechPartners } from '@/shared/model/tech-partners.model';
import { ITechProjects } from '@/shared/model/tech-projects.model';
import { ITechServices } from '@/shared/model/tech-services.model';
import { ITechTools } from '@/shared/model/tech-tools.model';
import { ITechPractices } from '@/shared/model/tech-practices.model';

export interface ICompetitors {
  id?: number;
  name?: string;
  webSite?: string;
  countryPhone?: string;
  dialogs?: IDialogs[];
  finances?: IFinance[];
  offices?: IOffices[];
  people?: IPeople[];
  prInfo?: IPrInfo[];
  hr?: IHrInfo[];
  techCompetancies?: ITechCompetancies[];
  techPractices?: ITechPractices[];
  techPartners?: ITechPartners[];
  techProjects?: ITechProjects[];
  techServices?: ITechServices[];
  techTools?: ITechTools[];
  country?: IListCountries;
  societeMain?: ISocieteMain[];
  infogreffe?: IInfogreffe[];
  globalGroups?: IGlobalGroups;
  legal?: Legal[];
  logoContentType?: string;
  logo?: any;
  competitiveRates?: ICompetitiveRates[];
}

export class Competitors implements ICompetitors {
  static fromId(id: number) {
    const result = new this();
    result.id = id;
    return result;
  }

  constructor(
    public id?: number,
    public name?: string,
    public webSite?: string,
    public countryPhone?: string,
    public dialogs?: IDialogs[],
    public finances?: IFinance[],
    public offices?: IOffices[],
    public people?: IPeople[],
    public prinfo?: IPrInfo[],
    public hr?: IHrInfo[],
    public techCompetancies?: ITechCompetancies[],
    public techPractices?: ITechPractices[],
    public techPartners?: ITechPartners[],
    public techProjects?: ITechProjects[],
    public techServices?: ITechServices[],
    public techTools?: ITechTools[],
    public country?: IListCountries,
    public societeMain?: ISocieteMain[],
    public infogreffe?: IInfogreffe[],
    public globalGroups?: IGlobalGroups,
    public legal?: Legal[],
    public logoContentType?: string,
    public logo?: any,
    public competitiveRates?: ICompetitiveRates[]
  ) {}
}
