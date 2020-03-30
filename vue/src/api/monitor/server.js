import api from '@/utils/api'

// 查询服务器详细
export function getServer() {
  debugger
  return api({
    url: '/monitor/server',
    method: 'get'
  })
}