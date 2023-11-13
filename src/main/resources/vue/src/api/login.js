import request from './request'

export const login = (data) => {
  return request({
    url: 'http//:127.0.0.1:8081/login',
    method: 'POST',
    data
  })
}
