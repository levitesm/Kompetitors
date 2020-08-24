import { IAccessKey } from '@/shared/model/access-key.model';

export interface IUserGroupRights {
  id?: number;
  userGroupName?: string;
  accessKey?: IAccessKey;
}

export class UserGroupRights implements IUserGroupRights {
  constructor(public id?: number, public userGroupName?: string, public accessKey?: IAccessKey) {}
}
