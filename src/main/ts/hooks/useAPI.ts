import 'regenerator-runtime/runtime';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '@context/AuthProvider';

import type DTO from 'types/DTO';

const useAPI = () => {
  const navigate = useNavigate();
  const { getToken, removeToken } = useAuth();

  const handleResponse = async (response: Response) => {
    const data = await response.json();

    if (response.ok) return data;

    if (response.status === 401) {
      removeToken();
      navigate('/login');
    }

    return Promise.reject(data.message ?? response.statusText);
  };

  const request = (method: string) => (
    async (url: string, body?: DTO) => {
      const token = getToken();

      const headers: HeadersInit = { method };
      if (body) headers['Content-Type'] = 'application/json';
      if (token) headers['Authorization'] = `Bearer ${token}`;

      return fetch(`/api/${url}`, {
        method,
        headers,
        body: body ? JSON.stringify(body) : null,
      }).then(handleResponse);
    }
  );

  const get = request('GET');
  const post = request('POST');
  const put = request('PUT');
  const del = request('DELETE');

  return {
    get, post, put, del,
  };
};

export default useAPI;
