import { IListClientsProjectTypes } from '@/shared/model/list-clients-project-types.model';
import { IClients } from '@/shared/model/clients.model';

export interface IClientsProjects {
  id?: number;
  status?: string;
  projectType?: IListClientsProjectTypes;
  clients?: IClients;
}

export class ClientsProjects implements IClientsProjects {
  constructor(public id?: number, public status?: string, public projectType?: IListClientsProjectTypes, public clients?: IClients) {}
}
