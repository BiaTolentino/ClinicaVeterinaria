import { getReport } from './api.js';

async function load() {
    try {
        const vet = await getReport('consultas-por-veterinario');
        const med = await getReport('medicamentos-mais-usados');
        const esp = await getReport('consultas-por-especie');

        renderTable('tblVet', vet, ['Veterinário', 'Quantidade de Consultas']);
        renderTable('tblMed', med, ['Medicamento', 'Quantidade de Usos']);
        renderTable('tblEsp', esp, ['Espécie', 'Quantidade de Consultas']);
    } catch (e) {
        console.error('Erro ao carregar relatórios:', e);
    }
}

function renderTable(id, data, cols) {
    const table = document.getElementById(id);
    if (!table) return;
    const tbody = table.querySelector('tbody');
    if (!Array.isArray(data) || data.length === 0) {
        tbody.innerHTML = `<tr><td colspan="${cols.length}">Sem dados</td></tr>`;
        return;
    }

    // Espera que o backend já traga nome e quantidade
    const rows = data.map(row => {
        const values = Array.isArray(row) ? row : Object.values(row);
        const nome = values[0] ?? '-';
        const qtd = values[1] ?? 0;
        return `<tr><td>${nome}</td><td>${qtd}</td></tr>`;
    }).join('');

    tbody.innerHTML = rows;
}

window.addEventListener('load', load);
