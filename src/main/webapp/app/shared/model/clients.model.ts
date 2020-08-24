import { IClientsProjects } from '@/shared/model/clients-projects.model';
import { IListIndustries } from '@/shared/model/list-industries.model';
import { IOffices } from '@/shared/model/offices.model';

export interface IClients {
  id?: number;
  officeName?: string;
  name?: string;
  since?: string;
  isIppon?: boolean;
  updateDate?: Date;
  projects?: IClientsProjects[];
  industry?: IListIndustries;
  offices?: IOffices;
}

export class Clients implements IClients {
  constructor(
    public id?: number,
    public officeName?: string,
    public name?: string,
    public since?: string,
    public isIppon?: boolean,
    public updateDate?: Date,
    public projects?: IClientsProjects[],
    public industry?: IListIndustries,
    public offices?: IOffices
  ) {
    this.isIppon = this.isIppon || false;
  }
}
