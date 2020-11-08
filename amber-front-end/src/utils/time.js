/* eslint-disable indent */
export function formatTime(timestamp) {
    const time = new Date(timestamp)
    const y = time.getFullYear()
    const M = time.getMonth() + 1
    const d = time.getDate()
    const h = time.getHours()
    const m = time.getMinutes()
    const s = time.getSeconds()
    return `${y}-${addZero(M)}-${addZero(d)} ${addZero(h)}:${addZero(m)}:${addZero(s)}`
}

export function formatTimeForChart(timestamp) {
    const time = new Date(timestamp)
    const M = time.getMonth() + 1
    const d = time.getDate()
    const h = time.getHours()
    const m = time.getMinutes()
    return `${addZero(M)}/${addZero(d)} ${addZero(h)}:${addZero(m)}`
}

function addZero(t) {
    return t < 10 ? `0${t}` : t
}
