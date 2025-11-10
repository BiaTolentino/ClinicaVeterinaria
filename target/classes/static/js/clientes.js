import * as api from './api.js';

const form = document.getElementById('formCliente');
const tbl = document.querySelector('#tblClientes tbody');
const search = document.getElementById('searchCliente');

// 🔹 Carrega todos os clientes
async function loadClientes() {
    let clientes = await api.listEntity('clientes');
    if (!Array.isArray(clientes)) clientes = [];
    render(clientes);
}

function render(list) {
    tbl.innerHTML = '';
    list.forEach(c => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
      <td>${c.idCliente ?? ''}</td>
      <td>${c.nome ?? ''}</td>
      <td>${c.cpf ?? ''}</td>
      <td>${c.telefone ?? ''}</td>
      <td>${c.email ?? ''}</td>
      <td>${c.endereco ?? ''}</td>
      <td class="text-center">
        <button class="btn btn-sm btn-primary me-1 btn-edit" data-id="${c.idCliente}">
          <i class="bi bi-pencil-square"></i>
        </button>
        <button class="btn btn-sm btn-danger btn-del" data-id="${c.idCliente}">
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
            const c = list.find(x => x.idCliente == btn.dataset.id);
            if (!c) return;
            document.getElementById('idCliente').value = c.idCliente;
            document.getElementById('nome').value = c.nome;
            document.getElementById('cpf').value = c.cpf;
            document.getElementById('telefone').value = c.telefone;
            document.getElementById('email').value = c.email;
            document.getElementById('endereco').value = c.endereco;
        };
    });

    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.onclick = async () => {
            if (confirm('Deseja remover este cliente?')) {
                await api.deleteEntity('clientes', btn.dataset.id);
                loadClientes();
            }
        };
    });
}

// 🔹 Salvar cliente
form.addEventListener('submit', async e => {
    e.preventDefault();

    const id = document.getElementById('idCliente').value;
    const cliente = {
        nome: document.getElementById('nome').value,
        cpf: document.getElementById('cpf').value,
        telefone: document.getElementById('telefone').value,
        email: document.getElementById('email').value,
        endereco: document.getElementById('endereco').value
    };

    if (id)
        await api.updateEntity('clientes', id, cliente);
    else
        await api.createEntity('clientes', cliente);

    form.reset();
    loadClientes();
});

// 🔹 Pesquisa rápida
search.addEventListener('input', async () => {
    const termo = search.value.trim().toLowerCase();
    let clientes = await api.listEntity('clientes');
    clientes = clientes.filter(c => c.nome?.toLowerCase().includes(termo));
    render(clientes);
});

// 🔹 Inicialização
window.addEventListener('load', loadClientes);
