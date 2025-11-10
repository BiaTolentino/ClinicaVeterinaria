// API client generic
const API_BASE = "http://localhost:8080/api";

async function request(path, opts = {}) {
  const res = await fetch(API_BASE + path, opts);
  const contentType = res.headers.get("content-type") || "";
  if (contentType.includes("application/json")) return res.json();
  return res.text();
}

// Generic CRUD for entities (assumes REST standard endpoints)
export async function listEntity(entity) {
  return await request('/' + entity);
}
export async function getEntity(entity, id) {
  return await request('/' + entity + '/' + id);
}
export async function createEntity(entity, data) {
  return await request('/' + entity, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  });
}
export async function updateEntity(entity, id, data) {
  return await request('/' + entity + '/' + id, {
    method: 'PUT',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  });
}
export async function deleteEntity(entity, id) {
  return await request('/' + entity + '/' + id, { method: 'DELETE' });
}

// Operations controller (tratamento + reports) lives under /api/operations
export async function adicionarTratamento(payload) {
  return await request('/operations/tratamento', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(payload)
  });
}
export async function getReport(slug) {
  return await request('/operations/reports/' + slug);
}
