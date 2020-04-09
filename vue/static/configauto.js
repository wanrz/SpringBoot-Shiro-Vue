// dev
const config = {
  protocol: 'http://',
  // picpath: 'http://localhost:9090'
  picpath: 'http://10.128.134.25:9090'
}

if (typeof module !== 'undefined' && typeof module.exports !== 'undefined') {
  module.exports = config
} else {
  window.config = config
}
