import * as api from './api.js';

const tbl = document.querySelector('#tblVets tbody');
const form = document.getElementById('formVet');
const search = document.getElementById('searchVet');

async function loadVets() {
    let vets = await api.listEntity('veterinarios');
    if (!Array.isArray(vets)) vets = [];
    render(vets);
}

function render(list) {
    tbl.innerHTML = '';
    list.forEach(v => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
      <td>${v.idVet ?? ''}</td>
      <td>${v.nome ?? ''}</td>
      <td>${v.crmv ?? ''}</td>
      <td>${v.especialidade ?? ''}</td>
      <td>${v.telefone ?? ''}</td>
      <td>${v.email ?? ''}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${v.idVet}">
          <i class="bi bi-pencil-square"></i>
        </button>
        <button class="btn btn-sm btn-danger btn-del" data-id="${v.idVet}">
          <i class="bi bi-trash"></i>
        </button>
      </td>
    `;
        tbl.appendChild(tr);
    });
    attachEvents(list);
}

function attachEvents(list) {
    document.querySelectorAll('.btn-edit').forEach(btn => {
        btn.onclick = () => {
            const v = list.find(x => x.idVet == btn.dataset.id);
            if (!v) return;
            document.getElementById('idVet').value = v.idVet;
            document.getElementById('nome').value = v.nome || '';
            document.getElementById('crmv').value = v.crmv || '';
            document.getElementById('especialidade').value = v.especialidade || '';
            document.getElementById('telefone').value = v.telefone || '';
            document.getElementById('email').value = v.email || '';
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (confirm('Deseja remover este veterinário?')) {
                await api.deleteEntity('veterinarios', btn.dataset.id);
                loadVets();
            }
        };
    });
}

form.addEventListener('submit', async e => {
    e.preventDefault();

    const id = document.getElementById('idVet').value;
    const payload = {
        nome: document.getElementById('nome').value,
        crmv: document.getElementById('crmv').value,
        especialidade: document.getElementById('especialidade').value,
        telefone: document.getElementById('telefone').value,
        email: document.getElementById('email').value
    };

    if (id)
        await api.updateEntity('veterinarios', id, payload);
    else
        await api.createEntity('veterinarios', payload);

    form.reset();
    loadVets();
});

search.addEventListener('input', async () => {
    const termo = search.value.trim().toLowerCase();
    let vets = await api.listEntity('veterinarios');
    vets = vets.filter(v => v.nome?.toLowerCase().includes(termo));
    render(vets);
});

window.addEventListener('load', loadVets);
