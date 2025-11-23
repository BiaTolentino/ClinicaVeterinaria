import { tryRefreshToken } from './api.js';

export async function ensureAuth() {
    let token = localStorage.getItem("token");

    if (!token) {
        const refreshed = await tryRefreshToken();
        if (!refreshed) {
            window.location.href = "login.html";
            return false;
        }
        token = localStorage.getItem("token");
    }

    return true; // token válido
}
