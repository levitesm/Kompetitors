import axios from 'axios';

import { IDashboardHR } from '@/shared/model/dashboard-hr.model';

const baseApiUrl = 'api/hr-infos/dashboard-hr';

export default class DashboardHrService {
  public retrieveByCompetitorId(competitorId: number): Promise<IDashboardHR[]> {
    const url = `${baseApiUrl}/competitor/${competitorId}`;
    return new Promise<IDashboardHR[]>(resolve => {
      axios.get(url).then(function(res) {
        resolve(res.data);
      });
    });
  }
}
