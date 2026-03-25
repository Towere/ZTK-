import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:5555', // 项目服务器地址
  withCredentials: true, // 跨域请求时发送 cookies
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 对响应数据做处理
    return response.data;
  },
  (error) => {
    // 对响应错误做处理
    return Promise.reject(error);
  }
);

export default instance;