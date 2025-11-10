import * as api from './api.js';

const tbl = document.querySelector('#tblMeds tbody');
const form = document.getElementById('formMed');
const search = document.getElementById('searchMed');

// 🔹 Carrega todos os medicamentos
async function load() {
    let meds = await api.listEntity('medicamentos');
    if (!Array.isArray(meds)) meds = [];
    render(meds);
}

// 🔹 Renderiza tabela
function render(list) {
    tbl.innerHTML = '';
    list.forEach(m => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
      <td>${m.idMedicamento ?? m.id ?? ''}</td>
      <td>${m.nome ?? ''}</td>
      <td>${m.dosagem ?? ''}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${m.idMedicamento ?? m.id}">
          <i class="bi bi-pencil-square"></i>
        </button>
        <button class="btn btn-sm btn-danger btn-del" data-id="${m.idMedicamento ?? m.id}">
          <i class="bi bi-trash"></i>
        </button>
      </td>`;
        tbl.appendChild(tr);
    });
    attachButtons();
}

// 🔹 Eventos dos botões
function attachButtons() {
    document.querySelectorAll('.btn-edit').forEach(btn => {
        btn.onclick = async () => {
            const id = btn.dataset.id;
            const m = await api.getEntity('medicamentos', id);
            document.getElementById('medId').value = m.idMedicamento ?? m.id;
            document.getElementById('medNome').value = m.nome || '';
            document.getElementById('dosagem').value = m.dosagem || '';
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (confirm('Deseja excluir este medicamento?')) {
                await api.deleteEntity('medicamentos', btn.dataset.id);
                load();
            }
        };
    });
}

// 🔹 Salvar / Atualizar medicamento
form.addEventListener('submit', async e => {
    e.preventDefault();
    const id = document.getElementById('medId').value;
    const payload = {
        nome: document.getElementById('medNome').value,
        dosagem: document.getElementById('dosagem').value || null
    };
    if (id)
        await api.updateEntity('medicamentos', id, payload);
    else
        await api.createEntity('medicamentos', payload);

    form.reset();
    load();
});

// 🔹 Filtro de busca
search.addEventListener('input', async () => {
    const termo = search.value.trim().toLowerCase();
    let meds = await api.listEntity('medicamentos');
    meds = meds.filter(m => m.nome?.toLowerCase().includes(termo));
    render(meds);
});

// 🔹 Carregar ao iniciar
window.addEventListener('load', load);
