import axios from 'axios';

import { SERVER_API_URL } from '@/constants';

const TIMEOUT = 1000000;
const onRequestSuccess = config => {
  config.timeout = TIMEOUT;
  config.url = `${SERVER_API_URL}${config.url}`;
  return config;
};
const setupAxiosInterceptors = onUnauthenticated => {
  const onResponseError = err => {
    if (!err || !err.response) {
      //  Session END Fix - Mark
      const loc = window.location;
      const port = loc.port ? ':' + loc.port : '';
      loc.href = `//${loc.hostname}${port}/oauth2/authorization/oidc`;
    } else {
      const status = err.status || err.response.status;
      if (status === 403 || status === 401) {
        onUnauthenticated();
      }
      return Promise.reject(err);
    }
  };
  if (axios.interceptors) {
    axios.interceptors.request.use(onRequestSuccess);
    axios.interceptors.response.use(res => res, onResponseError);
  }
};

export { onRequestSuccess, setupAxiosInterceptors };
