import * as api from './api.js';

const form = document.getElementById('formPaciente');
const tbl = document.querySelector('#tblPacientes tbody');
const search = document.getElementById('searchPaciente');
const selectCliente = document.getElementById('idCliente');

// 🔹 Carrega lista de clientes no <select>
async function loadClientes() {
    let clientes = await api.listEntity('clientes');
    if (!Array.isArray(clientes)) clientes = [];

    selectCliente.innerHTML = '<option value="">Selecione um cliente...</option>';
    clientes.forEach(c => {
        const opt = document.createElement('option');
        opt.value = c.idCliente;
        opt.textContent = `${c.nome} (${c.idCliente})`;
        selectCliente.appendChild(opt);
    });
}

// 🔹 Carrega todos os pacientes
async function loadPacientes() {
    let pacientes = await api.listEntity('pets');
    if (!Array.isArray(pacientes)) pacientes = [];
    render(pacientes);
}

function render(list) {
    tbl.innerHTML = '';
    list.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.idPet ?? ''}</td>
            <td>${p.nome ?? ''}</td>
            <td>${p.especie ?? ''}</td>
            <td>${p.raca ?? ''}</td>
            <td>${p.dataNascimento ?? ''}</td>
            <td>${p.cliente ? p.cliente.nome : '—'}</td>
            <td class="text-center">
                <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${p.idPet}">
                    <i class="bi bi-pencil-square"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-del" data-id="${p.idPet}">
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
            const paciente = list.find(p => p.idPet == btn.dataset.id);
            if (!paciente) return;
            document.getElementById('idPet').value = paciente.idPet;
            document.getElementById('nome').value = paciente.nome;
            document.getElementById('especie').value = paciente.especie;
            document.getElementById('raca').value = paciente.raca;
            document.getElementById('dataNascimento').value = paciente.dataNascimento?.substring(0, 10) || '';
            selectCliente.value = paciente.cliente ? paciente.cliente.idCliente : '';
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (confirm('Deseja remover este paciente?')) {
                await api.deleteEntity('pets', btn.dataset.id);
                loadPacientes();
            }
        };
    });
}

form.addEventListener('submit', async e => {
    e.preventDefault();

    const id = document.getElementById('idPet').value;
    const paciente = {
        nome: document.getElementById('nome').value,
        especie: document.getElementById('especie').value,
        raca: document.getElementById('raca').value,
        dataNascimento: document.getElementById('dataNascimento').value,
        idCliente: Number(selectCliente.value)
    };

    if (id)
        await api.updateEntity('pets', id, paciente);
    else
        await api.createEntity('pets', paciente);

    form.reset();
    loadPacientes();
});

search.addEventListener('input', async () => {
    const termo = search.value.trim().toLowerCase();
    let pacientes = await api.listEntity('pets');
    pacientes = pacientes.filter(p => p.nome?.toLowerCase().includes(termo));
    render(pacientes);
});

// 🔹 Carrega tudo ao iniciar
window.addEventListener('load', async () => {
    await loadClientes();
    await loadPacientes();
});
