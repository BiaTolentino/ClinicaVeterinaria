const API_BASE = "http://localhost:8080/api";

/* -----------------------------------------
   HEADERS COM TOKEN
------------------------------------------ */
export function getHeaders(extra = {}) {
    const token = localStorage.getItem("token");

    return {
        "Content-Type": "application/json",
        ...(token ? { "Authorization": `Bearer ${token}` } : {}),
        ...extra
    };
}

/* -----------------------------------------
   REFRESH TOKEN
------------------------------------------ */
export async function tryRefreshToken() {
    const refreshToken = localStorage.getItem("refreshToken");
    if (!refreshToken) return false;

    try {
        const res = await fetch(`${API_BASE}/auth/refresh`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ refreshToken })
        });

        if (!res.ok) return false;

        const data = await res.json();

        localStorage.setItem("token", data.token);
        localStorage.setItem("refreshToken", data.refreshToken);

        return true;
    } catch (e) {
        console.error("[refresh] erro:", e);
        return false;
    }
}

/* -----------------------------------------
   REQUEST BASE - COM RECUPERAÇÃO DE TOKEN
------------------------------------------ */
export async function request(path, opts = {}) {
    let originalBody = opts.body;
    if (opts.body && typeof opts.body !== "string")
        originalBody = JSON.stringify(opts.body);

    async function doFetch() {
        return fetch(API_BASE + path, {
            ...opts,
            headers: getHeaders(opts.headers || {}),
            body: originalBody
        });
    }

    let res = await doFetch();

    // tenta refresh automático
    if (res.status === 401) {
        const refreshed = await tryRefreshToken();
        if (refreshed) {
            res = await doFetch();
        } else {
            localStorage.removeItem("token");
            localStorage.removeItem("refreshToken");
            window.location.href = "login.html";
            return;
        }
    }

    if (!res.ok) {
        console.error("[request] erro:", res.status, path);
        return [];
    }

    const contentType = res.headers.get("content-type") || "";
    if (contentType.includes("application/json")) return res.json();
    return res.text();
}

/* -----------------------------------------
   CRUD GENÉRICO (compatível com o antigo)
------------------------------------------ */
export async function listEntity(entity) {
    return await request(`/${entity}`);
}

export async function getEntity(entity, id) {
    return await request(`/${entity}/${id}`);
}

export async function createEntity(entity, data) {
    return await request(`/${entity}`, {
        method: "POST",
        body: data
    });
}

export async function updateEntity(entity, id, data) {
    return await request(`/${entity}/${id}`, {
        method: "PUT",
        body: data
    });
}

export async function deleteEntity(entity, id) {
    return await request(`/${entity}/${id}`, {
        method: "DELETE"
    });
}

/* -----------------------------------------
   TRATAMENTOS E RELATÓRIOS (compatível)
------------------------------------------ */
export async function adicionarTratamento(payload) {
    return await request('/operations/tratamento', {
        method: 'POST',
        body: payload
    });
}

export async function getReport(slug) {
    return await request('/operations/reports/' + slug);
}
