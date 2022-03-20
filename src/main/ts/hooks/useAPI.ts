import 'regenerator-runtime/runtime';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '@context/AuthProvider';

import type DTO from 'types/DTO';

const useAPI = () => {
  const navigate = useNavigate();
  const { getToken, removeToken } = useAuth();

  const handleResponse = async (response: Response) => {
    if (response.headers.get('Content-Type')?.includes('application/json')) {
      const data = await response.json();

      if (response.ok) return data;

      if (response.status === 401) {
        removeToken();
        navigate('/login');
      }

      return Promise.reject(data.message ?? response.statusText);
    }

    if (response.headers.get('Content-Type')?.includes('application/octet-stream')) {
      const blob = await response.blob();

      if (response.ok) {
        const a = document.createElement('a');
        document.body.appendChild(a);
        a.setAttribute('style', 'display: none');

        const url = window.URL.createObjectURL(blob);
        a.href = url;

        console.log(response.headers.get('Content-Disposition'));
        a.download = response.headers.get('Content-Disposition')?.split(';')[1]?.split('=')[1]!.replace(/"/g, '')!;

        a.click();
        window.URL.revokeObjectURL(url);
        return Promise.resolve();
      }

      return Promise.reject(response.statusText);
    }

    return Promise.reject(Error('Unsupported content type.'));
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
  const patch = request('PATCH');

  return {
    get, post, put, del, patch,
  };
};

export default useAPI;
